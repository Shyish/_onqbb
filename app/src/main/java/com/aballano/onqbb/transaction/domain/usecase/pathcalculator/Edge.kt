package com.aballano.onqbb.transaction.domain.usecase.pathcalculator

data class Edge(val source: Vertex, val destination: Vertex, val weight: Double) {
    override fun toString(): String {
        return "$source - $weight - $destination"
    }
}