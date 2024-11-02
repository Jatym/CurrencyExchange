package pl.jatsoft.currencyexchange.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pl.jatsoft.currencyexchange.domain.BankAccountDomain
import pl.jatsoft.currencyexchange.domain.UserAccountDomain
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.entity.OperationEntity
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toEntity
import pl.jatsoft.currencyexchange.repository.BankAccountRepository
import pl.jatsoft.currencyexchange.repository.OperationRepository
import pl.jatsoft.currencyexchange.repository.UserAccountRepository

@Service
class BankAccountService(
    val userAccountRepository: UserAccountRepository,
    val bankAccountRepository: BankAccountRepository,
    val operationRepository: OperationRepository
) {

    @Transactional
    fun addNewAccount(bankAccount: BankAccountDomain, userAccountId: Long): UserAccountDomain {
        val userAccountEntity = userAccountRepository.findUserAccountById(userAccountId)
        userAccountEntity.bankAccounts?.add(bankAccount.toEntity())
        return userAccountRepository.save(userAccountEntity).toDomain()
    }

    @Transactional
    fun getDetails(userAccountId: Long, bankAccountId: Long): BankAccountDomain {
        val userAccountEntity = userAccountRepository.findUserAccountById(userAccountId)
        //TODO verify if belongs to user
        return bankAccountRepository.findBankAccountById(bankAccountId).toDomain()
    }

    @Transactional
    fun getBalance(bankAccountId: Long): Double {
        val bankAccount = bankAccountRepository.findBankAccountById(bankAccountId)
        val inputOperations = operationRepository.findAllByInputBankAccount(bankAccount)
        val outputOperations = operationRepository.findAllByOutputBankAccount(bankAccount)
        val inputSum = calculateInputOperations(inputOperations)
        val outputSum = calculateOutputOperations(outputOperations)
        return calculateBalance(bankAccount, inputSum, outputSum)
    }

    private fun calculateBalance(bankAccount: BankAccountEntity, inputSum: Double, outputSum: Double): Double {
        return bankAccount.initialBalance + outputSum - inputSum
    }

    private fun calculateInputOperations(inputOperations: List<OperationEntity>) : Double {
        return inputOperations.map { it.inputValue }.sum()
    }

    private fun calculateOutputOperations(outputOperations: List<OperationEntity>) : Double {
        return outputOperations.map { it.outputValue }.sum()
    }
}