package com.aballano.onqbb.transaction.domain.usecase.converter

import com.aballano.onqbb.transaction.data.model.Rate
import com.aballano.onqbb.transaction.domain.usecase.pathcalculator.Edge
import com.aballano.onqbb.transaction.domain.usecase.pathcalculator.Graph
import com.aballano.onqbb.transaction.domain.usecase.pathcalculator.Vertex
import java.util.*
import javax.inject.Inject

class RatesToGraphConverter @Inject constructor() {
    fun convert(list: List<Rate>): Graph {
        val vertexes = ArrayList<Vertex>()
        val edges = ArrayList<Edge>()

        list.forEach {
            val from = Vertex(it.from)
            vertexes.add(from)
            val to = Vertex(it.to)
            vertexes.add(to)
            edges.add(Edge(from, to, 1 / it.rate))
        }

        return Graph(vertexes, edges)
    }

}