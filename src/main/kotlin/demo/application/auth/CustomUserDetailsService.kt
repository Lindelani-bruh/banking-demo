package demo.application.auth

import demo.domain.IUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = demo.domain.Customer
@Service
class CustomUserDetailsService (private val userRepository: IUserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {

        return userRepository.findByEmail(username).get().mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role?.name)
            .build()

}