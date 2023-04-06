package io.sigfrido45.tree;

import io.sigfrido45.validation.AbstractTypeValidator;

import java.util.ArrayList;
import java.util.List;


public class Node<T> {
  protected AbstractTypeValidator<T> typeValidation;
  protected final List<Node<?>> nodes = new ArrayList<>();

  public Node<T> addNode(Node<?> node) {
    nodes.add(node);
    return this;
  }

  public AbstractTypeValidator<T> getTypeValidation() {
    return typeValidation;
  }
}
