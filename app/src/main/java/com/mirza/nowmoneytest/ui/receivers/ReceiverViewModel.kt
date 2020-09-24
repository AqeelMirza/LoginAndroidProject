package com.mirza.nowmoneytest.ui.receivers

import androidx.lifecycle.ViewModel
import com.mirza.nowmoneytest.repo.ReceiverRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReceiverViewModel(
    private val repo: ReceiverRepo
) : ViewModel() {

    suspend fun getReceiver(
        auth: String) = withContext(Dispatchers.IO) { repo.getReceiver(auth) }

}