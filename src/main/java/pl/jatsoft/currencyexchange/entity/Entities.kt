package pl.jatsoft.currencyexchange.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id


@Entity
class UserAccount(
    @Id @GeneratedValue var id: Long? = null,
    var firstName: String,
    var lastName: String,
)
