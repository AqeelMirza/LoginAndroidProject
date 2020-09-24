package com.mirza.nowmoneytest.repo

import com.mirza.nowmoneytest.network.MyApi
import com.mirza.nowmoneytest.network.SafeApiRequest
import com.mirza.nowmoneytest.network.responses.ReceiverResponse

class ReceiverRepo(
    private val api: MyApi
) : SafeApiRequest() {

    suspend fun getReceiver(auth: String): List<ReceiverResponse> {
        return apiRequest { api.getReceiver(auth) }
    }
}