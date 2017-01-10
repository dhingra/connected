package com.bgc.poc.model.graph;

import java.util.Comparator;

/**
 *@author rohitdhingra
 */
public class EdgeComparator<T,N extends Number> implements Comparator<Edge<T,N>> {

    @Override
    public int compare(Edge<T,N> e1, Edge<T,N> e2) {
        if (e1 == e2) return 0;
        if(e1.equals(e2)) return 0;
        if(e1.getWeight() instanceof Integer){
            return Integer.compare(e1.getWeight().intValue(),e2.getWeight().intValue());
        }
        else{//TODO handle fractional  also
            return 0;
        }

    }


}
