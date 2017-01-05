package com.aballano.onqbb.transaction.domain.usecase

import com.aballano.onqbb.products.domain.model.Amount
import com.aballano.onqbb.products.domain.model.Product
import com.aballano.onqbb.transaction.data.RatesLocalDataSource
import com.aballano.onqbb.transaction.domain.model.Transaction
import com.aballano.onqbb.transaction.domain.usecase.converter.RatesToGraphConverter
import com.aballano.onqbb.transaction.domain.usecase.pathcalculator.GraphPathAlgorithm
import com.aballano.onqbb.transaction.domain.usecase.pathcalculator.Vertex
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class TransactionInteractor @Inject constructor(val localDataSource: RatesLocalDataSource,
                                                val graphConverter: RatesToGraphConverter,
                                                val algorithm: GraphPathAlgorithm) {

    //TODO-IMPROVEMENT here we could add a memory or even disk cache for already known paths.
    fun getProductConvertedTransactions(product: Product): Single<List<Transaction>> {
        return localDataSource.getRates()
              // Convert the rates to a graph
              .map { graphConverter.convert(it) }
              // Load the graph into the algorithm implementation
              .map { algorithm.loadGraph(it) }
              // Now get each product transaction and convert it, so we return
              // both the original and converted one.
              .map {
                  val convertedTransactions = ArrayList<Transaction>()
                  product.transactions.forEach {
                      algorithm.execute(Vertex(it.currency))
                      val distanceTo = algorithm.getDistanceTo(Vertex("GBP"))
                      if (distanceTo == null) {
                          convertedTransactions.add(Transaction(it, Amount.invalidAmount))
                      } else {
                          val quantity = it.quantity / distanceTo
                          convertedTransactions.add(Transaction(it, Amount(quantity, "GBP")))
                      }
                  }
                  convertedTransactions
              }
    }
}