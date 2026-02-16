package com.themarto.melichallenge

import android.app.Application
import com.themarto.features.productDetails.ProductDetailsModuleProvider
import com.themarto.features.search.SearchModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                SearchModuleProvider.getModules()
                    .plus(ProductDetailsModuleProvider.getModules())
            )
        }
    }
}