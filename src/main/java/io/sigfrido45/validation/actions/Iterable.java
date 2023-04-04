package io.sigfrido45.validation.actions;

import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.AbstractTypeValidator;

public interface Iterable<T> {
  AbstractTypeValidator<T> forEach(ParentNode<?> schemaNode);

  AbstractTypeValidator<T> forEach(ChildNode<?> schemaNode);
}
