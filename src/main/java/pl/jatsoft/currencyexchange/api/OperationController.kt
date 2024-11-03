package pl.jatsoft.currencyexchange.api

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.jatsoft.currencyexchange.infrastructure.toDomain
import pl.jatsoft.currencyexchange.infrastructure.toDto
import pl.jatsoft.currencyexchange.infrastructure.toDtoList
import pl.jatsoft.currencyexchange.service.OperationService

@RestController()
@RequestMapping("/user-account")
class OperationController(
    val operationService: OperationService
) {

    @PostMapping("/{userAccountId}/operation",
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createBankAccount(@RequestBody @Validated newExchangeDto: NewExchangeDto,
                          @PathVariable userAccountId: Long
    ) : ResponseEntity<OperationDto> {
        return ResponseEntity.status(201)
            .body(operationService.addNewOperation(newExchangeDto.toDomain(), userAccountId).toDto())
    }

    @GetMapping("/{userAccountId}/bank-account/{bankAccountId}/operation", produces = ["application/json"])
    fun getBankAccountOperationsList(@PathVariable userAccountId: Long, @PathVariable bankAccountId: Long): ResponseEntity<List<OperationDto>> {
        return ResponseEntity.ok(operationService.getOperationList(bankAccountId, userAccountId).toDtoList())
    }
}