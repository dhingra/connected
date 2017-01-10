package com.bgc.poc.model.graph;

/**
 * <p>Immutable Vertex</p>
 * @author rohitdhingra
 */
public final class Vertex<T> { 
    private final T vertex;

    /**
     *
     * @param vertex
     */
    public Vertex(T vertex){
        this.vertex = vertex;
    }

    public Vertex<T> clone(Vertex<T> vertex){
        return new Vertex<T>(vertex.vertex);
    }

    @Override
    public String toString(){
        return vertex.toString();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if(this == obj) return true;
        final Vertex<?> that = (Vertex<?>) obj;
        return this.vertex.equals(that.vertex);
    }

    @Override
    public int hashCode(){
        final int prime = 17;
        return prime * vertex.hashCode();
    }


}
