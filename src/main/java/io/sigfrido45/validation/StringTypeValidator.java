package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.Interval;
import io.sigfrido45.validation.actions.Presence;

import java.util.Objects;

public class StringTypeValidator extends AbstractTypeValidator<String> implements Presence<String>, Interval<String>, TypeValidator<String> {

  public StringTypeValidator(String attrName) {
    super(attrName);
  }

  @Override
  public StringTypeValidator present(boolean present) {
    validationFunctions.add(presentValidationFunction(present));
    return this;
  }

  @Override
  public StringTypeValidator nullable(boolean nullable) {
    validationFunctions.add(nullableValidationFunction(nullable));
    return this;
  }

  @Override
  public StringTypeValidator min(int min) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value.length() <= min)
          return new Error(
            getMsg("validation.str.min", getAttr(attrName), String.valueOf(min))
          );
        return null;
      }
    );
    return this;
  }

  @Override
  public StringTypeValidator max(int max) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value.length() >= max)
          return new Error(
            getMsg("validation.str.max", getAttr(attrName), String.valueOf(max))
          );
        return null;
      }
    );
    return this;
  }

  @Override
  public StringTypeValidator cast() {
    validationFunctions.add(
      () -> {
        if (continueValidating)
          return validateCast();
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

  private String getCasted(Object value) {
    try {
      return String.class.cast(value);
    } catch (Exception E) {
      return null;
    }
  }
}
