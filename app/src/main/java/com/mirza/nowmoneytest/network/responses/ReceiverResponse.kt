package com.mirza.nowmoneytest.network.responses

import androidx.lifecycle.LiveData
import com.mirza.nowmoneytest.db.entities.Receiver

data class ReceiverResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val receiverResponse: LiveData<List<Receiver>>?
)