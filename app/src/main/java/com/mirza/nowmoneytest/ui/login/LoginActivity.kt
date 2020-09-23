package com.mirza.nowmoneytest.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.databinding.ActivityLoginBinding
import com.mirza.nowmoneytest.util.hide
import com.mirza.nowmoneytest.util.show
import com.mirza.nowmoneytest.util.snackbar
import com.mirza.nowmoneytest.util.toast
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity: AppCompatActivity(), LoginListener,KodeinAware {

    override val kodein by kodein()
    private val factory: LoginViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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