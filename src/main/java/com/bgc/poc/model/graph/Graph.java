package com.bgc.poc.model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.bgc.poc.dao.EdgeLoader;
import com.bgc.poc.functions.WeightagePredicate;
import com.bgc.poc.pattern.Builder;
/**
 * <p>The {@code DirectedGraph} class supports Directed Graph.</p>
 *
 * @author rohitdhingra
 */
public class Graph<T, N extends Number> {

    private boolean isWeightedGraph;
    private final WeightagePredicate predicate;
    private final Map<String,Vertex> verticesMap;
    private final Map<String, Set<Vertex<T>>> adjacencyMap;
    private final Map<String, Set<String>> transitiveClosureMap;

    /**
     *
     * @param builder
     */
    private Graph(DirectedGraphBuilder builder) {
        this.isWeightedGraph = builder.isWeightedGraph;
        predicate = new WeightagePredicate();
        verticesMap = new HashMap<>();
        adjacencyMap = new HashMap<>();
        transitiveClosureMap = new HashMap();
        buildGraphNetwork(builder.edgesList);
        constructTransitiveClosure();
        // really not needed any more, why hold up the RAM?
        adjacencyMap.clear();
    }


    /**
     * Creates a map of {vertex, [list of adjacent vertices]}
     * @param edges list holding edge object(s).
     */
    private void buildGraphNetwork(final List<Edge<T,N>> edges) {
        edges.stream().filter(edge -> predicate.test(edge.getWeight(),isWeightedGraph)).forEach(edge -> {
            final Vertex<T> sourceVertex =  edge.getSourceVertex();
            final Vertex<T> destinationVertex =  edge.getDestinationVertex();
            /** build vertices map. **/
            verticesMap.putIfAbsent(sourceVertex.toString(), sourceVertex);
            verticesMap.putIfAbsent(destinationVertex.toString(), destinationVertex);

            /** build adjacency network **/
            final Set<Vertex<T>> adjacentVertexSet;
            if (adjacencyMap.containsKey(edge.getSourceVertex().toString())) {
                adjacentVertexSet = adjacencyMap.get(edge.getSourceVertex().toString());
            } else {
                adjacentVertexSet = new HashSet<>();
            }
            adjacentVertexSet.add(edge.getDestinationVertex());
            adjacencyMap.put(edge.getSourceVertex().toString(), adjacentVertexSet);
            if(edge.isBiDirectional()){
                final Set<Vertex<T>> adjacentVertexSet2;
                if (adjacencyMap.containsKey(edge.getDestinationVertex().toString())) {
                    adjacentVertexSet2 = adjacencyMap.get(edge.getDestinationVertex().toString());
                } else {
                    adjacentVertexSet2 = new HashSet<>();
                }
                adjacentVertexSet2.add(edge.getSourceVertex());
                adjacencyMap.put(edge.getDestinationVertex().toString(), adjacentVertexSet2);
            }
        });
    }

    /**
     * Warshall algorithm to build a transitive closure map.
     */
    private void constructTransitiveClosure(){
        adjacencyMap.entrySet().stream().forEach(e -> {
            final Queue<Vertex<T>> verticesToVisit = new LinkedBlockingQueue<>();
            final HashSet<Vertex<T>> verticesVisited = new HashSet<>();
            final String currentVertex = e.getKey();
            final Set<String> transitiveSet = new HashSet<>();

            e.getValue().stream().forEach(v -> transitiveSet.add(v.toString()));
            transitiveClosureMap.put(currentVertex, transitiveSet);

            verticesToVisit.addAll(e.getValue());
            while (!verticesToVisit.isEmpty()) {
                final Vertex<T> visitingVertex = verticesToVisit.remove();
                verticesVisited.add(visitingVertex);

                final Optional<Set<Vertex<T>>> adjacencySet = Optional.ofNullable(adjacencyMap.get(visitingVertex.toString()));
                /** filter-out the vertices that are already had a visit. */
                adjacencySet.ifPresent(set -> set.stream().filter(v -> !verticesVisited.contains(v)).forEach(v -> {
                    /** vertex enqued for visit */
                    verticesToVisit.add(v);
                    /** add this vertex to transitive map **/
                    transitiveSet.add(v.toString());

                }));
            }
        });
    }

    /**
     *
     * @param fromVertex source vertex
     * @param toVertex destination vertex
     * @return true if two vertex are connected, else false.
     */
    public boolean isConnected(final Vertex<T> fromVertex, final Vertex<T> toVertex) {
        if(!transitiveClosureMap.containsKey(fromVertex.toString())) return false;
        return transitiveClosureMap.get(fromVertex.toString()).contains(toVertex.toString());
    }


    /**
     * Builder class to build DirectedGraph
     */
    public static class DirectedGraphBuilder<T, N extends Number>  implements Builder{
    	
        private boolean isWeightedGraph = false;
        private final List<Edge<T,N>> edgesList = new ArrayList<>();
        private EdgeLoader<T,N> edgeLoader;

        /**
         * @param fromVertex
         * @param toVertex
         * @param weight
         * @return
         */
        public DirectedGraphBuilder<T, N> addEdge(T fromVertex, T toVertex,  N weight, String direction) {
        	edgesList.add(new Edge<>(new Vertex<T>(fromVertex), new Vertex<T>(toVertex), weight, direction));
            return this;
        }

        public Graph<T,N> build() {
        	if(edgeLoader!= null){
        		edgeLoader.loadEdges((vertex1, vertex2, weight, direction) -> DirectedGraphBuilder.this.addEdge(vertex1,vertex2,weight, direction));
        	}
            return new Graph<>(this);
        }

        public DirectedGraphBuilder<T, N> fromEdgeLoader(EdgeLoader<T,N> edgeLoader){
        	this.edgeLoader = edgeLoader;
        	return this;
        }
        
        /**
         *
         * @param isWeightedGraph
         * @return
         */
        public DirectedGraphBuilder<T, N> setWeightedGraph(boolean isWeightedGraph) {
            this.isWeightedGraph = isWeightedGraph;
            return this;
        }
    }
}
