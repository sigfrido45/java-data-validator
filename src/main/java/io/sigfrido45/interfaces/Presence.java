package io.sigfrido45.interfaces;

import io.sigfrido45.validation.AbstractTypeValidation;

public interface Presence<T> {

    AbstractTypeValidation<T> required(boolean required);

    AbstractTypeValidation<T> nullable(boolean nullable);
}
