package pl.jatsoft.currencyexchange.infrastructure

import pl.jatsoft.currencyexchange.api.*
import pl.jatsoft.currencyexchange.domain.*
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.entity.Currency
import pl.jatsoft.currencyexchange.entity.OperationEntity
import pl.jatsoft.currencyexchange.entity.UserAccountEntity

fun NewUserAccountDto.toDomain(): UserAccountDomain {
    return UserAccountDomain(
        firstName = this.firstName,
        lastName = this.lastName,
        bankAccounts = listOf(
            BankAccountDomain(
                balance = this.initialBalance,
                currency = Currency.PLN
            )
        )
    )
}

fun UserAccountDomain.toEntity(): UserAccountEntity {
    return UserAccountEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        bankAccounts = this.bankAccounts.map { it.toEntity() }.toMutableList()
    )
}

fun UserAccountEntity.toDomain(): UserAccountDomain {
    return UserAccountDomain(
        id = this.id,
        createTime = this.createTime,
        firstName = this.firstName,
        lastName = this.lastName,
        bankAccounts = bankAccounts?.map { it.toDomain() } ?: listOf(),
    )
}

fun UserAccountDomain.toDto(): UserAccountDto {
    return UserAccountDto(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        bankAccounts = this.bankAccounts.map { it.toDto() }
    )
}

fun BankAccountDomain.toEntity(): BankAccountEntity {
    return BankAccountEntity(
        initialBalance = this.balance,
        currency = this.currency,
        createTime = this.createTime,
        //userAccount = userAccountEntity
    )
}

fun BankAccountDto.toDomain(): BankAccountDomain {
    return BankAccountDomain(
        id = this.id,
        balance = this.balance,
        currency = this.currency.toDomain()
    )
}

fun BankAccountEntity.toDomain(): BankAccountDomain {
    return BankAccountDomain(
        id = this.id,
        createTime = this.createTime,
        balance = this.initialBalance,
        currency = this.currency
    )
}

fun BankAccountDomain.toDto(): BankAccountDto {
    return BankAccountDto(
        id = this.id,
        balance = this.balance,
        currency = this.currency.toDto()
    )
}

fun NewExchangeDto.toDomain(): NewExchangeDomain {
    return NewExchangeDomain(
        value = this.value,
        currency = this.currency.toDomain(),
        action = this.action.toDomain()
    )
}

fun OperationEntity.toDomain() : OperationDomain {
    return OperationDomain(
        id = this.id,
        createTime = this.createTime,
        inputBankAccount = this.inputBankAccount.toDomain(),
        outputBankAccount = this.outputBankAccount.toDomain(),
        inputValue = this.inputValue,
        outputValue = this.outputValue,
        exchangeRate = this.exchangeRate
    )
}

fun OperationDomain.toDto() : OperationDto {
    return OperationDto(
        id = this.id,
        createTime = this.createTime,
        inputBankAccount = this.inputBankAccount.toDto(),
        outputBankAccount = this.outputBankAccount.toDto(),
        inputValue = this.inputValue,
        outputValue = this.outputValue,
        exchangeRate = this.exchangeRate
    )
}

fun List<OperationDomain>.toDtoList(): List<OperationDto> = this.map { it.toDto() }
fun List<OperationEntity>.toDomainList(): List<OperationDomain> = this.map { it.toDomain() }

fun CurrencyDto.toDomain() = Currency.valueOf(this.name)
fun Currency.toDto() = CurrencyDto.valueOf(this.name)

fun ActionDto.toDomain() = Action.valueOf(this.name)