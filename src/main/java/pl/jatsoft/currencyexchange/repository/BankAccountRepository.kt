package pl.jatsoft.currencyexchange.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import pl.jatsoft.currencyexchange.entity.BankAccountEntity

interface BankAccountRepository : JpaRepository<BankAccountEntity, Long>