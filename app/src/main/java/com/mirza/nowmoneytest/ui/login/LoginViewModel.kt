package com.mirza.nowmoneytest.ui.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.mirza.nowmoneytest.repo.UserRepo
import com.mirza.nowmoneytest.util.ApiException
import com.mirza.nowmoneytest.util.Coroutines
import com.mirza.nowmoneytest.util.NoInternetException

class LoginViewModel(
    private val repo: UserRepo
) : ViewModel() {

    var username: String? = null
    var password: String? = null

    var loginListener: LoginListener? = null

    fun onLoginButtonClick(view: View) {
        loginListener?.onStarted()
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            loginListener?.onFailure("Invalid values")
            return
        }
        Coroutines.main {
            try {
                val response = repo.userLogin(username!!, password!!)
                response.token?.let {
                    loginListener?.onSuccess(it)
                    return@main
                }
                loginListener?.onFailure(response.message!!)
            } catch (e: ApiException) {
                loginListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                loginListener?.onFailure(e.message!!)
            }

        }

    }

}