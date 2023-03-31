package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.Presence;

import java.util.Map;

public class MapTypeValidator extends AbstractTypeValidator<Map> implements Presence<Map> {

  public MapTypeValidator(String attrName) {
    super(attrName);
  }

  @Override
  public AbstractTypeValidator<Map> ifPresent() {
    return null;
  }

  @Override
  public AbstractTypeValidator<Map> required(boolean required) {
    validationFunctions.add(
      () -> {
        if (_value == null)
          return new Error(
            getMsg("validation.required", getAttr(attrName))
          );
        return null;
      }
    );
    return this;
  }

  @Override
  public AbstractTypeValidator<Map> nullable(boolean nullable) {
    if (nullable) {
      continueValidating = false;
    }
    return this;
  }
}
