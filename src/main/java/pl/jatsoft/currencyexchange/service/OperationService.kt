package pl.jatsoft.currencyexchange.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pl.jatsoft.currencyexchange.domain.Action
import pl.jatsoft.currencyexchange.domain.NewExchangeDomain
import pl.jatsoft.currencyexchange.domain.OperationDomain
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.entity.Currency
import pl.jatsoft.currencyexchange.entity.OperationEntity
import pl.jatsoft.currencyexchange.entity.UserAccountEntity
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toDomainList
import pl.jatsoft.currencyexchange.repository.OperationRepository
import pl.jatsoft.currencyexchange.repository.UserAccountRepository

@Service
class OperationService(
    val userAccountRepository: UserAccountRepository,
    val operationRepository: OperationRepository
) {

    @Transactional
    fun addNewOperation(newExchangeDomain: NewExchangeDomain, userAccountId: Long) : OperationDomain {
        val userAccountEntity = userAccountRepository.findById(userAccountId).get()
        return operationRepository.save(createNewOperation(newExchangeDomain, userAccountEntity)).toDomain()
    }

    private fun createNewOperation(newExchangeDomain: NewExchangeDomain, userAccount: UserAccountEntity): OperationEntity {
        return OperationEntity(
            inputBankAccount = findBankAccount(newExchangeDomain, userAccount),
            outputBankAccount = findBankAccount(newExchangeDomain, userAccount),
            inputValue = newExchangeDomain.value,
            outputValue = newExchangeDomain.value * 0.4,
            exchangeRate = 0.4
        )
    }

    private fun findBankAccount(newExchangeDomain: NewExchangeDomain, userAccount: UserAccountEntity): BankAccountEntity {
        return if (newExchangeDomain.action == Action.BUY) {
            userAccount.bankAccounts?.find { it.currency == Currency.PLN } ?: throw RuntimeException("Not found PLN bank account")
        }
        else {
            userAccount.bankAccounts?.find { it.currency == newExchangeDomain.currency } ?: throw RuntimeException("Not found ${newExchangeDomain.currency.name} bank account")
        }
    }

    @Transactional
    fun getOperationList(bankAccountId: Long, userAccountId: Long): List<OperationDomain> {
        val userAccountEntity = userAccountRepository.findById(userAccountId).get()
        val bankAccountEntity = userAccountEntity.bankAccounts?.find { it.id == bankAccountId } ?: throw RuntimeException("Bank account with id $bankAccountId does not exist")
        val inputOperations = operationRepository.findAllByInputBankAccount(bankAccountEntity)
        val outputOperations = operationRepository.findAllByOutputBankAccount(bankAccountEntity)
        return inputOperations.plus(outputOperations).sortedBy { it.createTime }.toDomainList()
    }
}