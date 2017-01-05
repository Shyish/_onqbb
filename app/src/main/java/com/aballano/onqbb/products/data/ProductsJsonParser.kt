package com.aballano.onqbb.products.data

import com.aballano.onqbb.products.data.model.ProductResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Single
import okio.Okio
import javax.inject.Inject

class ProductsJsonParser @Inject constructor(val moshi: Moshi) : ProductsLocalDataSource {
    override fun getProductsMap(): Single<List<ProductResponse>> {
        return Single.fromCallable {
            val stream = javaClass.getResourceAsStream("transactions1.json")
            val adapter = moshi.adapter<List<ProductResponse>>(
                  Types.newParameterizedType(List::class.java, ProductResponse::class.java))
            adapter.fromJson(Okio.buffer(Okio.source(stream)))
        }
    }
}