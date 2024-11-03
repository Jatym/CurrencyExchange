package pl.jatsoft.currencyexchange.api

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toDto
import pl.jatsoft.currencyexchange.service.UserAccountService

@RestController()
@RequestMapping("/user-account")
class UserAccountController(
    val userAccountService: UserAccountService
) {

    @PostMapping(
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createUserAccount(@Valid @RequestBody newUserAccountDto: NewUserAccountDto) : ResponseEntity<UserAccountDto> {
        return ResponseEntity.status(201)
            .body(userAccountService.addNewAccount(newUserAccountDto.toDomain()).toDto())
    }

    @GetMapping("/{accountId}", produces = ["application/json"])
    fun userAccountDetails(@PathVariable accountId: Long): ResponseEntity<UserAccountDto> {
        return ResponseEntity.ok(userAccountService.getDetails(accountId).toDto())
    }
}