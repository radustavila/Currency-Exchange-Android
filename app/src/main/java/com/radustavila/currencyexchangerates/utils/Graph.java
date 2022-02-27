package com.radustavila.currencyexchangerates.utils;

import com.radustavila.currencyexchangerates.model.Rate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

public class Graph
{
    private final List<List<Integer>> adjList;
    private static final List<Edge> edges = new ArrayList<>();
    private static final Map<String, Integer> nodes = new HashMap<>();

    Graph(List<Edge> edges, int n)
    {
        adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (Edge edge: edges) {
            adjList.get(edge.source).add(edge.dest);
        }
    }

    public static boolean isReachable(Graph graph, int src, int dest,
                                      boolean[] discovered, Stack<Integer> path)
    {
        discovered[src] = true;
        path.add(src);

        if (src == dest) {
            return true;
        }

        for (int i: graph.adjList.get(src))
        {
            if (!discovered[i])
            {
                if (isReachable(graph, i, dest, discovered, path)) {
                    return true;
                }
            }
        }

        path.pop();
        return false;
    }

    public static String getRate(Graph graph, String from, String to)
    {
        Integer src = nodes.get(from);
        Integer dest = nodes.get(to);

        if (src != null && dest != null) {
            boolean[] discovered = new boolean[nodes.size()];
            Stack<Integer> path = new Stack<>();

            if (isReachable(graph, src, dest, discovered, path)) {
                float exchangeValue = 1;
                for (int i = 0; i < path.size() - 1; i++) {
                    for (Edge edge : edges) {
                        if (path.get(i) == edge.source && path.get(i + 1) == edge.dest) {
                            exchangeValue *= edge.value;
                        }
                    }
                }
                return String.format(Locale.getDefault(), "%.2f", exchangeValue);
            }
        }
        return " - ";
    }

    public static void getNodes(List<Rate> rates) {
        int count = 0;
        for (Rate rate : rates) {
            if (!nodes.containsKey(rate.getFrom())) {
                nodes.put(rate.getFrom(), count);
                count++;
            }
            if (!nodes.containsKey(rate.getTo())) {
                nodes.put(rate.getTo(), count);
                count++;
            }
        }
    }

    public static Graph createGraph(List<Rate> rates) {
        getNodes(rates);

        for (Rate rate : rates) {
            Integer from = nodes.get(rate.getFrom());
            Integer to = nodes.get(rate.getTo());
            if (from != null && to != null) {
                edges.add(Edge.of(from, to, Float.parseFloat(rate.getRate())));
            }
        }

        return new Graph(edges, nodes.size());
    }

    static class Edge
    {
        public final int source, dest;
        public final float value;

        private Edge(int source, int dest, float value)
        {
            this.source = source;
            this.dest = dest;
            this.value = value;
        }

        public static Edge of(int a, int b, float value) {
            return new Edge(a, b, value);
        }
    }
}
