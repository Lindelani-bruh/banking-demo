package demo

import com.trendyol.kediatr.Mediator
import demo.application.deposit.commands.DepositCommand
import demo.application.transfer.commands.TransferCommand
import demo.domain.Account
import demo.domain.Customer
import demo.domain.IAccountRepository
import demo.domain.IUserRepository
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BankingDemoTests (
    @Autowired private val mediator: Mediator,
    @Autowired val accountRepository: IAccountRepository,
    @Autowired val  userRepository : IUserRepository
) {

    @Test
    fun `Assert deposit was successful`() {
        //TODO potential improvement is to setup in-memory DB and accounts before the tests run.
        val johnDoe = userRepository.save(Customer( "JohnB", "John", "Doe", "example-1@demo.com", "12345"))
        val account = accountRepository.save( Account(balance = 20000.00, type = "savings", owner = johnDoe.id.toString()) )
        val TEST_COMMAND = DepositCommand(2000.00, "ZAR", account.id.toString())

        runBlocking {
            mediator.send(TEST_COMMAND)
        }

        val RESULT = accountRepository.findById(account.id.toString())
        assertThat(RESULT.get().balance).isEqualTo(22000.0)
    }


    @Test
    fun `Assert user can't deposit to a non-existing account`(){

        val TEST_COMMAND = DepositCommand(2000.00, "ZAR", "123456")

        runBlocking {
            assertThrows<Exception> {
                mediator.send(TEST_COMMAND)
            }
        }
    }

    @Test
    fun `Assert transfer was successful`() {
        val johnDoe = userRepository.save(Customer( "JohnB", "John", "Doe", "example-1@demo.com", "12345"))
        val fromAccount = accountRepository.save( Account(balance = 20000.00, type = "savings", owner = johnDoe.id.toString()))

        val blink180 = userRepository.save(Customer( "blink180", "blink", "180", "example-2@demo.com", "12345"))
        val destinationAccount = accountRepository.save( Account(balance = 20000.00, type = "savings", owner = blink180.id.toString()))

        val TEST_COMMAND = TransferCommand(2000.00, destinationAccount.id.toString(), "ZAR", fromAccount.id.toString())

        runBlocking {
            mediator.send(TEST_COMMAND)
        }

        val destinationAccountRESULT = accountRepository.findById(destinationAccount.id.toString());
        val fromAccountRESULT = accountRepository.findById(fromAccount.id.toString());
        assertThat(destinationAccountRESULT.get().balance).isEqualTo(22000.0)
        assertThat(fromAccountRESULT.get().balance).isEqualTo(18000.0)
    }

    @Test
    fun `Assert user can't transfer amount above balance`() {
        val johnDoe = userRepository.save(Customer( "JohnB", "John", "Doe", "example-1@demo.com", "12345"))
        val fromAccount = accountRepository.save( Account(balance = 20000.00, type = "savings", owner = johnDoe.id.toString()))

        val blink180 = userRepository.save(Customer( "blink180", "blink", "180", "example-2@demo.com", "12345"))
        val destinationAccount = accountRepository.save( Account(balance = 20000.00, type = "savings", owner = blink180.id.toString()))

        val TEST_COMMAND = TransferCommand(30000.00, destinationAccount.id.toString(), "ZAR", fromAccount.id.toString())

        runBlocking {
            assertThrows<Exception> {
                mediator.send(TEST_COMMAND)
            }
        }
    }

    @Test
    fun `Assert user can't transfer to none existing account`() {
        val blink180 = userRepository.save(Customer( "blink180", "blink", "180", "example-2@demo.com", "12345"))
        val destinationAccount = accountRepository.save( Account(balance = 20000.00, type = "savings", owner = blink180.id.toString()))

        val TEST_COMMAND = TransferCommand(30000.00, destinationAccount.id.toString(), "ZAR", "12345")

        runBlocking {
            assertThrows<Exception> {
                mediator.send(TEST_COMMAND)
            }
        }
    }
}