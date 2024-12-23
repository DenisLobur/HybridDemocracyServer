package com.hybrid.democracy.hybrid.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/hello")
    fun hello(): ResponseEntity<String> {
        return ResponseEntity("Hello, brand new World!", HttpStatus.OK)
    }
}