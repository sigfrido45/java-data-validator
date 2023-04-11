package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

public interface Type<T> {
  AbstractTypeValidator<T> correctType();
}
