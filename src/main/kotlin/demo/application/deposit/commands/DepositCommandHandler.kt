package demo.application.deposit.commands

import com.trendyol.kediatr.CommandHandler
import demo.domain.IAccountRepository
import demo.domain.ITransactionRepository
import domain.entities.Account
import domain.entities.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DepositCommandHandler (private val accountRepository: IAccountRepository, private val transactionRepository: ITransactionRepository): CommandHandler<DepositCommand> {


    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)

    override suspend fun handle(command: DepositCommand) {
        // TODO convert with some currency exchange thing.
        getLogger().info("deposit-amount account ${command.amount} : owner ${command.amount}")
        if(command.amount > 0){
            val account = accountRepository.findById(command.accountId)
            accountRepository.save(
                Account(
                    id=account.get().id,
                    balance=account.get().balance + command.amount,
                    type = account.get().type,
                    owner=account.get().owner ))
            transactionRepository.save(
                Transaction(null,
                    owner = account.get().owner,
                    amount = command.amount ))
        } else {
            getLogger().error("Unable to deposit funds.")
            throw Exception("Unable to deposit funds.")
        }

    }
}
