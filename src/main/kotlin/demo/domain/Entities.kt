package demo.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator


@Entity
class Account (
    var balance: Double,
    var type: String,
    var owner: String,
    @Id @UuidGenerator var id:String? = null)

@Entity
class Customer (
    var username: String,
    var firstname: String,
    var lastname:String,
    val email: String,
    var password: String,
    var role: Role? = Role.USER,
    @Id @UuidGenerator var id:String? = null)

@Entity
class Transaction(
    var owner: String,
    var amount: Double,
    @Id @UuidGenerator var id:String? = null)


enum class Role {
    USER, ADMIN
}