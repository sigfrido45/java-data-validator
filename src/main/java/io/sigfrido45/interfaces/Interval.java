package io.sigfrido45.interfaces;

import io.sigfrido45.validation.AbstractTypeValidation;

public interface Interval<T> {

    AbstractTypeValidation<T> min(int min);

    AbstractTypeValidation<T> max(int max);
}
