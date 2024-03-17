package demo

import com.trendyol.kediatr.Mediator
import demo.application.deposit.commands.DepositCommand
import demo.domain.*
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BankingDemoTests (
    @Autowired private val mediator: Mediator,
    @Autowired val accountRepository: IAccountRepository,
    @Autowired val transactionRepository: ITransactionRepository,
    @Autowired val  userRepository : IUserRepository
) {

    @Test
    fun `Assert deposit was successful, status code`() {

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
    fun `Assert transfer was successful, status code`() {

        /*val johnDoe = userRepository.save(Customer( "JohnB", "John", "Doe", "12345"))
        var account = accountRepository.save( Account(balance = 20000.00, type = "savings", owner = johnDoe.id.toString()) )
        val TEST_COMMAND = DepositCommand(2000.00, "ZAR", account.id.toString())

        runBlocking {
            mediator.send(TEST_COMMAND)
        }

        val results = accountRepository.findById(account.id.toString());
        assertThat(results.get().balance).isEqualTo(22000.0)*/
    }
}