package pl.jatsoft.currencyexchange.api

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toDto
import pl.jatsoft.currencyexchange.service.BankAccountService

@RestController()
@RequestMapping("/user-account")
class BankAccountController(
    val bankAccountService: BankAccountService
) {

    @PostMapping("/{userAccountId}/bank-account",
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createBankAccount(@RequestBody @Validated bankAccount: BankAccountDto,
                          @PathVariable userAccountId: Long
    ) : UserAccountDto {
        return bankAccountService.addNewAccount(bankAccount.toDomain(), userAccountId).toDto()
    }

    @GetMapping("/{userAccountId}/bank-account/{bankAccountId}", produces = ["application/json"])
    fun accountDetails(@PathVariable userAccountId: Long, @PathVariable bankAccountId: Long): BankAccountDto {
        return bankAccountService.getDetails(userAccountId, bankAccountId).toDto()
    }
}