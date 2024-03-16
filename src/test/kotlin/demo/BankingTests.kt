/*
package demo

import com.ninjasquad.springmockk.MockkBean
import com.trendyol.kediatr.Mediator
import demo.application.deposit.commands.DepositCommand
import demo.application.transfer.commands.TransferCommand
import demo.domain.*
import io.mockk.every
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BankingTests (@Autowired private val mediator: Mediator) {

    @MockkBean
    lateinit var userRepository: IUserRepository

    @MockkBean
    lateinit var accountRepository: IAccountRepository

    @MockkBean
    lateinit var transactionRepository: ITransactionRepository

    @Test
    fun `Assert deposit was successful, content and status code`() {
        println(">> Assert deposit was successful")
        val customer = Customer("1", "JohnD", "John", "Doe", "*****")
        val account = Account(2000.0, "saving", customer.id.toString(), "account2")
        val TEST_COMMAND = DepositCommand(2000.00, "ZAR", "account2" )
        val results =  Account(balance = TEST_COMMAND.amount+ account.balance, type = account.type, owner = account.owner, account.id,)


        runBlocking {

            every { accountRepository.findById(account.id.toString()) } returns Optional.of(account)
            every { accountRepository.save( results) } returns(account)
            every { transactionRepository.save(Transaction(customer.id.toString(), TEST_COMMAND.amount)) } returns (Transaction("12345", TEST_COMMAND.amount,  customer.id.toString()))

            mediator.send(TEST_COMMAND)

            verify (exactly = 1){ accountRepository.save(results) }
            verify (exactly = 1){ transactionRepository.save(Transaction(customer.id.toString(), TEST_COMMAND.amount)) }
        }
    }


    @Test
    fun `Assert transfer was successful, content and status code`() {
        println(">> Assert transfer was successful")
        val customer1 = Customer("1","JohnD", "John", "Doe", "*****")
        val customer2 = Customer("2", "BonganiD", "Bongani", "Dlamini", "*****")

        val account1 = Account(2000.0, "saving", customer1.id.toString(), "account1")
        val account2 = Account( 2000.0, "saving", customer2.id.toString(), "account2")

        val TEST_COMMAND = TransferCommand(500.00, "account2", "ZAR", "account1")

        val resultsfrom =  Account( balance =  account1.balance - TEST_COMMAND.amount, type = account1.type, owner = account1.owner, account1.id)
        val resultsdestination =  Account(balance = TEST_COMMAND.amount + account2.balance, type = account2.type, owner = account2.owner, account2.id)


        runBlocking {

            every { accountRepository.findById(account1.id.toString()) } returns Optional.of(account1)
            every { accountRepository.findById(account2.id.toString()) } returns Optional.of(account2)

            println(account1)

            every { accountRepository.save( resultsdestination) } returns(account2)
            every { accountRepository.save(resultsfrom) } returns(account1)


            every { transactionRepository.save(Transaction( customer1.id.toString(), -TEST_COMMAND.amount)) } returns (Transaction("67890", -TEST_COMMAND.amount,  customer1.id.toString()))
            every { transactionRepository.save(Transaction(customer2.id.toString(), TEST_COMMAND.amount)) } returns (Transaction("12345", TEST_COMMAND.amount, customer1.id.toString()))

            mediator.send(TEST_COMMAND)

            verify (exactly = 1) { accountRepository.save(resultsfrom) }
            verify (exactly = 1) { accountRepository.save(resultsdestination) }
            verify (exactly = 1){ transactionRepository.save(Transaction(customer2.id.toString(), TEST_COMMAND.amount)) }
            verify (exactly = 1){ transactionRepository.save(Transaction(customer1.id.toString(), -TEST_COMMAND.amount)) }
        }
    }
}
*/
