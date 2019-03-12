package com.github.huangp.email.config

import com.github.huangp.email.service.MailNotificationService
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component

@Component
class JerseyConfig : ResourceConfig() {
    init {
        register(MailNotificationService::class.java)
    }
}