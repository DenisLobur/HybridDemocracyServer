package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.security.JwtUtil
import com.hybrid.democracy.hybrid.service.CitizenService
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController
class AuthController @Autowired constructor(
    private val authenticationManagerProvider: ObjectProvider<AuthenticationManager>,
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService,
    private val citizenService: CitizenService
) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<*> {
        val email = authenticationRequest.email
        val password = authenticationRequest.password

        val citizen = citizenService.authenticate(email, password)

        return if (citizen != null) {
            val authenticationManager = authenticationManagerProvider.getIfAvailable()
            authenticationManager?.authenticate(
                UsernamePasswordAuthenticationToken("user", "password")
            )

            val userDetails: UserDetails = userDetailsService.loadUserByUsername("user")
            val jwt: String = jwtUtil.generateToken(userDetails.username)

            ResponseEntity.ok(AuthenticationResponse(jwt))
        } else {
            ResponseEntity.status(401).body("No such user")
        }
    }
}

data class AuthenticationRequest(val email: String, val password: String)
data class AuthenticationResponse(val jwt: String)