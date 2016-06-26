package com.donishchenko.testgame.utils;

import java.util.ArrayList;
import java.util.Iterator;

public class FastRemoveArrayList<E> extends ArrayList<E> {

    public FastRemoveArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public E remove(int index) {
        E item = get(size()-1);
        set(index, item);
        return super.remove(size()-1);
    }

    @Override
    public Iterator<E> iterator() {
        return super.iterator();
    }
}
