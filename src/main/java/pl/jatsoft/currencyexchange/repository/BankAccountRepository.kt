package pl.jatsoft.currencyexchange.repository

import org.springframework.data.jpa.repository.JpaRepository
import pl.jatsoft.currencyexchange.entity.BankAccountEntity

interface BankAccountRepository : JpaRepository<BankAccountEntity, Long>