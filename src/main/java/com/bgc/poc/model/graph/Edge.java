package com.bgc.poc.model.graph;

/**
 * <p>Immutable Edge</p>
 * @author rohitdhingra
 */
public final class Edge<T,N extends Number> {
    private final Vertex<T> sourceVertex;
    private final Vertex<T> destinationVertex;
    private N weight;

    /**
     *
     * @param sourceVertex
     * @param destinationVertex
     * @param weight
     */
    public Edge(final Vertex<T> sourceVertex, final Vertex<T> destinationVertex, N weight){
        this.sourceVertex = sourceVertex;
        this.destinationVertex = destinationVertex;
        this.weight = weight;
    }

    /**
     *
     * @return
     */
    public Vertex<T> getSourceVertex(){
        return sourceVertex.clone(sourceVertex);
    }

    /**
     *
     * @return
     */
    public Vertex<T> getDestinationVertex(){
        return destinationVertex.clone(destinationVertex);
    }

    public N getWeight(){
        return weight;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if(this == obj) return true;
        if(getClass() != obj.getClass()) return false;

        final Edge<?,?> that = (Edge<?,?>)obj;

        if(that.getSourceVertex().equals(this.getSourceVertex()) &&
                that.getDestinationVertex().equals(this.getDestinationVertex()) &&
                that.getWeight().equals(this.getWeight())){
            return true;
        }

        return false;
    }

}
