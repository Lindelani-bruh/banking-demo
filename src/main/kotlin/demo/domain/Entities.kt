package domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Account (
    @Id val id:String?,
    val balance: Double,
    var type: String,
    var owner: String)

@Table
data class Customer (
    @Id val id:String?,
    val username: String,
    var firstname: String,
    var lastname:String,
    var password: String)

@Table
data class Transaction(
    @Id val id:String?,
    val owner: String,
    var amount: Double)

