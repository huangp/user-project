package com.github.huangp.User

import com.github.huangp.User.model.AppUser
import com.github.huangp.User.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean


@SpringBootApplication
open class UserApplication: SpringBootServletInitializer() {


    @Bean
    fun createDummyData(repository: UserRepository): AppUser {
        repository.save(AppUser("Patrick Huang", "pahuang", "abc1234", "pahuang@test.com"))
        repository.save(AppUser("Jack Smith", "jsmith", "abc1235", "jsmith@test.com"))

        // fetch all users
        log.info("users found with findAll():")
        log.info("-------------------------------")
        for (customer in repository.findAll()) {
            log.info(customer.toString())
        }
        log.info("")

        // fetch an individual user by ID
        repository.findById(1L)
                .ifPresent { customer ->
                    log.info("Customer found with findById(1L):")
                    log.info("--------------------------------")
                    log.info(customer.toString())
                    log.info("")
                }

        // fetch customers by username
        log.info("Customer found with findByUsername('pahuang'):")
        log.info("--------------------------------------------")
        val username = repository.findByUsername("pahuang")
        log.info("find by username {}", username)

        log.info("")
        return AppUser()
    }

	companion object {
		val log: Logger = LoggerFactory.getLogger(UserApplication::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            UserApplication().configure(SpringApplicationBuilder(UserApplication::class.java)).run(*args)
//            runApplication<UserApplication>(*args)
        }
	}

}

