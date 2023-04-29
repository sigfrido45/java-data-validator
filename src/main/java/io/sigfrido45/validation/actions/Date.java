package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

public interface Date<T> {

  AbstractTypeValidator<T> format(String format);

}
