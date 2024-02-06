package com.example.notduo.util

import java.security.MessageDigest

object Hasher {
    fun hashString(input: String): String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
}