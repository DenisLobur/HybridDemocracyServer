package com.hybrid.democracy.hybrid.controller

import com.hybrid.democracy.hybrid.dto.HelloResponse
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    companion object {
        private val logger: Log = LogFactory.getLog(HomeController::class.java)
    }

    @GetMapping("/hello")
    fun hello(): ResponseEntity<HelloResponse> {
        logger.info("hitting /hello endpoint")
        val response = HelloResponse("Hello, brand new World!")
        return ResponseEntity(response, HttpStatus.OK)
    }
}