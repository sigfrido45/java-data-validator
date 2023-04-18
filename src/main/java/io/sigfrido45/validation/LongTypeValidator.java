package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.LongInterval;
import io.sigfrido45.validation.actions.Presence;

public class LongTypeValidator extends AbstractTypeValidator<Long> implements Presence<Long>, LongInterval<Long>, TypeValidator<Long> {

  public LongTypeValidator(String attrName) {
    super(attrName);
  }

  public LongTypeValidator() {
    super();
  }

  @Override
  public LongTypeValidator cast() {
    validationFunctions.add(
      () -> {
        if (continueValidating)
          return validateCast(ValidationTypeUtil.getLongCastInfo(valueInfo.getValue()));
        return null;
      }
    );
    return this;
  }

  @Override
  public LongTypeValidator gte(Long min) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value <= min)
          return getMsg("validation.number.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return null;
      }
    );
    return this;
  }

  @Override
  public LongTypeValidator lte(Long max) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value >= max)
          return getMsg("validation.number.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
        return null;
      }
    );
    return this;
  }

  @Override
  public LongTypeValidator gt(Long min) {
    return gte(min - 1);
  }

  @Override
  public LongTypeValidator lt(Long max) {
    return lte(max - 1);
  }

  @Override
  public LongTypeValidator present(boolean present) {
    validationFunctions.add(presentValidationFunction(present));
    return this;
  }

  @Override
  public LongTypeValidator nullable(boolean nullable) {
    validationFunctions.add(nullableValidationFunction(nullable));
    return this;
  }
}
