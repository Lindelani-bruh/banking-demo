package demo.application.account.queries

import com.trendyol.kediatr.Query
import demo.domain.Account

class AccountQuery(val id: String) : Query<Iterable<Account>>