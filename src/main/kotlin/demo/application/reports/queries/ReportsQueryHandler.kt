package demo.application.reports.queries

import com.trendyol.kediatr.QueryHandler
import demo.domain.ITransactionRepository
import demo.domain.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ReportsQueryHandler(private val transactionRepository: ITransactionRepository) :
    QueryHandler<ReportsQuery, Iterable<Transaction>> {
    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)
    override suspend  fun handle(query: ReportsQuery): Iterable<Transaction> {
        getLogger().info("invoked reporting.")
        val transactions = transactionRepository.findAll().toList()
        return transactions
    }
}