package com.aballano.onqbb.products.domain.usecase

import com.aballano.common.TransformersProvider
import com.aballano.onqbb.products.data.ProductsLocalDataSource
import com.aballano.onqbb.products.data.model.ProductResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers.trampoline
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ProductsInteractorTest {
    lateinit var interactor: ProductsInteractor
    @Mock lateinit var localDataSource: ProductsLocalDataSource
    // We want everything to be executed in the current thread
    val transformersProvider = TransformersProvider(trampoline(), trampoline(),
          trampoline(), trampoline())

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = Mockito.spy(ProductsInteractor(localDataSource, transformersProvider))
    }

    @Test fun `empty products map return empty list`() {
        `when`(localDataSource.getProductsMap()).thenReturn(Single.just(arrayListOf()))
        val values = interactor.getProducts().test().values()
        assertTrue(values.isEmpty())
    }

    @Test fun `products are grouped by SKU only`() {
        val product = ProductResponse(1.0, "A", "CA")
        val anotherProduct = ProductResponse(2.0, "A", "CB")
        val differentProductSameCurrency = ProductResponse(3.0, "B", "CA")
        val differentProductSameAmount = ProductResponse(1.0, "C", "CC")

        `when`(localDataSource.getProductsMap()).thenReturn(Single.just(arrayListOf(
              product, anotherProduct, differentProductSameAmount, differentProductSameCurrency
        )))
        val values = interactor.getProducts().test().values()[0]

        assertEquals(3, values.size)
        val productA = values.find { it.name == "A" }
        if (productA == null) {
            fail()
            return
        }

        assertEquals(2, productA.numberTransactions)
        assertEquals(1.0, productA.transactions[0].quantity, 0.0)
        assertEquals("CA", productA.transactions[0].currency)
        assertEquals(2.0, productA.transactions[1].quantity, 0.0)
        assertEquals("CB", productA.transactions[1].currency)
    }
}