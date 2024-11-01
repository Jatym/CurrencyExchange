package pl.jatsoft.currencyexchange.api

import pl.jatsoft.currencyexchange.entity.Currency


data class NewUserAccountDto(
    val firstName: String,
    val lastName: String,
    val initialBalance: Double
)

data class UserAccountDto(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val bankAccounts: List<BankAccountDto>
)

data class BankAccountDto(
    val id: Long?,
    var balance: Double,
    var currency: Currency,
)

data class OperationDto(
    var inputBankAccount: BankAccountDto,
    var outputBankAccount: BankAccountDto,
    var inputValue: Double,
    var outputValue: Double,
    var exchangeRate: Double,
)


