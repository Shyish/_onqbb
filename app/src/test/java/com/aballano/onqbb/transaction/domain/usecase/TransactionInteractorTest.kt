package com.aballano.onqbb.transaction.domain.usecase

import com.aballano.onqbb.products.domain.model.Amount
import com.aballano.onqbb.products.domain.model.Product
import com.aballano.onqbb.transaction.data.RatesJsonParser
import com.aballano.onqbb.transaction.domain.usecase.converter.RatesToGraphConverter
import com.aballano.onqbb.transaction.domain.usecase.pathcalculator.ModifiedDijkstraAlgorithm
import com.squareup.moshi.Moshi
import io.reactivex.Single
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class TransactionInteractorTest {
    lateinit var interactor: TransactionInteractor

    // Using spy so we can rely on real data if needed
    @Spy var localDataSource = RatesJsonParser(Moshi.Builder().build())
    @Spy var graphConverter = RatesToGraphConverter()
    @Spy var algorithm = ModifiedDijkstraAlgorithm()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = spy(TransactionInteractor(localDataSource, graphConverter, algorithm))
    }

    @Test fun `empty transactions return empty conversions`() {
        `when`(localDataSource.getRates()).thenReturn(Single.just(arrayListOf()))
        val product = Product("a", 1, arrayListOf())

        val values = interactor.getProductConvertedTransactions(product).test().values()[0]
        assertTrue(values.isEmpty())
    }

    @Test fun `when there's no distance, the converted amount is invalid`() {
        doReturn(null).`when`(algorithm).getDistanceTo(ArgumentMatchers.any())
        val product = Product("a", 1, arrayListOf())

        val test = interactor.getProductConvertedTransactions(product).test()
        test.assertComplete().assertValue {
            it.all { Amount.invalidAmount == it.convertedAmount }
        }
    }
}