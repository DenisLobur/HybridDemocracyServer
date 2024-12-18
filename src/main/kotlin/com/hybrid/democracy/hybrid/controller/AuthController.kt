package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.security.JwtUtil
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
    private val userDetailsService: UserDetailsService
) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<*> {
        val authenticationManager = authenticationManagerProvider.getIfAvailable()
        authenticationManager?.authenticate(
            UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
        )

        val userDetails: UserDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val jwt: String = jwtUtil.generateToken(userDetails.username)

        return ResponseEntity.ok(AuthenticationResponse(jwt))
    }
}

data class AuthenticationRequest(val username: String, val password: String)
data class AuthenticationResponse(val jwt: String)