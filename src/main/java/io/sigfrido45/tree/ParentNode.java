package io.sigfrido45.tree;

import lombok.Getter;

import java.util.List;

public class ParentNode<T> extends Node<T> {

  @Getter
  private String label;
  public ParentNode(String label) {
    this.label = label;
  }

  public ParentNode() {
    label = "";
  }

  public List<Node<?>> getChildNodes() {
    return nodes;
  }
}
