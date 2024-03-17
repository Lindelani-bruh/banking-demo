package demo.application.account.queries

import com.trendyol.kediatr.QueryHandler
import demo.domain.Account
import demo.domain.IAccountRepository
import demo.domain.IUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.toList


@Component
class AccountQueryHandler(private val accountRepository: IAccountRepository, private val userRepository: IUserRepository) :
    QueryHandler<AccountQuery, Iterable<Account>> {

    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)

    override suspend  fun handle(query: AccountQuery): Iterable<Account> {
        val accounts = accountRepository.findAll().toList()
        accounts.forEach{
            getLogger().info("account :  ${it.owner}, ${it.balance}, account: ${it.id} " )
        }

        getLogger().info("get-account ${query.id}" )
        return accountRepository.findById(query.id).toList()
    }
}