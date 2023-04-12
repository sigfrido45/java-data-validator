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
          return validateCast();
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

  private String validateCast() {
    var castedInfo = getCasted(valueInfo.getValue());
    if (castedInfo.isValid()) {
      _value = castedInfo.getCasted();
      return null;
    }
    return getMsg("validation.type", getAttr(FIELD_PREFIX + attrName));
  }

  private CastInfo<Long> getCasted(Object value) {
    var castedInfo = new CastInfo<Long>();
    var strValue = String.valueOf(value);
    if (strValue.equalsIgnoreCase("null")) {
      castedInfo.setCasted(null);
      castedInfo.setValid(true);
    } else {
      try {
        castedInfo.setCasted(Long.valueOf(strValue));
        castedInfo.setValid(true);
      } catch (Exception e) {
        castedInfo.setValid(false);
      }
    }
    return castedInfo;
  }
}
