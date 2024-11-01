package pl.jatsoft.currencyexchange.infrastructure

import pl.jatsoft.currencyexchange.api.BankAccountDto
import pl.jatsoft.currencyexchange.api.NewUserAccountDto
import pl.jatsoft.currencyexchange.api.UserAccountDto
import pl.jatsoft.currencyexchange.domain.BankAccountDomain
import pl.jatsoft.currencyexchange.domain.UserAccountDomain
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.entity.Currency
import pl.jatsoft.currencyexchange.entity.UserAccountEntity

fun NewUserAccountDto.toDomain() : UserAccountDomain {
    return UserAccountDomain(
        firstName = this.firstName,
        lastName = this.lastName,
        bankAccounts = listOf(BankAccountDomain(
            balance = this.initialBalance,
            currency = Currency.PLN
        ))
    )
}

fun UserAccountDomain.toEntity() : UserAccountEntity {
    return UserAccountEntity(
        firstName = this.firstName,
        lastName = this.lastName
    )
}

fun UserAccountEntity.toDomain() : UserAccountDomain {
    return UserAccountDomain(
        id = this.id,
        createTime = this.createTime,
        firstName = this.firstName,
        lastName = this.lastName,
        bankAccounts = bankAccounts?.map { it.toDomain() } ?: listOf(),
    )
}

fun UserAccountDomain.toDto() : UserAccountDto {
    return UserAccountDto(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        bankAccounts = this.bankAccounts.map { it.toDto() }
    )
}

fun BankAccountDomain.toEntity(userAccountEntity: UserAccountEntity): BankAccountEntity {
    return BankAccountEntity(
        initialBalance = this.balance,
        currency = this.currency,
        createTime = this.createTime,
        userAccount = userAccountEntity
    )
}

fun BankAccountDto.toDomain() :BankAccountDomain {
    return BankAccountDomain(
        id = this.id,
        balance = this.balance,
        currency = this.currency
    )
}

fun BankAccountEntity.toDomain() : BankAccountDomain {
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
        currency = this.currency
    )
}