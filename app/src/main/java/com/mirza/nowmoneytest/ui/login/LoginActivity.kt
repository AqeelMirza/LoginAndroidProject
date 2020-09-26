package com.mirza.nowmoneytest.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.databinding.ActivityLoginBinding
import com.mirza.nowmoneytest.ui.receivers.ReceiverActivity
import com.mirza.nowmoneytest.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: LoginViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.buttonSignIn.setOnClickListener {
            login()
        }

    }

    companion object {
        fun buildToastMessage(msg: String): String {
            return msg
        }
    }

    private fun login() {
        val username = binding.editTextUsername.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            toast(buildToastMessage(getString(R.string.please_enter_values)))
        } else {
            progress_bar.show()
            lifecycleScope.launch {
                try {
                    val loginResp = viewModel.userLogin(username, password)
                    if (loginResp.token != null) {
                        progress_bar.hide()
                        toast(buildToastMessage(getString(R.string.success)))
                        navigateToReceiverActivity(loginResp.token)
                    }
                } catch (e: ApiException) {
                    progress_bar.hide()
                    binding.rootLayout.snackbar(e.message.toString())
                    toast(buildToastMessage(getString(R.string.failure)))
                    e.printStackTrace()
                } catch (e: NoInternetException) {
                    progress_bar.hide()
                    binding.rootLayout.snackbar(e.message.toString())
                    e.printStackTrace()
                }
            }
        }
    }

    fun navigateToReceiverActivity(auth: String) {
        val intent = Intent(this, ReceiverActivity::class.java).apply {
            putExtra(getString(R.string.auth), auth)
        }
        startActivity(intent)
        finish()
    }
}
