package com.github.huangp.email

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class EmailApplication: SpringBootServletInitializer() {

    companion object {
        val log: Logger = LoggerFactory.getLogger(EmailApplication::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
//            EmailApplication().configure(SpringApplicationBuilder(EmailApplication::class.java)).run(*args)
            runApplication<EmailApplication>(*args)
        }
    }

}

fun main(args: Array<String>) {
//            EmailApplication().configure(SpringApplicationBuilder(EmailApplication::class.java)).run(*args)
    runApplication<EmailApplication>(*args)
}
