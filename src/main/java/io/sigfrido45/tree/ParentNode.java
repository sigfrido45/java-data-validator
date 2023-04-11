package io.sigfrido45.tree;

import lombok.Getter;

import java.util.List;

public class ParentNode<T> extends Node<T> {

  @Getter
  private final String attrName;

  public static <K> ParentNode<K> build(String attrName) {
    return new ParentNode<>(attrName);
  }

  public static <K> ParentNode<K> build() {
    return new ParentNode<>();
  }

  public ParentNode(String label) {
    this.attrName = label;
  }

  public ParentNode() {
    attrName = "";
  }

  public List<Node<?>> getChildNodes() {
    return nodes;
  }
}
