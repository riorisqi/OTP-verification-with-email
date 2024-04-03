package com.sendmailtest

import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailHandler{
    private val settingProps = getSetting()

    // get mail config from setting.properties
    private val emailSMTPServer: String = settingProps.getProperty("mail.host")
    private val emailServerPort: String = settingProps.getProperty("mail.port")
    private val senderEmail: String = settingProps.getProperty("mail.username")
    private val senderPassword: String = settingProps.getProperty("mail.password")

    // Mail server properties
    fun sendEmail(receiverEmail: String, subject: String, body: String): Boolean{
        val props = Properties()
        props["mail.smtp.user"] = senderEmail
        props["mail.smtp.host"] = emailSMTPServer
        props["mail.smtp.port"] = emailServerPort
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"

        // proxy
//        props["mail.smtp.proxy.host"] = ""
//        props["mail.smtp.proxy.port"] = ""
//        props["mail.smtp.proxy.user"] = ""
//        props["mail.smtp.proxy.password"] = ""

        try {
            val auth: Authenticator = SMTPAuthenticator()
            val session = Session.getInstance(props, auth)
            session.debug = true

            val msg = MimeMessage(session)
            msg.setText(body)
            msg.subject = subject
            msg.setFrom(InternetAddress(senderEmail))
            msg.addRecipient(Message.RecipientType.TO, InternetAddress(receiverEmail))
            Transport.send(msg)
            println("Message sent successfully")

            return true
        } catch (e: Exception) {
            e.printStackTrace()

            return false
        }
    }

    private inner class SMTPAuthenticator : Authenticator() {
        public override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(senderEmail, senderPassword)
        }
    }

    private fun getSetting(): Properties {
        var mInputStream: InputStream? = null
        val mProperties = Properties()
        try {
            val settingPath = "${System.getProperty("user.dir")}/setting.properties"
            mInputStream = FileInputStream(settingPath)
            mProperties.load(mInputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mInputStream?.close()
        }

        return mProperties
    }
}
