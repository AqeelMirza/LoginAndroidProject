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

    //Dependency Injection
    override val kodein by kodein()
    private val factory: LoginViewModelFactory by instance()

    //Databinding
    private lateinit var binding: ActivityLoginBinding

    //Viewmodel
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.buttonSignIn.setOnClickListener {
            loginApi()
        }

    }

    private fun loginApi() {
        val (username, password) = getValues()

        //checking for empty validation
        if (username.isEmpty() || password.isEmpty()) {
            toast(buildToastMessage(getString(R.string.please_enter_values)))
        } else {
            progress_bar.show()
            lifecycleScope.launch {
                try {
                    val loginResp = viewModel.userLogin(username, password)
                    if (loginResp.token != null) {
                        hideProgressBar()
                        toast(buildToastMessage(getString(R.string.success)))
                        navigateToReceiverActivity(loginResp.token)
                    }
                }//Handling error responses
                catch (e: ApiException) {
                    hideProgressBar()
                    displayErrorMessage(e.message.toString())
                    toast(buildToastMessage(getString(R.string.failure)))
                    e.printStackTrace()
                } catch (e: NoInternetException) {
                    hideProgressBar()
                    displayErrorMessage(e.message.toString())
                    e.printStackTrace()
                }
            }
        }
    }

    private fun displayErrorMessage(message: String) {
        binding.rootLayout.snackbar(message)
    }

    private fun hideProgressBar() {
        progress_bar.hide()
    }

    private fun getValues(): Pair<String, String> {
        val username = binding.editTextUsername.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        return Pair(username, password)
    }

    fun navigateToReceiverActivity(auth: String) {
        val intent = Intent(this, ReceiverActivity::class.java).apply {
            putExtra(getString(R.string.auth), auth)
        }
        startActivity(intent)
        finish()
    }

    //to build and test toast message
    companion object {
        fun buildToastMessage(msg: String): String {
            return msg
        }
    }
}
