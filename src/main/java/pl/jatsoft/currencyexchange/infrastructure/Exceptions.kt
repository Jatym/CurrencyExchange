package pl.jatsoft.currencyexchange.infrastructure


open class BusinessException(override val message: String, open val errorResponse: ErrorResponse) : RuntimeException(message)

class BankAccountNotFoundException(message: String = "", override val errorResponse: ErrorResponse) : BusinessException(message, errorResponse) {

    companion object {
        val bResponse = ErrorResponse(404, listOf("Bank account not found"))
    }
}

class UserAccountNotFoundException(message: String = "", override val errorResponse: ErrorResponse) : BusinessException(message, errorResponse) {

    companion object {
        val uResponse = ErrorResponse(404, listOf("User account not found"))
    }
}

class ValidationException(message: String = "", override val errorResponse: ErrorResponse) : BusinessException(message, errorResponse)


data class ErrorResponse(val code: Int, val messages: List<String>)