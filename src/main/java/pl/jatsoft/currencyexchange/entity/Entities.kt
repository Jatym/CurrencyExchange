package pl.jatsoft.currencyexchange.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
data class UserAccountEntity(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val createTime: LocalDateTime = LocalDateTime.now(),
    val firstName: String,
    val lastName: String,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "userAccount", fetch = FetchType.EAGER)
    val bankAccounts: List<BankAccountEntity>? = listOf()
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
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="user_account_id", nullable=false)
    val userAccount: UserAccountEntity
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
    val exchangeRate: Double = 2.0,
)


enum class Currency {
    PLN, USD
}
