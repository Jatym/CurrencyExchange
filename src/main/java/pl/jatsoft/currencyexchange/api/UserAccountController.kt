package pl.jatsoft.currencyexchange.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/user-account")
class UserAccountController {

    @PostMapping(
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createAccount(name: String): String {
        return name
    }

    @GetMapping("/{accountId}", produces = ["application/json"])
    fun accountDetails(@PathVariable accountId: String): String {
        return accountId
    }
}