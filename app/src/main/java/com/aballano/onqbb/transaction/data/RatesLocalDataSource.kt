package com.aballano.onqbb.transaction.data

import com.aballano.onqbb.transaction.data.model.Rate
import io.reactivex.Single

interface RatesLocalDataSource {
    fun getRates(): Single<List<Rate>>
}