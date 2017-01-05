package com.aballano.onqbb.transaction.domain.usecase.pathcalculator;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"CollectionWithoutInitialCapacity", "unused"})
public class ModifiedDijkstraAlgorithm implements GraphPathAlgorithm {

    private List<Edge> edges;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Double> distance;

    @Override
    public ModifiedDijkstraAlgorithm loadGraph(Graph graph) {
        // create a copy of the array so that we can operate on this array
        edges = new ArrayList<>(graph.getEdges());
        return this;
    }

    @Override
    public void execute(Vertex source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 1.0);
        unSettledNodes.add(source);
        try {
            while (!unSettledNodes.isEmpty()) {
                Vertex node = getMinimum(unSettledNodes);
                settledNodes.add(node);
                unSettledNodes.remove(node);
                findMinimalDistances(node);
            }
        } catch (Exception ignored) {
            //TODO log error
            // We don't want to stop execution if the graph is malformed.
            //In that case the distance will be invalid
        }
    }

    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (int i = 0, adjacentNodesSize = adjacentNodes.size(); i < adjacentNodesSize; i++) {
            Vertex target = adjacentNodes.get(i);
            if (getShortestDistance(target) > getShortestDistance(node)
                  * getAdjacentDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                      * getAdjacentDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    private Double getAdjacentDistance(Vertex node, Vertex adjacentNode) {
        for (int i = 0, edgesSize = edges.size(); i < edgesSize; i++) {
            Edge edge = edges.get(i);
            if (edge.getSource().equals(node)
                  && edge.getDestination().equals(adjacentNode)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<>();
        for (int i = 0, edgesSize = edges.size(); i < edgesSize; i++) {
            Edge edge = edges.get(i);
            if (edge.getSource().equals(node)
                  && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        //noinspection SSBasedInspection
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    private Double getShortestDistance(Vertex destination) {
        Double distance = this.distance.get(destination);
        if (distance == null) {
            return Double.MAX_VALUE;
        } else {
            return distance;
        }
    }

    @Override
    public List<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return Collections.emptyList();
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

    @Override
    @Nullable
    public Double getDistanceTo(Vertex target) {
        Double shortestDistance = getShortestDistance(target);
        return shortestDistance == Double.MAX_VALUE ? null : shortestDistance;
    }
}