package com.mirza.nowmoneytest.network.responses

data class LoginResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val token: String?

)