package com.github.huangp.email

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class EmailApplication: SpringBootServletInitializer() {

}

fun main(args: Array<String>) {
    runApplication<EmailApplication>(*args)
}
