package com.github.huangp.email.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Response
import org.springframework.mail.MailException
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import javax.mail.internet.MimeMessage


@Path("mail")
@Consumes("application/json")
@Produces("application/json")
class MailNotificationService @Inject constructor(val javaMailSender: JavaMailSender) {

    @POST
    fun sendNotification(@QueryParam("to") to: String,
                         @QueryParam("from") from: String?,
                         @QueryParam("subject") subject: String,
                         body: List<String>): Response {
        val messagePreparator = MimeMessagePreparator { mimeMessage: MimeMessage ->
            val messageHelper = MimeMessageHelper(mimeMessage)
            messageHelper.setFrom(from?:"no-reply@example.com")
            messageHelper.setTo(to)
            messageHelper.setSubject(subject)
            messageHelper.setText(body.joinToString("\n"))
        }
        return try {
            javaMailSender.send(messagePreparator)
            Response.ok().build()
        } catch (e: MailException) {
            log.error("failed to send mail", e)
            Response.serverError().build()
        }

    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(MailNotificationService::class.java)
    }

}

