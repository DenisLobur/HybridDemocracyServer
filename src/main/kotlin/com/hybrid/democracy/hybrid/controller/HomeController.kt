package com.hybrid.democracy.hybrid.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
    @GetMapping("/hello")
    fun hello(): String {
        return "Hello, World!"
    }
}