package com.bgc.poc.dao;

/**
 * @author rohitdhingra
 * @param <T>
 * @param <N>
 */
public interface EdgeLoader<T,N extends Number> {
	
	void loadEdges(final VertexFoundCallback<T,N> edgeFoundCallback);
	
	interface VertexFoundCallback<T,N>{
		void newVertex(T city1,T city2, N weight, String direction);
	}

	

	
}
