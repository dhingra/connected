package com.bgc.poc.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.bgc.poc.model.City;
/**
 * <p> </p>
 *
 * @author rohitdhingra
 */

public class GraphFileLoader implements EdgeLoader<City,Integer> {
	
	private final  String fileName;

	public GraphFileLoader(final String file){
		this.fileName = file;
		
	}

	@Override
	public void loadEdges(final VertexFoundCallback<City,Integer> edgeFoundCallback) {
		try {
			final File file = new File(fileName);
			final Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\n");
			while (scanner.hasNext()) {
				final String cities[] = scanner.next().split(",");
				if (edgeFoundCallback != null)
					edgeFoundCallback.newVertex(new City(cities[0].trim()),new City( cities[1].trim()), Integer.valueOf(0));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not read file!!",e);
		}

	}


}
