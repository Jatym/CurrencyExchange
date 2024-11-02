package pl.jatsoft.currencyexchange.infrastructure


open class BusinessException(override val message: String, open val errorResponse: ErrorResponse) : RuntimeException(message)

class BankAccountNotFoundException(override val message: String, override val errorResponse: ErrorResponse) : BusinessException(message, errorResponse) {

    companion object {
        val response = ErrorResponse(404, "Bank account not found")
    }
}

class UserAccountNotFoundException(override val message: String, override val errorResponse: ErrorResponse) : BusinessException(message, errorResponse) {

    companion object {
        val response = ErrorResponse(404, "User account not found")
    }
}


data class ErrorResponse(val code: Int, val message: String)