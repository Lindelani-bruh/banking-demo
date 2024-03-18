package demo.application.deposit.commands

import com.trendyol.kediatr.CommandHandler
import demo.domain.Account
import demo.domain.IAccountRepository
import demo.domain.ITransactionRepository
import demo.domain.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DepositCommandHandler (private val accountRepository: IAccountRepository, private val transactionRepository: ITransactionRepository): CommandHandler<DepositCommand> {
    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)

    override suspend fun handle(command: DepositCommand) {
        // TODO convert with some currency exchange thing.
        getLogger().info("Processing deposit amount ${command.amount} to owner ${command.accountId}")
        val account = accountRepository.findById(command.accountId)

        if(command.amount > 0 && !account.isEmpty){

            accountRepository.save(
                Account(
                    balance=account.get().balance + command.amount,
                    type = account.get().type,
                    owner=account.get().owner,
                    id=account.get().id)
            )
            transactionRepository.save(
                Transaction(
                    owner = account.get().owner,
                    amount = command.amount)
            )
        } else {
            getLogger().error("Unable to deposit funds.")
            throw Exception("Unable to deposit funds.")
        }
    }
}
