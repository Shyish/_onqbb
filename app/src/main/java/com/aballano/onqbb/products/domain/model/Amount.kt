package com.aballano.onqbb.products.domain.model

import java.io.Serializable

data class Amount(val quantity: Double, val currency: String) : Serializable {

    companion object {
        val invalidAmount = Amount(-1.0, "")
    }

    override fun toString(): String {
        if (this == invalidAmount) return "Cannot calculate amount"
        return currency + " %.2f".format(quantity)
    }
}