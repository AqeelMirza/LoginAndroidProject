package com.mirza.nowmoneytest.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.databinding.ActivityLoginBinding
import com.mirza.nowmoneytest.network.MyApi
import com.mirza.nowmoneytest.network.NetworkConnectionInterceptor
import com.mirza.nowmoneytest.repo.UserRepo
import com.mirza.nowmoneytest.util.hide
import com.mirza.nowmoneytest.util.toast
import com.mirza.nowmoneytest.util.show
import com.mirza.nowmoneytest.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val repo = UserRepo(api)
        val factory = LoginViewModelFactory(repo)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.loginListener = this
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(loginResponse: String) {
        progress_bar.hide()
        //root_layout.snackbar("Token: ${loginResponse}")
        toast("Token - ${loginResponse}")
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar("${message}")

    }
}