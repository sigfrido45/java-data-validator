package io.sigfrido45.tree;

import io.sigfrido45.validation.AbstractTypeValidation;

import java.util.ArrayList;
import java.util.List;


public class Node<T> {
    private AbstractTypeValidation<T> data;
    private List<Node> nodes = new ArrayList<>();

    public static <T> Node<T> build() {
        return (new Node<>());
    }

    public Node<T> setValidation(AbstractTypeValidation<T> data) {
        this.data = data;
        return this;
    }

    public Node<T> addNode(Node node) {
        nodes.add(node);
        return this;
    }

    public AbstractTypeValidation<T> getData() {
        return data;
    }

    public List<Node> getNodes() {
        return nodes;
    }


}
