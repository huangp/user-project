package com.github.huangp.user.repository

import com.github.huangp.user.model.AppUser
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<AppUser, Long>{
    fun findByUsername(username: String): AppUser?

    fun findAllByUsernameIn(usernames: Iterable<String>): List<AppUser>

    fun findAllByActive(active: Boolean = true): List<AppUser>
}