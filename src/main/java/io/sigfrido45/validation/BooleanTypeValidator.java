package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.Presence;

public class BooleanTypeValidator extends AbstractTypeValidator<Boolean> implements Presence<Boolean>, TypeValidator<Boolean> {

  public BooleanTypeValidator() {
    super();
  }

  public BooleanTypeValidator(String attrName) {
    super(attrName);
  }

  @Override
  public BooleanTypeValidator cast() {
    validationFunctions.add(() -> {
      if (continueValidating)
        return validateCast(ValidationTypeUtil.getBooleanCastInfo(valueInfo.getValue()));
      return null;
    });
    return this;
  }

  @Override
  public BooleanTypeValidator present(boolean present) {
    validationFunctions.add(presentValidationFunction(present));
    return this;
  }

  @Override
  public BooleanTypeValidator nullable(boolean nullable) {
    validationFunctions.add(nullableValidationFunction(nullable));
    return this;
  }
}
