package com.mirza.nowmoneytest.repo

import com.mirza.nowmoneytest.network.MyApi
import com.mirza.nowmoneytest.network.SafeApiRequest
import com.mirza.nowmoneytest.network.responses.LoginResponse

class UserRepo(
    private val api: MyApi
) : SafeApiRequest() {

    suspend fun userLogin(username: String, password: String): LoginResponse {
        return apiRequest { api.userLogin(username, password) }
    }
}