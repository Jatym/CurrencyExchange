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
    fun createUserAccount(@RequestBody @Validated newUserAccountDto: NewUserAccountDto) : UserAccountDto {
        return userAccountService.addNewAccount(newUserAccountDto.toDomain()).toDto()
    }

    @GetMapping("/{accountId}", produces = ["application/json"])
    fun userAccountDetails(@PathVariable accountId: Long): UserAccountDto {
        return userAccountService.getDetails(accountId).toDto()
    }
}