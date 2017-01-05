package com.aballano.onqbb.products.presentation.presenter

import android.util.Log
import com.aballano.common.BasePresenter
import com.aballano.common.BaseView
import com.aballano.onqbb.products.domain.usecase.ProductsInteractor
import javax.inject.Inject

class ProductListPresenter @Inject constructor(val productsInteractor: ProductsInteractor) :
      BasePresenter<ProductListPresenter.View>() {

    override fun bind(view: View) {
        super.bind(view)

        addSubscription(productsInteractor.getProducts()
              .subscribe({ view.showItems(it) }, { Log.e("ProductListPresenter", "Error: ", it) }))
    }

    interface View : BaseView {
        fun showItems(items: List<Any>)
    }
}


