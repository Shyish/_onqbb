package com.aballano.onqbb.transaction.presentation.presenter

import android.util.Log
import com.aballano.common.BasePresenter
import com.aballano.common.BaseView
import com.aballano.onqbb.products.domain.model.Product
import com.aballano.onqbb.transaction.domain.usecase.TransactionInteractor
import javax.inject.Inject

class TransactionsListPresenter @Inject constructor(val interactor: TransactionInteractor) :
      BasePresenter<TransactionsListPresenter.View>() {

    private lateinit var product: Product

    override fun bind(view: View) {
        super.bind(view)

        addSubscription(interactor.getProductConvertedTransactions(product)
              .subscribe({ view.showItems(it) },
                    { Log.e("ProductListPresenter", "Error: ", it) }))
    }

    interface View : BaseView {
        fun showItems(items: List<Any>)
    }

    fun init(product: Product) {
        this.product = product
    }
}