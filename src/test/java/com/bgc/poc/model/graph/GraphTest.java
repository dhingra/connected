package com.bgc.poc.model.graph;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.bgc.poc.dao.EdgeLoader;
/**
 *@author rohitdhingra
 */
public class GraphTest {

	   private Graph<String, Integer> graph;

	    @Before
	    public void setUp(){
	    	graph = new Graph.DirectedGraphBuilder<String,Integer>()
	    			.addEdge("Node 1", "Node 2", 0, "1")//Node 1 --> Node 2
	    			.addEdge("Node 1", "Node 3", 0, "0")//Node 1 --> Node 3
	    			.fromEdgeLoader(new EdgeLoader<String, Integer>() {
						
						@Override
						public void loadEdges(VertexFoundCallback<String, Integer> edgeFoundCallback) {
							edgeFoundCallback.newVertex("Node 2", "Node 4", 0,"0");//Node 2 --> Node 4
							edgeFoundCallback.newVertex("Node 5", "Node 6", 0, "1");//Node 5 --> Node 6
							edgeFoundCallback.newVertex("Node 6", "Node 7", 0, "1");//Node 6 --> Node 7
							
						}
					})
	    			.build();
	    }
	 

	    @Test
	    public void isConnectedShouldReturnFalseForNotConnected(){

	        boolean isConnected = graph.isConnected(new Vertex<String>("Node 1"), new Vertex<String>("Node 5"));
	        assertEquals(false,isConnected);
	    }    

	    @Test
	    public void isConnectedShouldReturnTrueFor2ndDegreeConnected(){

	        boolean isConnected = graph.isConnected(new Vertex<String>("Node 1"), new Vertex<String>("Node 4"));
	        assertEquals(true,isConnected);
	    }    


	    @Test
	    public void isConnectedShouldReturnTrueFor1stDegreeConnected(){

	        boolean isConnected = graph.isConnected(new Vertex<String>("Node 1"), new Vertex<String>("Node 2"));
	        assertEquals(true,isConnected);
	    }
}
