package com.aballano.di

import com.aballano.MyApplication
import com.aballano.onqbb.products.presentation.ui.ProductListActivity
import com.aballano.onqbb.transaction.presentation.ui.TransactionListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class))
interface ApplicationComponent {

    fun inject(application: MyApplication)

    fun inject(activity: ProductListActivity)

    fun inject(activity: TransactionListActivity)
}