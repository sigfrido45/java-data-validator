package io.sigfrido45.tree;

import io.sigfrido45.validation.AbstractTypeValidator;

public class ChildNode<T> extends Node<T> {

  public static <K> ChildNode<K> build() {
    return new ChildNode<>();
  }

  public Node<T> setValidator(AbstractTypeValidator<T> data) {
    this.typeValidation = data;
    return this;
  }
}
