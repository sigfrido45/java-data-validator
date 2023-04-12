package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

public interface LongInterval<T> {

  AbstractTypeValidator<T> gte(Long min);

  AbstractTypeValidator<T> lte(Long max);

  AbstractTypeValidator<T> gt(Long min);

  AbstractTypeValidator<T> lt(Long max);
}
