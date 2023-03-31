package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.Interval;
import io.sigfrido45.validation.actions.Presence;

import java.util.Objects;

public class LongTypeValidator extends AbstractTypeValidator<Long> implements Presence<Long>, Interval<Long>, TypeValidator<Long> {

  public LongTypeValidator(String attrName) {
    super(attrName);
  }

  @Override
  public LongTypeValidator cast() {
    validationFunctions.add(
      () -> {
        if (continueValidating)
          return validateCast();
        return null;
      }
    );
    return this;
  }

  @Override
  public LongTypeValidator min(int min) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value <= min)
          return new Error(
            getMsg("validation.long.min", getAttr(attrName), String.valueOf(min))
          );
        return null;
      }
    );
    return this;
  }

  @Override
  public LongTypeValidator max(int max) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value >= max)
          return new Error(
            getMsg("validation.long.min", getAttr(attrName), String.valueOf(max))
          );
        return null;
      }
    );
    return this;
  }

  @Override
  public LongTypeValidator ifPresent() {
    validationFunctions.add(
      () -> {
        continueValidating = valueInfo.isPresent();
        return null;
      }
    );
    return this;
  }

  @Override
  public LongTypeValidator required(boolean required) {
    validationFunctions.add(
      () -> {
        if (continueValidating && required && (Objects.isNull(_value) || !valueInfo.isPresent()))
          return new Error(getMsg("validation.required", getAttr(attrName)));
        return null;
      }
    );
    return this;
  }

  @Override
  public LongTypeValidator nullable(boolean nullable) {
    validationFunctions.add(
      () -> {
        if (nullable && Objects.isNull(valueInfo.getValue())) {
          continueValidating = false;
        }
        return null;
      }
    );
    return this;
  }

  private Error validateCast() {
    _value = getCasted(valueInfo.getValue());
    return _value != null ? null : new Error(
      getMsg("validation.type", getAttr(attrName))
    );
  }

  private Long getCasted(Object value) {
    try {
      var tmp = String.valueOf(value);
      return Long.valueOf(tmp);
    } catch (Exception E) {
      return null;
    }
  }
}
