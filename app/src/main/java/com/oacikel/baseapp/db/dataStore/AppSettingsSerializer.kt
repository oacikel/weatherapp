package com.oacikel.baseapp.db.dataStore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.crypto.tink.Aead
import com.google.protobuf.InvalidProtocolBufferException
import com.oacikel.baseapp.AppSettings
import java.io.InputStream
import java.io.OutputStream

class AppSettingsSerializer(private val aead: Aead) : Serializer<AppSettings> {
    override suspend fun readFrom(input: InputStream): AppSettings {
        return try {
            val encryptedInput = input.readBytes()

            val decryptedInput = if (encryptedInput.isNotEmpty()) {
                aead.decrypt(encryptedInput, null)
            } else {
                encryptedInput
            }

            AppSettings.parseFrom(decryptedInput)

        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Error deserializing proto", e)
        }
    }

    override suspend fun writeTo(user: AppSettings, output: OutputStream) {
        val encryptedBytes = aead.encrypt(user.toByteArray(), null)

        output.write(encryptedBytes)
    }

    override val defaultValue: AppSettings =
        AppSettings.getDefaultInstance()
}