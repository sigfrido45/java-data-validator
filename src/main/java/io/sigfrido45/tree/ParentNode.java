package io.sigfrido45.tree;

import java.util.List;

public class ParentNode<T> extends Node<T> {

  public List<Node<?>> getChildNodes() {
    return nodes;
  }
}
