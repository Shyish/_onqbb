package com.aballano.onqbb.transaction.domain.usecase.pathcalculator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ALL")
public class ModifiedDijkstraAlgorithmTest {

    private List<Vertex> nodes;
    private List<Edge> edges;

    @Test
    public void optimalButLongerPathIsChosen() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            Vertex location = new Vertex("Node_" + i);
            nodes.add(location);
        }

        addLane(0, 1, 85);
        addLane(0, 10, 5101);
        addLane(0, 2, 217);
        addLane(0, 4, 173);
        addLane(2, 6, 186);
        addLane(2, 7, 103);
        addLane(3, 7, 183);
        addLane(5, 8, 250);
        addLane(8, 9, 84);
        addLane(7, 9, 167);
        addLane(4, 9, 502);
        addLane(9, 10, 40);
        addLane(1, 10, 60);

        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        ModifiedDijkstraAlgorithm dijkstra = new ModifiedDijkstraAlgorithm();
        dijkstra.loadGraph(graph);
        dijkstra.execute(nodes.get(0));
        Vertex target = nodes.get(10);
        List<Vertex> path = dijkstra.getPath(target);
        double distance = dijkstra.getDistanceTo(target);

        assertNotNull(path);
        assertTrue(path.size() > 0);
        // One path would be 0-1-10 and the total distance should be 60 * 85 = 5100
        // the other possible path is directly 0-10 which would be 5101
        // so since 5100 < 5101 -> the first path is chosen and so is the distance
        assertEquals(60 * 85, distance, 0);

        for (int i = 0, pathSize = path.size(); i < pathSize; i++) {
            Vertex vertex = path.get(i);
            System.out.println(vertex);
        }
        System.out.println();
    }

    @Test
    public void optimalShortestPathIsChosen() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            Vertex location = new Vertex("Node_" + i);
            nodes.add(location);
        }

        addLane(0, 1, 85);
        addLane(0, 10, 5099);
        addLane(0, 2, 217);
        addLane(0, 4, 173);
        addLane(2, 6, 186);
        addLane(2, 7, 103);
        addLane(3, 7, 183);
        addLane(5, 8, 250);
        addLane(8, 9, 84);
        addLane(7, 9, 167);
        addLane(4, 9, 502);
        addLane(9, 10, 40);
        addLane(1, 10, 60);

        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        ModifiedDijkstraAlgorithm dijkstra = new ModifiedDijkstraAlgorithm();
        dijkstra.loadGraph(graph);
        dijkstra.execute(nodes.get(0));
        Vertex target = nodes.get(10);
        List<Vertex> path = dijkstra.getPath(target);
        double distance = dijkstra.getDistanceTo(target);

        assertNotNull(path);
        assertTrue(path.size() > 0);
        // One path would be 0-1-10 and the total distance should be 60 * 85 = 5100
        // the other possible path is directly 0-10 which would be 5099
        // so since 5099 < 5100 -> the second path is chosen and so is the distance
        assertEquals(5099, distance, 0);

        for (int i = 0, pathSize = path.size(); i < pathSize; i++) {
            Vertex vertex = path.get(i);
            System.out.println(vertex);
        }
    }

    private void addLane(int sourceLocNo, int destLocNo, int duration) {
        Edge lane = new Edge(nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
    }
}