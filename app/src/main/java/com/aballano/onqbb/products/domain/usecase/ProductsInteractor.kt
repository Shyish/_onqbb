package com.aballano.onqbb.products.domain.usecase

import com.aballano.common.TransformersProvider
import com.aballano.onqbb.products.data.ProductsLocalDataSource
import com.aballano.onqbb.products.domain.model.Product
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class ProductsInteractor @Inject constructor(val localDataSource: ProductsLocalDataSource,
                                             val transformersProvider: TransformersProvider) {

    fun getProducts(): Single<List<Product>> = localDataSource.getProductsMap()
          .map {
              val map = HashMap<String, Product>()
              // Counts each different SKU in the list
              it.forEach { it ->
                  val item = map.getOrElse(it.sku, { Product(it.sku, 0, arrayListOf()) })
                  map.put(it.sku, item.addTransaction(it.amount, it.currency))
              }
              // Then maps the count to a Product object
              map.map { it.value }
          }.compose(transformersProvider.computationSingleTransformer())
}
