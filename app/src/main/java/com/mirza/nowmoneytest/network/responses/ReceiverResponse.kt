package com.mirza.nowmoneytest.network.responses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mirza.nowmoneytest.db.entities.Receiver

data class ReceiverResponse(
    val receiverResponse: MutableLiveData<Receiver>?
)