package com.example.bancospringmobile.network

interface AuthCallback {
    fun onLoginSuccess()
    fun onLoginFailure()
}