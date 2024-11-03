package pl.jatsoft.currencyexchange.service

import java.time.LocalDateTime
import java.util.Optional
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.entity.Currency
import pl.jatsoft.currencyexchange.entity.OperationEntity
import pl.jatsoft.currencyexchange.repository.BankAccountRepository
import pl.jatsoft.currencyexchange.repository.OperationRepository
import pl.jatsoft.currencyexchange.repository.UserAccountRepository
import pl.jatsoft.currencyexchange.validation.BankAccountValidatorService


class BankAccountServiceTest {

    val userAccountRepository: UserAccountRepository = mock()
    val bankAccountRepository: BankAccountRepository = mock()
    val operationRepository: OperationRepository = mock()
    val bankAccountValidatorService: BankAccountValidatorService = mock()
    val bankAccountService = BankAccountService(userAccountRepository, bankAccountRepository, operationRepository, bankAccountValidatorService)

    val plnBankAccountId = 1L
    val plnBankAccount = fillBankAccountPLN()
    val usdBankAccount = fillBankAccountUSD()

    @Test
    fun getBalance() {

        whenever(bankAccountRepository.findById(plnBankAccountId)).thenReturn(Optional.of(fillBankAccountPLN()))
        whenever(operationRepository.findAllByInputBankAccount(any())).thenReturn(fillInputOperations())
        whenever(operationRepository.findAllByOutputBankAccount(any())).thenReturn(fillOutputOperations())

        val balance = bankAccountService.getBalance(plnBankAccountId).balance

        assertTrue(balance == 30.00)
    }

    private fun fillOutputOperations(): List<OperationEntity> {
        return listOf(
            OperationEntity(
                id = 1,
                createTime = LocalDateTime.now(),
                inputBankAccount = usdBankAccount,
                outputBankAccount = plnBankAccount,
                inputValue = 10.00,
                outputValue = 20.00,
                exchangeRate = 2.00
            ),
            OperationEntity(
                id = 2,
                createTime = LocalDateTime.now(),
                inputBankAccount = usdBankAccount,
                outputBankAccount = plnBankAccount,
                inputValue = 5.00,
                outputValue = 10.00,
                exchangeRate = 2.00
            )
        )
    }

    private fun fillInputOperations(): List<OperationEntity> {
        return listOf(
            OperationEntity(
                id = 3,
                createTime = LocalDateTime.now(),
                inputBankAccount = plnBankAccount,
                outputBankAccount = usdBankAccount,
                inputValue = 5.00,
                outputValue = 10.00,
                exchangeRate = 2.00
            )
        )
    }

    private fun fillBankAccountPLN(): BankAccountEntity {
        return BankAccountEntity(
            id = 1,
            createTime = LocalDateTime.now(),
            initialBalance = 10.00,
            currency = Currency.PLN
        )
    }

    private fun fillBankAccountUSD(): BankAccountEntity {
        return BankAccountEntity(
            id = 2,
            createTime = LocalDateTime.now(),
            initialBalance = 10.00,
            currency = Currency.USD
        )
    }
}