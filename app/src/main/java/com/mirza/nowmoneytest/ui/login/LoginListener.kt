package com.mirza.nowmoneytest.ui.login

interface LoginListener {
    fun onStarted()
    fun onSuccess(userToken: String)
    fun onFailure(message: String)
}