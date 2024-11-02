package pl.jatsoft.currencyexchange.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pl.jatsoft.currencyexchange.domain.BankAccountDomain
import pl.jatsoft.currencyexchange.domain.UserAccountDomain
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toEntity
import pl.jatsoft.currencyexchange.repository.BankAccountRepository
import pl.jatsoft.currencyexchange.repository.UserAccountRepository

@Service
class BankAccountService(
    val userAccountRepository: UserAccountRepository,
    val bankAccountRepository: BankAccountRepository
) {

    @Transactional
    fun addNewAccount(bankAccount: BankAccountDomain, userAccountId: Long) : UserAccountDomain {
        val userAccountEntity = userAccountRepository.findById(userAccountId).get()
        userAccountEntity.bankAccounts?.add(bankAccount.toEntity())
        return userAccountRepository.save(userAccountEntity).toDomain()
    }

    @Transactional
    fun getDetails(userAccountId: Long, bankAccountId: Long): BankAccountDomain {
        val userAccountEntity = userAccountRepository.findById(userAccountId).get()
        //TODO verify if belongs to user
        return bankAccountRepository.findById(bankAccountId).get().toDomain()
    }
}