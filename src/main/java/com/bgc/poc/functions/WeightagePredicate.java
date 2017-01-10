package com.bgc.poc.functions;
import java.util.function.BiPredicate;
/**
 * @author rohitdhingra
 */
public class WeightagePredicate implements BiPredicate<java.lang.Number,java.lang.Boolean> {


    @Override
    public boolean test(Number n, Boolean weight) {
        if(n instanceof Integer) {
            /** Weighted graph so Checke for edge weight > 0 */
            if(!weight.equals(Boolean.TRUE)){
                return true; // non weighted graph.
            }else{
                return (Integer) n > 0  ;
            }

        }
        return true;
    }
}
