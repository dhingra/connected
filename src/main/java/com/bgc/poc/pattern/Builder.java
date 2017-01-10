package com.bgc.poc.pattern;

/**
 * @author rohitdhingra
 */

@FunctionalInterface
public interface Builder<T> {
    T build();
}
