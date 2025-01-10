package com.unitip.mobile.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PickRoleViewModel @Inject constructor(
    private val role: String
) : ViewModel() {

    fun loginWithRole(
        email: String,
        password: String,
        role: String
    ){

    }
}