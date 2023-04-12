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
          return validateCast();
        return null;
      }
    );
    return this;
  }

  @Override
  public IntTypeValidator min(int min) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value <= min)
          return getMsg("validation.str.min", getAttr(attrName), String.valueOf(min));
        return null;
      }
    );
    return this;
  }

  @Override
  public IntTypeValidator max(int max) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value >= max)
          return getMsg("validation.str.max", getAttr(attrName), String.valueOf(max));
        return null;
      }
    );
    return this;
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

  private String validateCast() {
    var castedInfo = getCasted(valueInfo.getValue());
    if (castedInfo.isValid()) {
      _value = castedInfo.getCasted();
      return null;
    }
    return getMsg("validation.type", getAttr(attrName));
  }

  private CastInfo<Integer> getCasted(Object value) {
    var castedInfo = new CastInfo<Integer>();
    var strValue = String.valueOf(value);
    if (strValue.equalsIgnoreCase("null")) {
      castedInfo.setCasted(null);
      castedInfo.setValid(true);
    } else {
      try {
        castedInfo.setCasted(Integer.valueOf(strValue));
        castedInfo.setValid(true);
      } catch (Exception e) {
        castedInfo.setValid(false);
      }
    }
    return castedInfo;
  }
}
