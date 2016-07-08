package com.patrick.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LinkTree<T> implements Iterable<LinkTree<T>>{

    private T data;
    private LinkTree<T> parent;
    private List<LinkTree<T>> children;

    public LinkTree(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public LinkTree<T> addChild(T data) {
        LinkTree<T> child = new LinkTree<>(data);
        child.parent = this;
        this.children.add(child);
        return child;
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public List<LinkTree<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    @Override
    public Iterator<LinkTree<T>> iterator() {
        return children.iterator();
    }

    @Override
    public void forEach(Consumer<? super LinkTree<T>> action) {

    }

    @Override
    public Spliterator<LinkTree<T>> spliterator() {
        return null;
    }
}
