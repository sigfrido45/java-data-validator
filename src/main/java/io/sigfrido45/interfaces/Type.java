package io.sigfrido45.interfaces;

import io.sigfrido45.validation.AbstractTypeValidation;

public interface Type<T> {

    AbstractTypeValidation<T> correctType();
}
