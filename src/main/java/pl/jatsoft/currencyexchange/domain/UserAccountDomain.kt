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
    val initialBalance: Double,
    val currency: Currency
)

data class OperationDomain(
    val id: Long? = null,
    val createTime: LocalDateTime = LocalDateTime.now(),
    val inputBankAccount: BankAccountDomain,
    val outputBankAccount: BankAccountDomain,
    val inputValue: Double,
    val outputValue: Double,
    val exchangeRate: Double
)

data class NewExchangeDomain(
    val value: Double,
    val currency: Currency,
    val action: Action
)

enum class Action {
    BUY, SELL
}
