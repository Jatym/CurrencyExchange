package pl.jatsoft.currencyexchange.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import pl.jatsoft.currencyexchange.domain.UserAccountDomain
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toEntity
import pl.jatsoft.currencyexchange.repository.UserAccountRepository

@Service
class UserAccountService(
    val userAccountRepository: UserAccountRepository
) {

    @Transactional
    fun addNewAccount(userAccount: UserAccountDomain) : UserAccountDomain {
        val userAccountEntity = userAccountRepository.save(userAccount.toEntity())
        return userAccountRepository.findUserAccountById(userAccountEntity.id!!).toDomain()
    }

    @Transactional
    fun getDetails(accountId: Long): UserAccountDomain {
        return userAccountRepository.findUserAccountById(accountId).toDomain()
    }
}