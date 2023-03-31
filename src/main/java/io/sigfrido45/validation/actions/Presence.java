package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

public interface Presence<T> {

  AbstractTypeValidator<T> ifPresent();

  AbstractTypeValidator<T> required(boolean required);

  AbstractTypeValidator<T> nullable(boolean nullable);
}
