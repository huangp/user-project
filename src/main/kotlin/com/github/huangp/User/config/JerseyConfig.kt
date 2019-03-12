package com.github.huangp.User.config

import com.github.huangp.User.exception.UserExistExceptionMapper
import com.github.huangp.User.service.UserService
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component

@Component
class JerseyConfig : ResourceConfig() {
    init {
        register(UserService::class.java)
        register(UserExistExceptionMapper::class.java)
        property(org.glassfish.jersey.server.ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
}