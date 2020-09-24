package com.mirza.nowmoneytest

import android.app.Application
import com.mirza.nowmoneytest.network.MyApi
import com.mirza.nowmoneytest.network.NetworkConnectionInterceptor
import com.mirza.nowmoneytest.repo.ReceiverRepo
import com.mirza.nowmoneytest.repo.UserRepo
import com.mirza.nowmoneytest.ui.login.LoginViewModelFactory
import com.mirza.nowmoneytest.ui.receivers.ReceiverViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NowApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@NowApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
       // bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepo(instance()) }
        bind() from provider { LoginViewModelFactory(instance()) }
        bind() from provider { ReceiverRepo(instance()) }
        bind() from provider { ReceiverViewModelFactory(instance()) }


    }
}