package demo.application.account.queries

import com.trendyol.kediatr.QueryHandler
import demo.domain.Account
import demo.domain.IAccountRepository
import demo.domain.IUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountQueryHandler(private val accountRepository: IAccountRepository, private val userRepository: IUserRepository) :
    QueryHandler<AccountQuery, Iterable<Account>> {

    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)

    override suspend  fun handle(query: AccountQuery): Iterable<Account> {
        var accounts:Optional<List<Account>> =  Optional.of(listOf())
        val user = userRepository.findByEmail(query.email)

        if (!user.isEmpty){
            accounts = accountRepository.findByOwner(user.get().id.toString())
            getLogger().info("Found ${accounts.get().size} account for: ${query.email}" )
        }
        else {
            getLogger().error("Unable to retrieve account for ${query.email}")
        }
        return accounts.get()
    }
}