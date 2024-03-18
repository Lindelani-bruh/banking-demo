package demo.application.account.commands

import com.trendyol.kediatr.CommandHandler
import demo.domain.Account
import demo.domain.Customer
import demo.domain.IAccountRepository
import demo.domain.IUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class RegisterCommandHandler (
    private val userRepository: IUserRepository,
    private val accounRepository: IAccountRepository,
    private val encoder: PasswordEncoder
)
    : CommandHandler<RegisterCommand> {

    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)

    override suspend fun handle(command: RegisterCommand) {
        getLogger().info("registering account ${command.username}")
        val user = userRepository.save(
            Customer(
            username = command.username,
            firstname = command.firstname,
            lastname = command.lastname,
            email = command.email,
            password =  encoder.encode(command.password))
        )
        accounRepository.save(Account(balance=0.0, type=command.type, owner=user.id.toString() ))
        getLogger().info("account registered ${command.username}")
    }
}
