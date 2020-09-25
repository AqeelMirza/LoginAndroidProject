package com.mirza.nowmoneytest.repo

import com.mirza.nowmoneytest.db.entities.Receiver
import com.mirza.nowmoneytest.network.MyApi
import com.mirza.nowmoneytest.network.SafeApiRequest
import com.mirza.nowmoneytest.network.responses.UpdateReceiverResponse

class ReceiverRepo(
    private val api: MyApi
) : SafeApiRequest() {

    suspend fun getReceiver(auth: String): List<Receiver> {
        return apiRequest { api.getReceiver(auth) }
    }

    suspend fun addReceiver(
        auth: String,
        name: String,
        number: String,
        address: String
    ): UpdateReceiverResponse {
        return apiRequest { api.addReceiver(auth, name, number, address) }
    }

    suspend fun deleteReceiver(auth: String, _id: String): UpdateReceiverResponse {
        return apiRequest { api.deleteReceiver(auth, _id) }
    }


}