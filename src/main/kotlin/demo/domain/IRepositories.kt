package demo.domain


import org.springframework.data.repository.CrudRepository

interface IAccountRepository : CrudRepository<Account, String> {
    fun findByOwner(ownerId: String):List<Account>
}

interface IUserRepository : CrudRepository<Customer, String> {
    fun findByEmail(email: String): Customer
}

interface ITransactionRepository : CrudRepository<Transaction, String>
