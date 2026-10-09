package com.umg.vetcare.vetcareapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
object VetcareApiApplication {
    fun main(args: Array<String?>?) {
        SpringApplication.run(VetcareApiApplication::class.java, args)
    }
}
