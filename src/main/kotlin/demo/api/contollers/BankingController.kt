package demo.api.contollers

import com.trendyol.kediatr.Mediator
import demo.application.account.commands.RegisterCommand
import demo.application.account.queries.AccountQuery
import demo.application.deposit.commands.DepositCommand
import demo.application.reports.queries.ReportsQuery
import demo.application.transfer.commands.TransferCommand
import domain.entities.Account
import domain.entities.Transaction
import org.springframework.web.bind.annotation.*


@RestController
class BankingController (private val mediator: Mediator) {

    companion object {
        const val DEPOSIT_PATH = "/deposit"
        const val TRANSFER_PATH = "/transfer"
        const val ACCOUNT_PATH = "/account"
        const val REPORT_PATH = "/reports"
    }



    @PostMapping("$DEPOSIT_PATH/{accountid}")
    suspend  fun deposit(@RequestBody command: DepositContract, @PathVariable accountid:String) {
        mediator.send(DepositCommand(command.amount, command.currency, accountid ))
    }


    @PostMapping("$TRANSFER_PATH/{accountid}")
    suspend fun transfer(@RequestBody command: TransferContract, @PathVariable accountid:String) {
        mediator.send(TransferCommand(command.amount, command.destinationAccount, command.currency, accountid ))
    }

    @PostMapping(ACCOUNT_PATH)
    suspend fun account(@RequestBody command: AccountContract) {
        mediator.send(
            RegisterCommand(command.username,
                command.firstname,
                command.lastname,
                command.password,
                command.type))
    }

    @GetMapping("$ACCOUNT_PATH/{accountid}")
    suspend fun getAccount(@PathVariable accountid: String) : Iterable<Account> {
        val account = mediator.send(AccountQuery(accountid))
        return account
    }

    @GetMapping(REPORT_PATH)
    suspend fun reports() : Iterable<Transaction> {
        val reports = mediator.send(ReportsQuery())
        return reports
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
    var password: String,
    var type: String)

class TransferContract (
    val amount: Double,
    val currency: String,
    val destinationAccount:String)
