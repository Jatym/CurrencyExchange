package pl.jatsoft.currencyexchange.repository

import org.springframework.data.repository.CrudRepository
import pl.jatsoft.currencyexchange.entity.BankAccountEntity
import pl.jatsoft.currencyexchange.entity.OperationEntity

interface OperationRepository : CrudRepository<OperationEntity, Long> {

    fun findAllByInputBankAccount(inputBankAccount: BankAccountEntity) : List<OperationEntity>
    fun findAllByOutputBankAccount(outputBankAccount: BankAccountEntity) : List<OperationEntity>
}