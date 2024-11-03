package pl.jatsoft.currencyexchange.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pl.jatsoft.currencyexchange.adapter.NbpClient
import pl.jatsoft.currencyexchange.adapter.RatesDto
import pl.jatsoft.currencyexchange.domain.Action
import pl.jatsoft.currencyexchange.domain.NewExchangeDomain
import pl.jatsoft.currencyexchange.domain.OperationDomain
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.entity.Currency
import pl.jatsoft.currencyexchange.entity.OperationEntity
import pl.jatsoft.currencyexchange.entity.UserAccountEntity
import pl.jatsoft.currencyexchange.infrastructure.BankAccountNotFoundException
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toDomainList
import pl.jatsoft.currencyexchange.repository.OperationRepository
import pl.jatsoft.currencyexchange.repository.UserAccountRepository

@Service
class OperationService(
    val userAccountRepository: UserAccountRepository,
    val operationRepository: OperationRepository,
    val nbpClient: NbpClient
) {

    @Transactional
    fun addNewOperation(newExchangeDomain: NewExchangeDomain, userAccountId: Long): OperationDomain {
        val userAccountEntity = userAccountRepository.findUserAccountById(userAccountId)
        return operationRepository.save(createNewOperation(newExchangeDomain, userAccountEntity)).toDomain()
    }

    private fun createNewOperation(
        newExchangeDomain: NewExchangeDomain,
        userAccount: UserAccountEntity
    ): OperationEntity {
        val exchangeRates = nbpClient.exchangeRates(newExchangeDomain.currency)?.rates?.first() ?: throw RuntimeException("Can't retrive exchange rates")
        val rate = retriveRate(exchangeRates, newExchangeDomain.action)
        return OperationEntity(
            inputBankAccount = findInputBankAccount(newExchangeDomain, userAccount),
            outputBankAccount = findOutputBankAccount(newExchangeDomain, userAccount),
            inputValue = newExchangeDomain.value,
            outputValue = newExchangeDomain.value * rate,
            exchangeRate = rate
        )
    }

    private fun retriveRate(exchangeRates: RatesDto, action: Action): Double {
        return if (action == Action.BUY) {
            exchangeRates.ask
        }
        else {
            exchangeRates.bid
        }
    }

    private fun findInputBankAccount(
        newExchangeDomain: NewExchangeDomain,
        userAccount: UserAccountEntity
    ): BankAccountEntity {
        return if (newExchangeDomain.action == Action.BUY) {
            userAccount.bankAccounts?.find { it.currency == Currency.PLN } ?: throw BankAccountNotFoundException(
                "Not found PLN bank account",
                BankAccountNotFoundException.response
            )
        } else {
            userAccount.bankAccounts?.find { it.currency == newExchangeDomain.currency }
                ?: throw BankAccountNotFoundException(
                    "Not found ${newExchangeDomain.currency.name} bank account",
                    BankAccountNotFoundException.response
                )
        }
    }

    private fun findOutputBankAccount(
        newExchangeDomain: NewExchangeDomain,
        userAccount: UserAccountEntity
    ): BankAccountEntity {
        return if (newExchangeDomain.action == Action.SELL) {
            userAccount.bankAccounts?.find { it.currency == Currency.PLN } ?: throw BankAccountNotFoundException(
                "Not found PLN bank account",
                BankAccountNotFoundException.response
            )
        } else {
            userAccount.bankAccounts?.find { it.currency == newExchangeDomain.currency }
                ?: throw BankAccountNotFoundException(
                    "Not found ${newExchangeDomain.currency.name} bank account",
                    BankAccountNotFoundException.response
                )
        }
    }

    @Transactional
    fun getOperationList(bankAccountId: Long, userAccountId: Long): List<OperationDomain> {
        val userAccountEntity = userAccountRepository.findById(userAccountId).get()
        val bankAccountEntity = userAccountEntity.bankAccounts?.find { it.id == bankAccountId }
            ?: throw BankAccountNotFoundException(
                "Bank account with id $bankAccountId does not exist",
                BankAccountNotFoundException.response
            )
        val inputOperations = operationRepository.findAllByInputBankAccount(bankAccountEntity)
        val outputOperations = operationRepository.findAllByOutputBankAccount(bankAccountEntity)
        return inputOperations.plus(outputOperations).sortedBy { it.createTime }.toDomainList()
    }
}