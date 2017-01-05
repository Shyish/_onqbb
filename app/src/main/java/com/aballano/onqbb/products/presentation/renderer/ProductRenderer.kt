package com.aballano.onqbb.products.presentation.renderer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aballano.R
import com.aballano.onqbb.products.domain.model.Product
import com.pedrogomez.renderers.Renderer
import kotlinx.android.synthetic.main.product_list_item.view.*

class ProductRenderer(val clickListener: View.OnClickListener) : Renderer<Product>() {
    override fun inflate(inflater: LayoutInflater, parent: ViewGroup)
          = inflater.inflate(R.layout.product_list_item, parent, false)
          .apply { setOnClickListener(clickListener) }

    override fun render(payloads: MutableList<Any>?) {
        rootView.tag = content
        rootView.text1.text = content.name
        rootView.text2.text = content.numberTransactions.toString()
    }
}