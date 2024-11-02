package pl.jatsoft.currencyexchange.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import pl.jatsoft.currencyexchange.entity.UserAccountEntity
import pl.jatsoft.currencyexchange.infrastructure.UserAccountNotFoundException

interface UserAccountRepository : JpaRepository<UserAccountEntity, Long> {

    fun findUserAccountById(userAccountId: Long): UserAccountEntity {
        return findByIdOrNull(userAccountId) ?: throw UserAccountNotFoundException(
            "User account with id {userAccountId} not found",
            UserAccountNotFoundException.response
        )
    }
}