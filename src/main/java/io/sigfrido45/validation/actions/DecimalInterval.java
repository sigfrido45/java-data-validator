package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

public interface DecimalInterval<T> {
  AbstractTypeValidator<T> gte(double min);

  AbstractTypeValidator<T> lte(double max);

  AbstractTypeValidator<T> lt(double min);

  AbstractTypeValidator<T> gt(double max);
}
