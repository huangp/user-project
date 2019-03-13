package com.github.huangp.User

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

//@EnableDiscoveryClient
@SpringBootApplication
class UserApplication: SpringBootServletInitializer() {

	companion object {
		val log: Logger = LoggerFactory.getLogger(UserApplication::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            UserApplication().configure(SpringApplicationBuilder(UserApplication::class.java)).run(*args)
//            runApplication<UserApplication>(*args)
        }
	}

}

