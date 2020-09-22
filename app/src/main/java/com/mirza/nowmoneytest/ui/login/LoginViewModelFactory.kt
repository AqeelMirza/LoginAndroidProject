package com.mirza.nowmoneytest.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirza.nowmoneytest.repo.UserRepo

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory (
    private val repo: UserRepo
): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repo) as T
    }
}