package pl.jatsoft.currencyexchange.validation

import org.springframework.stereotype.Component
import pl.jatsoft.currencyexchange.domain.NewExchangeDomain
import pl.jatsoft.currencyexchange.entity.UserAccountEntity
import pl.jatsoft.currencyexchange.infrastructure.ErrorResponse
import pl.jatsoft.currencyexchange.infrastructure.ValidationException

@Component
class OperationValidatorService(
    private val operationValidators: List<OperationValidator>
) {
    fun validate(newExchangeDomain: NewExchangeDomain, userAccountEntity: UserAccountEntity) {
        val errors: MutableList<String?> = mutableListOf()
        operationValidators.forEach {
            validator ->
            if (validator.check(newExchangeDomain, userAccountEntity)) {
                errors.add(validator.addError(newExchangeDomain, userAccountEntity))
            }
        }
        if (errors.filterNotNull().isNotEmpty()) {
            throw ValidationException("Operation validation exception", ErrorResponse(400, errors.filterNotNull()))
        }
    }
}

interface OperationValidator {

    fun addError(newExchangeDomain: NewExchangeDomain, userAccountEntity: UserAccountEntity) : String?

    fun check(newExchangeDomain: NewExchangeDomain, userAccountEntity: UserAccountEntity) : Boolean
}

@Component
class OperationValidatorImpl : OperationValidator {

    override fun addError(newExchangeDomain: NewExchangeDomain, userAccountEntity: UserAccountEntity): String? {
        return "User ${userAccountEntity.id} doesnt have ${newExchangeDomain.currency.name} account"
    }

    override fun check(newExchangeDomain: NewExchangeDomain, userAccountEntity: UserAccountEntity): Boolean {
        return userAccountEntity.bankAccounts?.map { it.currency.name }?.contains(newExchangeDomain.currency.name) != true
    }

}