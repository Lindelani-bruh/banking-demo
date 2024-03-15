package demo.application.account.queries

import com.trendyol.kediatr.QueryHandler
import demo.domain.IAccountRepository
import demo.domain.IUserRepository
import domain.entities.Account
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.toList


@Component
class AccountQueryHandler(private val accountRepository: IAccountRepository, private val userRepository: IUserRepository) :
    QueryHandler<AccountQuery, Iterable<Account>> {

    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)

    override suspend  fun handle(query: AccountQuery): Iterable<Account> {
        /*4acefeec-fa57-49db-968a-3655f487250d owner 593d0e96-c6bc-484b-8fd7-706b28eeb0b1*/
        getLogger().info("get-account account ${query.id}" )
        return accountRepository.findById(query.id).toList()
    }
}