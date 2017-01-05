package com.aballano.onqbb.transaction.presentation.renderer

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aballano.R
import com.aballano.onqbb.transaction.domain.model.Transaction
import com.pedrogomez.renderers.Renderer
import kotlinx.android.synthetic.main.product_list_item.view.*

class TransactionRenderer() : Renderer<Transaction>() {
    override fun inflate(inflater: LayoutInflater, parent: ViewGroup)
          = inflater.inflate(R.layout.product_list_item, parent, false)

    override fun render(payloads: MutableList<Any>) {
        rootView.text1.text = content.originalAmount.toString()
        rootView.text2.text = content.convertedAmount.toString()
    }
}