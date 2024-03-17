package demo.api.contollers

import com.trendyol.kediatr.Mediator
import demo.application.account.commands.RegisterCommand
import demo.application.account.queries.AccountQuery
import demo.application.deposit.commands.DepositCommand
import demo.application.reports.queries.ReportsQuery
import demo.application.transfer.commands.TransferCommand
import demo.domain.Account
import demo.domain.Transaction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class BankingController (private val mediator: Mediator) {

    companion object {
        const val DEPOSIT_PATH = "/deposit"
        const val TRANSFER_PATH = "/transfer"
        const val ACCOUNT_PATH = "/account"
        const val REPORT_PATH = "/reports"
    }

    @PostMapping("$DEPOSIT_PATH/{accountid}")
    fun deposit(@RequestBody command: DepositContract, @PathVariable accountid:String) {
        runBlocking {
            mediator.send(DepositCommand(command.amount, command.currency, accountid ))
        }
    }


    @PostMapping("$TRANSFER_PATH/{accountid}")
    fun transfer(@RequestBody command: TransferContract, @PathVariable accountid:String) {
        runBlocking {
            mediator.send(TransferCommand(command.amount, command.destinationAccount, command.currency, accountid ))
        }
    }

    @PostMapping(ACCOUNT_PATH)
    fun account(@RequestBody command: AccountContract) {
        runBlocking {
            mediator.send(
                RegisterCommand(command.username,
                    command.firstname,
                    command.lastname,
                    command.password,
                    command.email,
                    command.type)
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @GetMapping("$ACCOUNT_PATH/{accountid}")
    fun getAccount(@PathVariable accountid: String) : Iterable<Account>{

        var data = runBlocking {
            mediator.send(AccountQuery(accountid))
        }

        return data
    }

    @GetMapping(REPORT_PATH)
    fun reports() : Iterable<Transaction> {
        val data = runBlocking {
            mediator.send(ReportsQuery())
        }
        return data
    }
}


// Contracts
class DepositContract (
    val amount: Double,
    val currency: String)

class AccountContract (
    val username: String,
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String,
    var type: String)

class TransferContract (
    val amount: Double,
    val currency: String,
    val destinationAccount:String)
