package com.alfaradev.notification

import android.app.Application
import com.alfaradev.notification.repositories.FcmRepository
import com.alfaradev.notification.repositories.FirebaseRepository
import com.alfaradev.notification.repositories.HistoryRepository
import com.alfaradev.notification.ui.history.HistoryViewModel
import com.alfaradev.notification.ui.home.HomeViewModel
import com.alfaradev.notification.webservices.ApiClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.core.logger.Level


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            modules(listOf(repositoryModules, viewModelModules, retrofitModule))
        }
    }
}

val retrofitModule = module {
    single { ApiClient.instance() }
    single { FirebaseRepository() }
}

val repositoryModules = module {
    factory { FcmRepository(get()) }
    factory { HistoryRepository(get()) }
}

val viewModelModules = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { HistoryViewModel(get()) }
}