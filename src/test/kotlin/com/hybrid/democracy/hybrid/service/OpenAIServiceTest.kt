package com.hybrid.democracy.hybrid.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
 class OpenAIServiceTest {

    @Autowired
    private lateinit var openAIService: OpenAIService

    @Test
    fun getOpenAIResponse() {
        val response = openAIService.getOpenAIResponse("What is the capital of France?")
        assertNotNull(response)
        assertTrue(response.isNotBlank())

        println("Got response: $response")
    }
}