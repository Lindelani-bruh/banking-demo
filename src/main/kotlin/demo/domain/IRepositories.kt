package demo.domain


import org.springframework.data.repository.CrudRepository
import java.util.*

interface IAccountRepository : CrudRepository<Account, String> {
    fun findByOwner(ownerId: String):Optional<List<Account>>
}

interface IUserRepository : CrudRepository<Customer, String> {
    fun findByEmail(email: String): Optional<Customer>
}

interface ITransactionRepository : CrudRepository<Transaction, String>
