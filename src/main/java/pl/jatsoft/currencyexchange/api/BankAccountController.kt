package pl.jatsoft.currencyexchange.api

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
    ) : BankAccountDto {
        return bankAccountService.addNewAccount(bankAccount.toDomain(), userAccountId).toDto()
    }

    @GetMapping("/{userAccountId}/bank-account/{bankAccountId}", produces = ["application/json"])
    fun accountDetails(@PathVariable userAccountId: Long, @PathVariable bankAccountId: Long): BankAccountDto {
        return bankAccountService.getDetails(userAccountId, bankAccountId).toDto()
    }
}