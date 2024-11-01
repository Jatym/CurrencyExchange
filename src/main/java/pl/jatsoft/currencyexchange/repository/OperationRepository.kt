package pl.jatsoft.currencyexchange.repository

import org.springframework.data.repository.CrudRepository
import pl.jatsoft.currencyexchange.entity.OperationEntity

interface OperationRepository : CrudRepository<OperationEntity, Long>