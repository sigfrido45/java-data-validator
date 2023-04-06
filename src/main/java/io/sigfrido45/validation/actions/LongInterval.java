package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

public interface LongInterval<T> {

    AbstractTypeValidator<T> min(Long min);

    AbstractTypeValidator<T> max(Long max);
}
