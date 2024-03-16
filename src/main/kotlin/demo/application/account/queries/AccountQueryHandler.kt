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
        /*
        85440e42-265f-48d3-bf3e-8f4e32db08b8 owner 7751ee34-5580-4720-a5ca-5ce4974e2015
        ade9c37-29a1-4713-9dcb-12010a2fc332  owner 7cbc115b-7d49-4a54-ad1f-a1d0fc06bcc6
        */
        getLogger().info("get-account ${query.id}" )
        return accountRepository.findById(query.id).toList()
    }
}