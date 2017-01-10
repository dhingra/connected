package com.bgc.poc;

import com.bgc.poc.dao.GraphFileLoader;
import com.bgc.poc.model.City;
import com.bgc.poc.model.graph.DirectedGraph;
import com.bgc.poc.model.graph.Vertex;

/**
 * <p>
 * The {@code Connected} class provides a mechanism to check if two vertex are
 * connected.
 * </p>
 *
 * @author rohitdhingra
 */
public class Connected {

	private final DirectedGraph<City, Integer> graph;

	/**
	 * @param fileName
	 */
	public Connected(String fileName) {
		this.graph = new DirectedGraph.DirectedGraphBuilder<City, Integer>()
				.fromEdgeLoader(new GraphFileLoader(fileName)).setWeightedGraph(false).build();
	}

	/**
	 * @param fromVertex
	 * @param toVertex
	 * @return
	 */

	public boolean isConnected(final String fromVertex, final String toVertex) {
		if (isEmpty(fromVertex) || isEmpty(toVertex)) {
			throw new IllegalArgumentException("From or To vertex can not be empty!");
		} 
		
		return graph.isConnected(new Vertex<>(new City(fromVertex)), new Vertex<City>(new City(toVertex)));
	}

	private boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static void main(String args[]) {

		final Connected connected = new Connected(args[0]);
		final boolean isConnected = connected.isConnected(args[1], args[2]);
		if (isConnected) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}

		System.out.println("Program will exit now.");
		System.exit(0);

	}

}
