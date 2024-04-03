package com.sendmailtest

fun main() {
    val otpGenerator = OTPGenerator()
    val mailHandler = MailHandler()
    val cacheConfig = CacheConfig()

    // generate otp code and init cache
    val otpCode = otpGenerator.generateOtp()
    val cache = cacheConfig.cache

    // insert otp code in cache for verification, 3 minutes expiration time
    cache.getUnchecked(otpCode)

    // send otp code to receiver mail
    val sendMailStatus = mailHandler.sendEmail(
        "info@gmail.com",
        "OTP",
        "OTP Code: $otpCode\n" +
        "This OTP valid for 3 minutes.\n\n" +
        "Please do not share this OTP to anyone."
    )

    if(sendMailStatus){
        println("OTP successfully sent, please check your email")
    } else {
        println("OTP failed to send")
    }

    // example code for checking otp code verification after user input otp with otp code in cache
    val getOtpFromCache = cache.getIfPresent("otp")
    val otpFromUserInput = "otp"
    if(otpFromUserInput == getOtpFromCache){
        println("OTP Verified. Login Confirmed")
    } else {
        println("OTP Not Valid. Login Failed")
    }
}