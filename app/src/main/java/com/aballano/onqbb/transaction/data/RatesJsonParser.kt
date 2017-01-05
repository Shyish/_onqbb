package com.aballano.onqbb.transaction.data

import com.aballano.onqbb.transaction.data.model.Rate
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Single
import okio.Okio
import javax.inject.Inject


class RatesJsonParser @Inject constructor(val moshi: Moshi) : RatesLocalDataSource {
    override fun getRates(): Single<List<Rate>> {
        return Single.fromCallable {
            val stream = javaClass.getResourceAsStream("rates1.json")
            val adapter = moshi.adapter<List<Rate>>(
                  Types.newParameterizedType(List::class.java, Rate::class.java))
            adapter.fromJson(Okio.buffer(Okio.source(stream)))
        }
    }
}