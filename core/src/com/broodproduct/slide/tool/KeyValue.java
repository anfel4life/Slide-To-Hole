package com.broodproduct.slide.tool;

public class KeyValue<T> {

    private T key;
    private T value;

    public KeyValue(T key, T value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
