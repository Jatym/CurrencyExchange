package pl.jatsoft.currencyexchange.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import pl.jatsoft.currencyexchange.entity.UserAccountEntity

interface UserAccountRepository : JpaRepository<UserAccountEntity, Long>