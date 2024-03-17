package demo.auth

import demo.domain.IUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = demo.domain.Customer
@Service
class CustomUserDetailsService (private val userRepository: IUserRepository): UserDetailsService {
    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)
    override fun loadUserByUsername(username: String): UserDetails {

        var value = userRepository.findByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")
        getLogger().info("User foundByEmail: ${value.username}, ${value.authorities}")
        return  value
    }


    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role?.name)
            .build()

}