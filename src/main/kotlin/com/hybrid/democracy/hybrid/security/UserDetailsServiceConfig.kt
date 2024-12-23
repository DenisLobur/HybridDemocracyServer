package com.hybrid.democracy.hybrid.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
class UserDetailsServiceConfig {

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val password: String = "password"
        val encodedPassword: String = passwordEncoder.encode(password)
        val user: UserDetails = User.withUsername("user")
            .password(encodedPassword)
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(user)
    }
}