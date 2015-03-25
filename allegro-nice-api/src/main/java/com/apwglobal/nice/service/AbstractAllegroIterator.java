package com.apwglobal.nice.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractAllegroIterator<T> implements Iterator<T> {

    protected String session;
    protected long startingPoint;
    protected List<T> items;
    private boolean fetched;
    private int index;

    public AbstractAllegroIterator(String session, long startingPoint) {
        this.session = session;
        this.startingPoint = startingPoint;
        this.items = new ArrayList<>();
    }

    private boolean isFetchNeeded() {
        return !fetched || index == items.size();
    }

    @Override
    public boolean hasNext() {
        if (isFetchNeeded()) {
            fetch();
        }
        return index < items.size();
    }

    @Override
    public T next() {
        if (isFetchNeeded()) {
            fetch();
        }
        return items.get(index++);
    }

    private void fetch() {
        this.fetched = true;
        this.startingPoint = getStartingPoint();
        this.index = 0;
        this.items = doFetch();
    }

    private long getStartingPoint() {
        return items.size() == 0
                ? startingPoint
                : getItemId(items.get(items.size() - 1));
    }

    protected abstract List<T> doFetch();
    protected abstract long getItemId(T t);

}
