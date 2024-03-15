package demo.application.account.queries

import com.trendyol.kediatr.Query
import domain.entities.Account

class AccountQuery(val id: String) : Query<Iterable<Account>>