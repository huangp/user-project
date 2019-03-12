package com.github.huangp.User.repository

import com.github.huangp.User.model.AppUser
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<AppUser, Long>{
    fun findByUsername(username: String): AppUser?

    fun findAllByUsernameIn(usernames: Iterable<String>): List<AppUser>

    fun findAllByActive(active: Boolean = true): List<AppUser>
}