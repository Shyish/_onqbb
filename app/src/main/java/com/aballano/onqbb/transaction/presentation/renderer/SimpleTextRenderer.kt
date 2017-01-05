package com.aballano.onqbb.transaction.presentation.renderer

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aballano.R
import com.pedrogomez.renderers.Renderer
import kotlinx.android.synthetic.main.simple_list_item.view.*

class SimpleTextRenderer() : Renderer<String>() {
    override fun inflate(inflater: LayoutInflater, parent: ViewGroup)
          = inflater.inflate(R.layout.simple_list_item, parent, false)

    override fun render(payloads: MutableList<Any>) {
        rootView.text1.text = "Total: $content"
    }
}