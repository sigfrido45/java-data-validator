package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

public interface IntInterval<T> {
  AbstractTypeValidator<T> min(int min);

  AbstractTypeValidator<T> max(int max);
}
