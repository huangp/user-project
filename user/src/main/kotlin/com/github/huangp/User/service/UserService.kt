package com.github.huangp.User.service

import com.github.huangp.User.exception.UserExistException
import com.github.huangp.User.model.AppUser
import com.github.huangp.User.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.validation.Errors
import java.net.URI
import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("user")
@Consumes("application/json")
@Produces("application/json")
class UserService @Inject constructor(val userRepository: UserRepository) {

    @Context
    lateinit var uriInfo: UriInfo

    @POST
    fun register(@Valid user: AppUser): Response {

        val existing = user.username?.let {
            userRepository.findByUsername(it)
        }
        return if (existing == null) {
            log.info("creating new user: {}", user)
            // TODO pahuang password hash
            user.active = true
            val saved = userRepository.save(user)
            Response.created(URI("${uriInfo.baseUri}/${saved.username}")).entity(saved).build()
        } else {
            log.error("username already exists")
            throw UserExistException()
        }
    }

    @PUT
    fun edit(@Valid user: AppUser): Response {
        val existing = user.username?.let {
            userRepository.findByUsername(it)
        }
        return if (existing == null) {
            Response.status(Response.Status.BAD_REQUEST)
                    .entity(listOf("user does not exist")).build()
        } else {
            log.info("editing user: {} -> {}", existing, user)
            with(existing) {
                email = user.email
                name = user.name
                passwordHash = user.passwordHash

            }
            val saved = userRepository.save(existing)
            Response.ok(saved).build()
        }
    }

    @DELETE
    @Path("/{username}")
    fun delete(@PathParam("username") username: String): Response {
        val found = userRepository.findByUsername(username)
        return if (found == null) {
            log.info("{} not found", username)
            Response.notModified().build()
        } else {
            log.info("{} soft delete user: {}", username)
            found.active = false
            userRepository.save(found)
            Response.ok().build()
        }
    }

    @DELETE
    fun deleteAll(usernames: Set<String>): Response {
        val users = userRepository.findAllByUsernameIn(usernames).map {
            it.softDelete()
        }
        userRepository.saveAll(users)
        return Response.ok(listOf("${users.size} of users deleted")).build()
    }

    @GET
    fun getAll(): List<AppUser> {
        return userRepository.findAllByActive()
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(UserService::class.java)
    }
}