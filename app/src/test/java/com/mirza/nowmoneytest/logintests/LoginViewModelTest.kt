package com.mirza.nowmoneytest.logintests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mirza.nowmoneytest.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.newInstance

class LoginViewModelTest() : KodeinAware {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    override val kodein: Kodein = Kodein.lazy {
        import(LoginViewModelModule)
    }

    @Test
    fun login() {
        // given
        val username = "aqeel"
        val password = "AqeeL123"
        val viewModel by kodein.newInstance { LoginViewModel(instance()) }
        GlobalScope.launch(Dispatchers.IO) {
            // when
            val res = viewModel.userLogin(username, password)

            // then
            Assert.assertNotNull(res.token)
        }
    }
}