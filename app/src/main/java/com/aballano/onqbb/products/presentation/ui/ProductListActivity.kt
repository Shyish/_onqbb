package com.aballano.onqbb.products.presentation.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aballano.R
import com.aballano.di.ApplicationComponent
import com.aballano.di.InjectableActivity
import com.aballano.onqbb.products.domain.model.Product
import com.aballano.onqbb.products.presentation.presenter.ProductListPresenter
import com.aballano.onqbb.products.presentation.renderer.ProductRenderer
import com.aballano.onqbb.transaction.presentation.ui.TransactionListActivity
import com.pedrogomez.renderers.RendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class ProductListActivity : InjectableActivity(), ProductListPresenter.View {

    lateinit var adapter: RendererAdapter<Any>

    @Inject lateinit var presenter: ProductListPresenter

    override fun onInject(graph: ApplicationComponent) {
        graph.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setSupportActionBar(toolbar)
        title = "Products"

        val builder = RendererBuilder<Any>()
              .bind(Product::class.java, ProductRenderer(View.OnClickListener
              { TransactionListActivity.startActivity(this, it.tag as Product) }))

        adapter = RendererAdapter(builder)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        presenter.bind(this)
    }

    override fun showItems(items: List<Any>) {
        adapter.addAllAndNotify(items)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }
}