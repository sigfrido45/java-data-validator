package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.IntInterval;
import io.sigfrido45.validation.actions.Presence;

public class StringTypeValidator extends AbstractTypeValidator<String> implements Presence<String>, IntInterval<String>, TypeValidator<String> {

  public StringTypeValidator() {
    super();
  }

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
    var castedInfo = getCasted(valueInfo.getValue());
    if (castedInfo.isValid()) {
      _value = castedInfo.getCasted();
      return null;
    }
    return new Error(
      getMsg("validation.type", getAttr(attrName))
    );
  }

  private CastInfo<String> getCasted(Object value) {
    var castedInfo = new CastInfo<String>();
    var strVal = String.valueOf(value);
    if (strVal.equalsIgnoreCase("null")) {
      castedInfo.setCasted(null);
    } else {
      castedInfo.setCasted(strVal);
    }
    castedInfo.setValid(true);
    return castedInfo;
  }
}
