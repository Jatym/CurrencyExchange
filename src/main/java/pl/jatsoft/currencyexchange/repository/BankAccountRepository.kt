package pl.jatsoft.currencyexchange.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.infrastructure.BankAccountNotFoundException

interface BankAccountRepository : JpaRepository<BankAccountEntity, Long> {

    fun findBankAccountById(bankAccountId: Long): BankAccountEntity {
        return findByIdOrNull(bankAccountId) ?: throw BankAccountNotFoundException(
            "Bank account with id {bankAccountId} not found",
            BankAccountNotFoundException.response
        )
    }
}