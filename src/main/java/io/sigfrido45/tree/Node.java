package io.sigfrido45.tree;

import io.sigfrido45.validation.AbstractTypeValidator;

import java.util.ArrayList;
import java.util.List;


public class Node<T> {
    private AbstractTypeValidator<T> typeValidation;
    private List<Node> nodes = new ArrayList<>();

    public static <T> Node<T> build() {
        return (new Node<>());
    }

    public Node<T> setValidation(AbstractTypeValidator<T> data) {
        this.typeValidation = data;
        return this;
    }

    public Node<T> addNode(Node node) {
        nodes.add(node);
        return this;
    }

    public AbstractTypeValidator<T> getTypeValidation() {
        return typeValidation;
    }

    public List<Node> getNodes() {
        return nodes;
    }


}
