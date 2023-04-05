package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

public interface Presence<T> {

  AbstractTypeValidator<T> present(boolean present);

  AbstractTypeValidator<T> nullable(boolean nullable);
}
