package com.aballano.onqbb.products.domain.model

import java.io.Serializable
import java.util.*

data class Product(val name: String, var numberTransactions: Int,
                   val transactions: ArrayList<Amount>) : Serializable {

    fun addTransaction(quantity: Double, currency: String): Product {
        numberTransactions++
        transactions.add(Amount(quantity, currency))
        return this
    }
}