@file:Suppress("unused")

package com.aballano.di

import android.app.Application
import com.aballano.onqbb.products.data.ProductsJsonParser
import com.aballano.onqbb.products.data.ProductsLocalDataSource
import com.aballano.onqbb.transaction.data.RatesJsonParser
import com.aballano.onqbb.transaction.data.RatesLocalDataSource
import com.aballano.onqbb.transaction.domain.usecase.pathcalculator.GraphPathAlgorithm
import com.aballano.onqbb.transaction.domain.usecase.pathcalculator.ModifiedDijkstraAlgorithm
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Qualifier


@Module
class AndroidModule(private val application: Application) {

    @Provides internal fun provideMoshi() = Moshi.Builder().build()

    @Provides internal fun provideGraphPathAlgorithm():
          GraphPathAlgorithm = ModifiedDijkstraAlgorithm()

    @Provides
    internal fun provideProductsLocalDataSource(moshi: Moshi):
          ProductsLocalDataSource = ProductsJsonParser(moshi)

    @Provides
    internal fun provideRatesLocalDataSource(moshi: Moshi):
          RatesLocalDataSource = RatesJsonParser(moshi)

    @Provides
    @SchedulerIo
    internal fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @SchedulerMainThread
    internal fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @SchedulerImmediate
    internal fun provideImmediateScheduler(): Scheduler = Schedulers.trampoline()

    @Provides
    @SchedulerComputation
    internal fun provideComputationScheduler(): Scheduler = Schedulers.computation()

}

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SchedulerImmediate

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SchedulerComputation

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SchedulerMainThread

@MustBeDocumented
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SchedulerIo
