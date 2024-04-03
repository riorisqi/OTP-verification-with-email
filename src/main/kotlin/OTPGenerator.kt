package com.sendmailtest

import com.bastiaanjansen.otp.HMACAlgorithm
import com.bastiaanjansen.otp.HOTPGenerator
import com.bastiaanjansen.otp.SecretGenerator
import com.bastiaanjansen.otp.TOTPGenerator
import java.time.Duration

class OTPGenerator {
    private val secret: ByteArray = SecretGenerator.generate()

    fun generateOtp(): String{
        val totp: TOTPGenerator = TOTPGenerator.Builder(secret)
            .withHOTPGenerator { builder: HOTPGenerator.Builder ->
                builder.withPasswordLength(6)
                builder.withAlgorithm(HMACAlgorithm.SHA256) // SHA256 and SHA512 are also supported
            }
            .withPeriod(Duration.ofMinutes(1))
            .build()

        return totp.now()
    }
}