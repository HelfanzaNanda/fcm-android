package com.alfaradev.notification.repositories

import com.alfaradev.notification.utils.SingleResponse
import com.google.firebase.messaging.FirebaseMessaging

interface FirebaseContract{
    fun generateToken(listener : SingleResponse<String>)
}

class FirebaseRepository : FirebaseContract{
    override fun generateToken(listener: SingleResponse<String>) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    it.result?.let {result->
                        listener.onSuccess(result)
                    } ?: kotlin.run { listener.onFailure(Error("Failed to get firebase token")) }
                }
                else -> {
                    println("firebase onfailure "+it.exception?.message)
                    listener.onFailure(Error("Cannot get firebase token"))
                }
            }
        }
    }
}