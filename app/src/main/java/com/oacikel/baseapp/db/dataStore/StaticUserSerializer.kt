package com.oacikel.baseapp.db.dataStore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.crypto.tink.Aead
import com.google.protobuf.InvalidProtocolBufferException
import com.oacikel.baseapp.StaticUser
import java.io.InputStream
import java.io.OutputStream

class StaticUserSerializer(private val aead: Aead) : Serializer<StaticUser> {
    override suspend fun readFrom(input: InputStream): StaticUser {
        return try {
            val encryptedInput = input.readBytes()

            val decryptedInput = if (encryptedInput.isNotEmpty()) {
                aead.decrypt(encryptedInput, null)
            } else {
                encryptedInput
            }

            StaticUser.parseFrom(decryptedInput)

        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Error deserializing proto", e)
        }
    }

    override suspend fun writeTo(user: StaticUser, output: OutputStream) {
        val encryptedBytes = aead.encrypt(user.toByteArray(), null)

        output.write(encryptedBytes)
    }

    override val defaultValue: StaticUser =
        StaticUser.getDefaultInstance()
}