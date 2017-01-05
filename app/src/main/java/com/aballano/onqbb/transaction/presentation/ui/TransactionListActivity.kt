package com.aballano.onqbb.transaction.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.aballano.R
import com.aballano.di.ApplicationComponent
import com.aballano.di.InjectableActivity
import com.aballano.onqbb.products.domain.model.Product
import com.aballano.onqbb.transaction.domain.model.Transaction
import com.aballano.onqbb.transaction.presentation.presenter.TransactionsListPresenter
import com.aballano.onqbb.transaction.presentation.renderer.SimpleTextRenderer
import com.aballano.onqbb.transaction.presentation.renderer.TransactionRenderer
import com.pedrogomez.renderers.RendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class TransactionListActivity : InjectableActivity(), TransactionsListPresenter.View {

    lateinit var adapter: RendererAdapter<Any>

    @Inject lateinit var presenter: TransactionsListPresenter

    override fun onInject(graph: ApplicationComponent) {
        graph.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val product = intent.getSerializableExtra(EXTRA_PRODUCT) as Product

        setSupportActionBar(toolbar)
        title = "Transactions for ${product.name}"

        val builder = RendererBuilder<Any>()
              .bind(String::class.java, SimpleTextRenderer())
              .bind(Transaction::class.java, TransactionRenderer())

        adapter = RendererAdapter(builder)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        presenter.init(product)
        presenter.bind(this)
    }

    override fun showItems(items: List<Any>) {
        adapter.addAllAndNotify(items)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    companion object {
        private val EXTRA_PRODUCT = "extra_product"

        fun startActivity(context: Context, product: Product) {
            val intent = Intent(context, TransactionListActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, product)
            context.startActivity(intent)
        }
    }
}

