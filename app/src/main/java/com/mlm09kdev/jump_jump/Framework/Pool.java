package com.mlm09kdev.jump_jump.Framework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Pool<T> {
	
    public interface PoolObjectFactory<T> {
        T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<>(maxSize);
    }

    public T newObject() {
        T object;

        if (freeObjects.isEmpty())
            object = factory.createObject();
        else
            object = freeObjects.remove(freeObjects.size() - 1);
        return object;
    }

    public void free(T object) {
        if (freeObjects.size() < maxSize)
            freeObjects.add(object);
    }
}
