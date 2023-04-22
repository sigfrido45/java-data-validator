package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.IntInterval;
import io.sigfrido45.validation.actions.Presence;

public class IntTypeValidator extends AbstractTypeValidator<Integer> implements Presence<Integer>, IntInterval<Integer>, TypeValidator<Integer> {

  public IntTypeValidator(String attrName) {
    super(attrName);
  }

  public IntTypeValidator() {
    super();
  }

  @Override
  public IntTypeValidator cast() {
    validationFunctions.add(
      () -> {
        if (continueValidating)
          return validateCast(ValidationTypeUtil.getIntCastInfo(valueInfo.getValue()));
        return null;
      }
    );
    return this;
  }

  @Override
  public IntTypeValidator gte(int min) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value < min)
          return getMsg("validation.string.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return null;
      }
    );
    return this;
  }

  @Override
  public IntTypeValidator lte(int max) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value > max)
          return getMsg("validation.string.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
        return null;
      }
    );
    return this;
  }

  @Override
  public IntTypeValidator lt(int min) {
    return lte(min - 1);
  }

  @Override
  public IntTypeValidator gt(int max) {
    return gte(max + 1);
  }

  @Override
  public IntTypeValidator present(boolean present) {
    validationFunctions.add(presentValidationFunction(present));
    return this;
  }

  @Override
  public IntTypeValidator nullable(boolean nullable) {
    validationFunctions.add(nullableValidationFunction(nullable));
    return this;
  }
}
