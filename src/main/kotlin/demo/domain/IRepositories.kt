package demo.domain

import org.springframework.data.repository.CrudRepository

interface IAccountRepository : CrudRepository<Account, String>

interface IUserRepository : CrudRepository<Customer, String> {
    fun findByEmail(email: String): Customer
}

interface ITransactionRepository : CrudRepository<Transaction, String>
