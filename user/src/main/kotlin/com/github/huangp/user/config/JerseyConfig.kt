package com.github.huangp.user.config

import com.github.huangp.user.exception.UserExistExceptionMapper
import com.github.huangp.user.service.UserService
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component
import springfox.documentation.swagger2.annotations.EnableSwagger2
import javax.ws.rs.ApplicationPath
import io.swagger.jaxrs.config.BeanConfig
import io.swagger.jaxrs.listing.ApiListingResource
import io.swagger.jaxrs.listing.SwaggerSerializers


@Component
@EnableSwagger2
@ApplicationPath("/api")
class JerseyConfig : ResourceConfig() {
    init {
        register(UserService::class.java)
        register(UserExistExceptionMapper::class.java)
        property(org.glassfish.jersey.server.ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
        configureSwagger()
    }

    private fun configureSwagger() {
        register(ApiListingResource::class.java)
        register(SwaggerSerializers::class.java)
        val beanConfig = BeanConfig()
        beanConfig.version = "1.0"
        beanConfig.schemes = arrayOf("http")
        beanConfig.host = "localhost:8080"
        beanConfig.basePath = "/api"
        beanConfig.resourcePackage = "com.github.huangp.user.service"
        beanConfig.prettyPrint = true
        beanConfig.scan = true
    }
}