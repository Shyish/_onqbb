package com.aballano.onqbb.products.data

import com.aballano.onqbb.products.data.model.ProductResponse
import io.reactivex.Single


interface ProductsLocalDataSource {
    fun getProductsMap(): Single<List<ProductResponse>>
}