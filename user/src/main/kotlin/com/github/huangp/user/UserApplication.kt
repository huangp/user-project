package com.github.huangp.user

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication(
        scanBasePackages = ["com.github.huangp.user.config", "com.github.huangp.user.service"]
)
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

