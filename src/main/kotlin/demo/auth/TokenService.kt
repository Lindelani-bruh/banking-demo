package demo.auth

import demo.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    jwtProperties: JwtProperties
) {

    fun getLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)

    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )
    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()
    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
        var results = userDetails.username == email && !isExpired(token)
        getLogger().info("Token is valid: ${results}")
        getLogger().info("User details username ${ userDetails.username }, Extracted from email : ${email}")
        return results
    }
    fun extractEmail(token: String): String? =
        getAllClaims(token)
            .subject
    fun isExpired(token: String): Boolean =
        getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))
    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()
        return parser
            .parseSignedClaims(token)
            .payload
    }
}