package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

public interface IntInterval<T> {
  AbstractTypeValidator<T> gte(int min);

  AbstractTypeValidator<T> lte(int max);

  AbstractTypeValidator<T> lt(int min);

  AbstractTypeValidator<T> gt(int max);
}
