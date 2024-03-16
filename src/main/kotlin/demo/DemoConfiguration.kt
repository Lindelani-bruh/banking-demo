package demo

import demo.domain.Account
import demo.domain.Customer
import demo.domain.IAccountRepository
import demo.domain.IUserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DemoConfiguration {

    @Bean
    fun databaseInitializer(userRepository: IUserRepository,
                            accountRepository: IAccountRepository
    ) = ApplicationRunner {

        val johnDoe = userRepository.save(Customer( "JohnB", "John", "Doe", "12345"))
        accountRepository.save(
            Account(
            balance = 20000.00,
            type = "savings",
            owner = johnDoe.id.toString())
        )

        val sbusiso = userRepository.save(Customer("SbusisoN", "Sbusiso", "Dlamini", "789"))
        accountRepository.save(
            Account(
                balance = 20000.00,
                type = "savings",
                owner = sbusiso.id.toString())
        )
    }
}
