package io.sigfrido45.tree;

import io.sigfrido45.validation.AbstractTypeValidator;

public class ChildNode<T> extends Node<T> {

  public Node<T> setValidator(AbstractTypeValidator<T> data) {
    this.typeValidation = data;
    return this;
  }
}
