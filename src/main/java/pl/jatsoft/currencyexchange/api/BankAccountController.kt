package pl.jatsoft.currencyexchange.api

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
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
    fun createBankAccount(@Valid @RequestBody bankAccount: BankAccountDto,
                          @PathVariable userAccountId: Long
    ) : ResponseEntity<UserAccountDto> {
        return ResponseEntity.status(201)
            .body(bankAccountService.addNewAccount(bankAccount.toDomain(), userAccountId).toDto())
    }

    @GetMapping("/{userAccountId}/bank-account/{bankAccountId}", produces = ["application/json"])
    fun getAccountDetails(@PathVariable userAccountId: Long, @PathVariable bankAccountId: Long): ResponseEntity<BankAccountDto> {
        return ResponseEntity.ok(bankAccountService.getDetails(userAccountId, bankAccountId).toDto())
    }

    @GetMapping("/{userAccountId}/bank-account/{bankAccountId}/balance", produces = ["application/json"])
    fun getBalance(@PathVariable userAccountId: Long, @PathVariable bankAccountId: Long): ResponseEntity<BalanceDto> {
        return ResponseEntity.ok(bankAccountService.getBalance(bankAccountId).toDto())
    }
}