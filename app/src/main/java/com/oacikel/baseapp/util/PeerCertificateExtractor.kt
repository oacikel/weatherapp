package com.oacikel.baseapp.util

import android.content.Context
import android.util.Base64
import com.oacikel.baseapp.util.enums.CertificateEnum
import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

object PeerCertificateExtractor {

    fun extract(context: Context): String {

        var inputStream: InputStream? = null

        try {
            inputStream = context.assets.open(CertificateEnum.PROD.description!!)

            val x509Certificate = CertificateFactory.getInstance("X509")
                .generateCertificate(inputStream) as X509Certificate

            val publicKeyEncoded = x509Certificate.publicKey.encoded
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val publicKeySha256 = messageDigest.digest(publicKeyEncoded)
            val publicKeyShaBase64 = Base64.encode(publicKeySha256, 0)

            return "sha256/" + String(publicKeyShaBase64)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return ""
    }
}