package com.aballano.onqbb.transaction.domain.model

import com.aballano.onqbb.products.domain.model.Amount

data class Transaction(val originalAmount: Amount, val convertedAmount: Amount)