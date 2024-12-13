package com.unitip.mobile.features.auth.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() {
    fun login(
        email: String,
        password: String,
    ) {

    }

    fun register(
        name: String,
        email: String,
        password: String,
    ) {

    }

    fun logout() {

    }
}