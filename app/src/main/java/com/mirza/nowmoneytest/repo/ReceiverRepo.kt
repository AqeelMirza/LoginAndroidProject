package com.mirza.nowmoneytest.repo

import com.mirza.nowmoneytest.db.AppDatabase
import com.mirza.nowmoneytest.db.entities.Receiver
import com.mirza.nowmoneytest.network.MyApi
import com.mirza.nowmoneytest.network.SafeApiRequest
import com.mirza.nowmoneytest.network.responses.UpdateReceiverResponse

class ReceiverRepo(
    private val api: MyApi,
    private val db: AppDatabase
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
        return apiRequest {
            api.deleteReceiver(auth, _id)
        }
    }

    suspend fun insert(receiverList: List<Receiver>) {
        return db.getReceiverDao().insertReceiver(receiverList)
    }

    suspend fun delete(receiver: Receiver) {
        return db.getReceiverDao().deleteReceiver(receiver)
    }

    suspend fun getAllFromDb(): List<Receiver> {
        return db.getReceiverDao().getAllReceivers()
    }
}