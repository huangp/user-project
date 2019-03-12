package com.github.huangp.User.exception

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

class UserExistException : RuntimeException("username exists")

@Provider
class UserExistExceptionMapper : ExceptionMapper<UserExistException> {
    override fun toResponse(ex: UserExistException?): Response {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(listOf("user with same username already exists"))
                .build();
    }
}