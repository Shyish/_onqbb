package com.aballano.onqbb.transaction.domain.usecase.pathcalculator;

import android.support.annotation.Nullable;

import java.util.List;

public interface GraphPathAlgorithm {
    ModifiedDijkstraAlgorithm loadGraph(Graph graph);

    void execute(Vertex source);

    List<Vertex> getPath(Vertex target);

    @Nullable Double getDistanceTo(Vertex target);
}
