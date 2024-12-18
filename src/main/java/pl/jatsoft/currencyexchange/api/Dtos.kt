package pl.jatsoft.currencyexchange.api

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime


data class NewUserAccountDto(
    @field:NotBlank
    val firstName: String,
    @field:NotBlank
    val lastName: String,
    @field:NotNull
    @field:Min(10)
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
    val initialBalance: Double,
    val currency: CurrencyDto,
)

data class NewExchangeDto(
    val value: Double,
    val currency: CurrencyDto,
    val action: ActionDto
)

data class OperationDto(
    val id: Long?,
    val createTime: LocalDateTime,
    val inputBankAccount: BankAccountDto,
    val outputBankAccount: BankAccountDto,
    val inputValue: Double,
    val outputValue: Double,
    val exchangeRate: Double
)

data class BalanceDto(
    val balance: Double,
    val currency: CurrencyDto
)

enum class ActionDto {
    BUY, SELL
}

enum class CurrencyDto {
    PLN, USD
}


