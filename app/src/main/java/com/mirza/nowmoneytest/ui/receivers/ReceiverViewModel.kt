package com.mirza.nowmoneytest.ui.receivers

import androidx.lifecycle.ViewModel
import com.mirza.nowmoneytest.db.entities.Receiver
import com.mirza.nowmoneytest.repo.ReceiverRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReceiverViewModel(
    private val repo: ReceiverRepo
) : ViewModel() {

    suspend fun getReceiver(
        auth: String
    ) = withContext(Dispatchers.IO) { repo.getReceiver(auth) }

    suspend fun addReceiver(
        auth: String, name: String, number: String, address: String
    ) = withContext(Dispatchers.IO) {
        repo.addReceiver(auth, name, number, address)
    }

    suspend fun deleteReceiver(
        auth: String, _id: String
    ) = withContext(Dispatchers.IO) { repo.deleteReceiver(auth, _id) }

    suspend fun addAllReceiver(receiverList: List<Receiver>) =
        withContext(Dispatchers.IO) { repo.insert(receiverList) }

    suspend fun deleteReceiverFromDb(receiver: Receiver) = withContext(Dispatchers.IO)
    { repo.delete(receiver) }

    suspend fun getAllReceivers() = withContext(Dispatchers.IO) {
        repo.getAllFromDb()
    }
}