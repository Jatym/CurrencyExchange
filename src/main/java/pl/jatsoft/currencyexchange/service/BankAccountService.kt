package pl.jatsoft.currencyexchange.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pl.jatsoft.currencyexchange.domain.BalanceDomain
import pl.jatsoft.currencyexchange.domain.BankAccountDomain
import pl.jatsoft.currencyexchange.domain.UserAccountDomain
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.entity.Currency
import pl.jatsoft.currencyexchange.entity.OperationEntity
import pl.jatsoft.currencyexchange.infrastructure.BankAccountNotFoundException
import pl.jatsoft.currencyexchange.infrastructure.BankAccountNotFoundException.Companion.bResponse
import pl.jatsoft.currencyexchange.infrastructure.UserAccountNotFoundException
import pl.jatsoft.currencyexchange.infrastructure.UserAccountNotFoundException.Companion.uResponse
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toEntity
import pl.jatsoft.currencyexchange.repository.BankAccountRepository
import pl.jatsoft.currencyexchange.repository.OperationRepository
import pl.jatsoft.currencyexchange.repository.UserAccountRepository
import pl.jatsoft.currencyexchange.validation.BankAccountValidatorService

@Service
class BankAccountService(
    val userAccountRepository: UserAccountRepository,
    val bankAccountRepository: BankAccountRepository,
    val operationRepository: OperationRepository,
    val bankAccountValidatorService: BankAccountValidatorService
) {

    @Transactional
    fun addNewAccount(bankAccount: BankAccountDomain, userAccountId: Long): UserAccountDomain {
        val userAccountEntity = userAccountRepository.findById(userAccountId)
            .orElseThrow { UserAccountNotFoundException(errorResponse = uResponse) }
        userAccountEntity!!.bankAccounts?.add(bankAccount.toEntity())
        return userAccountRepository.save(userAccountEntity).toDomain()
    }

    @Transactional
    fun getDetails(userAccountId: Long, bankAccountId: Long): BankAccountDomain {
        val userAccountEntity = userAccountRepository.findById(userAccountId)
            .orElseThrow { UserAccountNotFoundException(errorResponse = uResponse) }
        val bankAccountEntity = bankAccountRepository.findById(bankAccountId)
            .orElseThrow { BankAccountNotFoundException(errorResponse = bResponse) }
        bankAccountValidatorService.validate(bankAccountEntity, userAccountEntity)
        return bankAccountEntity.toDomain()
    }

    @Transactional
    fun getBalance(bankAccountId: Long): BalanceDomain {
        val bankAccount = bankAccountRepository.findById(bankAccountId)
            .orElseThrow { BankAccountNotFoundException(errorResponse = bResponse) }
        val inputOperations = operationRepository.findAllByInputBankAccount(bankAccount)
        val outputOperations = operationRepository.findAllByOutputBankAccount(bankAccount)
        val inputSum = calculateInputOperations(inputOperations, bankAccount.currency)
        val outputSum = calculateOutputOperations(outputOperations, bankAccount.currency)
        return calculateBalance(bankAccount, inputSum, outputSum)
    }

    private fun calculateBalance(
        bankAccount: BankAccountEntity,
        inputSum: Double,
        outputSum: Double
    ): BalanceDomain {
        return if (bankAccount.currency == Currency.PLN) {
            BalanceDomain(
                bankAccount.initialBalance + outputSum - inputSum,
                bankAccount.currency
            )
        } else {
            BalanceDomain(
                bankAccount.initialBalance - inputSum + outputSum,
                bankAccount.currency
            )
        }
    }

    private fun calculateInputOperations(inputOperations: List<OperationEntity>, currency: Currency): Double {
        return if (currency == Currency.PLN) {
            inputOperations.sumOf { it.outputValue }
        } else {
            inputOperations.sumOf { it.inputValue }
        }
    }

    private fun calculateOutputOperations(outputOperations: List<OperationEntity>, currency: Currency): Double {
        return if (currency == Currency.PLN) {
            outputOperations.sumOf { it.outputValue }
        } else {
            outputOperations.sumOf { it.inputValue }
        }
    }
}