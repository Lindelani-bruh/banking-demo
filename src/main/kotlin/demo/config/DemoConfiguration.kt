package demo.config

import demo.application.auth.CustomUserDetailsService
import demo.domain.*
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class DemoConfiguration {

    @Bean
    fun userDetailsService(userRepository: IUserRepository): UserDetailsService =
        CustomUserDetailsService(userRepository)

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(userRepository: IUserRepository): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(userRepository))
                it.setPasswordEncoder(encoder())
            }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun databaseInitializer(userRepository: IUserRepository,
                            accountRepository: IAccountRepository,
                            encoder: PasswordEncoder
    ) = ApplicationRunner {

        val johnDoe = userRepository.save(Customer(
            username = "JohnB",
            firstname = "John",
            lastname = "Doe",
            email = "example1@demo.com",
            password = encoder.encode("12345"),
            role = Role.USER
            ))
        accountRepository.save(
            Account(
            balance = 20000.00,
            type = "savings",
            owner = johnDoe.id.toString())
        )

        val sbusiso = userRepository.save(Customer(
            username = "SbusisoN",
            firstname = "Sbusiso",
            lastname = "Dlamini",
            email = "example2@demo.com",
            password = encoder.encode("12345"),
            role = Role.USER))
        accountRepository.save(
            Account(
                balance = 20000.00,
                type = "savings",
                owner = sbusiso.id.toString())
        )
    }
}