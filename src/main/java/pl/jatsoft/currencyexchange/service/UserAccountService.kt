package pl.jatsoft.currencyexchange.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pl.jatsoft.currencyexchange.domain.UserAccountDomain
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toEntity
import pl.jatsoft.currencyexchange.repository.BankAccountRepository
import pl.jatsoft.currencyexchange.repository.UserAccountRepository

@Service
class UserAccountService(
    val userAccountRepository: UserAccountRepository,
    val bankAccountRepository: BankAccountRepository
) {

    fun addNewAccount(userAccount: UserAccountDomain) : UserAccountDomain {
        val userAccountEntity = userAccountRepository.save(userAccount.toEntity())
        bankAccountRepository.save(userAccount.bankAccounts.first().toEntity(userAccountEntity))
        return userAccountRepository.findById(userAccountEntity.id!!).get().toDomain()
    }

    fun getDetails(accountId: Long): UserAccountDomain {
        return userAccountRepository.findById(accountId).get().toDomain()
    }
}