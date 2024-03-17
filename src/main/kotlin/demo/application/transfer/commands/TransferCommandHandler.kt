package demo.application.transfer.commands

import com.trendyol.kediatr.CommandHandler
import demo.domain.Account
import demo.domain.IAccountRepository
import demo.domain.ITransactionRepository
import demo.domain.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class TransferCommandHandler (
    private val accountRepository: IAccountRepository,
    private val transactionRepository: ITransactionRepository
) : CommandHandler<TransferCommand> {

    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)

    override suspend fun handle(command: TransferCommand) {
        val from = accountRepository.findById(command.fromAccount);
        val to = accountRepository.findById(command.destinationAccount);

        //TODO: do something with the currency.
        if(!from.isEmpty && isAbleToTransfer(command.amount, from.get(), to.get())){
            getLogger().info("Transfering funds from ${command.fromAccount} to ${command.destinationAccount}.")
            //Send money
            accountRepository.save(
                Account(
                balance = to.get().balance + command.amount,
                type = to.get().type,
                owner = to.get().owner,
                id=to.get().id,)
            )

            //Credit sender account
            accountRepository.save(
                Account(
                balance = from.get().balance - command.amount,
                type = from.get().type,
                owner = from.get().owner,
                id = from.get().id)
            )

            transactionRepository.save(Transaction( owner = to.get().owner, amount = command.amount ))
            transactionRepository.save(Transaction(owner =from.get().owner, amount = -command.amount ))
        }  else {
            getLogger().error("Unable to transfer funds.")
            throw Exception("Unable to transfer funds.")
        }
    }

    private fun isAbleToTransfer(amountToTransfer: Double, from: Account, to: Account):Boolean{
        return (from.balance >= amountToTransfer && from.balance >= amountToTransfer)
    }
}