package demo.domain

import org.springframework.data.repository.CrudRepository

interface IAccountRepository : CrudRepository<Account, String>

interface IUserRepository : CrudRepository<Customer, String>

interface ITransactionRepository : CrudRepository<Transaction, String>
