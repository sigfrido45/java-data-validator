package io.sigfrido45.validation.actions;

import io.sigfrido45.validation.AbstractTypeValidator;

import java.time.LocalDate;

public interface DateInterval<T> {

  AbstractTypeValidator<T> gte(LocalDate min);

  AbstractTypeValidator<T> lte(LocalDate max);

  AbstractTypeValidator<T> lt(LocalDate min);

  AbstractTypeValidator<T> gt(LocalDate max);
}
