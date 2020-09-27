package com.mirza.nowmoneytest.logintests

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val LoginViewModelModule = Kodein.Module("LoginViewModel") {
    bind<() -> Boolean>() with singleton { { true } }
}