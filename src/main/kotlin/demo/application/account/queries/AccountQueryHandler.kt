package demo.application.account.queries

import com.trendyol.kediatr.QueryHandler
import demo.domain.Account
import demo.domain.IAccountRepository
import demo.domain.IUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AccountQueryHandler(private val accountRepository: IAccountRepository, private val userRepository: IUserRepository) :
    QueryHandler<AccountQuery, Iterable<Account>> {

    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)

    override suspend  fun handle(query: AccountQuery): Iterable<Account> {
        var accounts:List<Account> =  listOf()
        val user = userRepository.findByEmail(query.email)

        if (!user.equals(null)){
            accounts = accountRepository.findByOwner(user.id.toString())
            /*accounts.forEach{
                getLogger().info("account :  ${it.owner}, ${it.balance}, account: ${it.id} " )
            }*/
            getLogger().info("Found ${accounts.size} account for: ${query.email}" )
        }
        else {
            getLogger().error("Unable to retrieve account for ${query.email}")
        }
        return accounts
    }
}