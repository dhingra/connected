package com.bgc.poc.model;

/**
 * <p> Each {@code City} class holds name of the city and its attributes. </p>
 * @author rohitdhingra
 */
public final class City {
    private final String name;


    public City(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return  name;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if(this == obj) return true;
        final City that = (City) obj;
        return this.getName().equals(that.getName());
    }

    @Override
    public int hashCode(){
        final int prime = 17;
        int result = 1;
        return prime * result + ((name == null && !name.trim().isEmpty()) ? 0 : toString().hashCode());

    }
}
