package pl.jatsoft.currencyexchange.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime


@Entity
data class UserAccountEntity(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val createTime: LocalDateTime = LocalDateTime.now(),
    val firstName: String,
    val lastName: String,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    val bankAccounts: MutableList<BankAccountEntity>? = null
)

@Entity
data class BankAccountEntity(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val createTime: LocalDateTime = LocalDateTime.now(),
    val initialBalance: Double,
    @Enumerated(EnumType.STRING)
    val currency: Currency,
)

@Entity
data class OperationEntity(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val createTime: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    @JoinColumn(name="input_bank_account_id", nullable=false)
    val inputBankAccount: BankAccountEntity,
    @ManyToOne
    @JoinColumn(name="output_bank_account_id", nullable=false)
    val outputBankAccount: BankAccountEntity,
    val inputValue: Double,
    val outputValue: Double,
    val exchangeRate: Double
)


enum class Currency {
    PLN, USD
}
