package demo.domain

import domain.entities.Account
import domain.entities.Customer
import domain.entities.Transaction
import org.springframework.data.repository.CrudRepository

interface IAccountRepository : CrudRepository<Account, String>

interface IUserRepository : CrudRepository<Customer, String>

interface ITransactionRepository : CrudRepository<Transaction, String>
