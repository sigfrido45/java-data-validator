package io.sigfrido45.validation.actions;

import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.Node;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.AbstractTypeValidator;

public interface Iterable<T> {
  AbstractTypeValidator<T> forEach(Node<?> schemaNode);
}
