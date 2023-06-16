package com.theideal.notary.utils

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.theideal.notary.di.authModule
import com.theideal.notary.di.clientModule
import com.theideal.notary.di.companyModule
import com.theideal.notary.di.createSupplier
import com.theideal.notary.di.supplier
import com.theideal.notary.di.theSupplier
import com.theideal.notary.di.theSupplierBill
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(clientModule)
            modules(
                authModule,
                companyModule,
                supplier,
                createSupplier,
                theSupplier,
                theSupplierBill,
            )
        }
    }

}