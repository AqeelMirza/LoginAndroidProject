package com.mirza.nowmoneytest.ui.receivers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirza.nowmoneytest.repo.ReceiverRepo

@Suppress("UNCHECKED_CAST")
class ReceiverViewModelFactory(
        private val repo: ReceiverRepo
    ): ViewModelProvider.NewInstanceFactory(){

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ReceiverViewModel(repo) as T
        }
    }