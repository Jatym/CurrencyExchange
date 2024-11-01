package pl.jatsoft.currencyexchange.domain

import pl.jatsoft.currencyexchange.entity.Currency
import java.time.LocalDateTime

data class UserAccountDomain(
    val id: Long? = null,
    val createTime: LocalDateTime = LocalDateTime.now(),
    val firstName: String,
    val lastName: String,
    val bankAccounts: List<BankAccountDomain>
)

data class BankAccountDomain(
    val id: Long? = null,
    val createTime: LocalDateTime = LocalDateTime.now(),
    val balance: Double,
    val currency: Currency
)

data class OperationDomain(
    val id: Long? = null,
    val createTime: LocalDateTime = LocalDateTime.now(),
    val inBankAccount: BankAccountDomain,
    val outBankAccount: BankAccountDomain,
    val inputValue: Double,
    val outValue: Double,
    val exchangeRate: Double
)
