package pl.jatsoft.currencyexchange.validation

import org.springframework.stereotype.Component
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.entity.UserAccountEntity
import pl.jatsoft.currencyexchange.infrastructure.ErrorResponse
import pl.jatsoft.currencyexchange.infrastructure.ValidationException

@Component
class BankAccountValidatorService(
    private val bankAccountValidators: List<BankAccountValidator>
) {
    fun validate(bankAccountEntity: BankAccountEntity?, userAccountEntity: UserAccountEntity?) {
        val errors: MutableList<String?> = mutableListOf()
        bankAccountValidators.forEach {
            validator ->
            if (validator.check(bankAccountEntity, userAccountEntity)) {
                errors.add(validator.addError(bankAccountEntity, userAccountEntity))
            }
        }
        if (errors.filterNotNull().isNotEmpty()) {
            throw ValidationException("Bank account validation exception", ErrorResponse(400, errors.filterNotNull()))
        }
    }
}

interface BankAccountValidator {

    fun addError(bankAccountEntity: BankAccountEntity?, userAccountEntity: UserAccountEntity?) : String?

    fun check(bankAccountEntity: BankAccountEntity?, userAccountEntity: UserAccountEntity?) : Boolean
}

@Component
class BankAccountValidatorImpl : BankAccountValidator {

    override fun addError(bankAccountEntity: BankAccountEntity?, userAccountEntity: UserAccountEntity?): String? {
        return "Bank account ${bankAccountEntity?.id} doesnt belong to user ${userAccountEntity?.id}"
    }

    override fun check(bankAccountEntity: BankAccountEntity?, userAccountEntity: UserAccountEntity?): Boolean {
        return userAccountEntity?.bankAccounts?.contains(bankAccountEntity) != true
    }

}