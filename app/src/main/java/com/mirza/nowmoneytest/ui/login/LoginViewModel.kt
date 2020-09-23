package com.mirza.nowmoneytest.ui.login

import androidx.lifecycle.ViewModel
import com.mirza.nowmoneytest.repo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val repo: UserRepo
) : ViewModel() {

    suspend fun userLogin(
        username: String,
        password: String) = withContext(Dispatchers.IO) { repo.userLogin(username, password) }

}
