package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.Presence;
import reactor.core.publisher.Mono;

public class BooleanReactiveTypeValidator extends AbstractTypeValidator<Boolean> implements Presence<Boolean>, TypeValidator<Boolean> {

  public BooleanReactiveTypeValidator() {
    super();
  }

  public BooleanReactiveTypeValidator(String attrName) {
    super(attrName);
  }

  @Override
  public BooleanReactiveTypeValidator cast() {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating)
          return validateCast(ValidationTypeUtil.getBooleanCastInfo(valueInfo.getValue()));
        return null;
      })
    );
    return this;
  }

  @Override
  public BooleanReactiveTypeValidator present(boolean present) {
    reactiveValidationFunctions.add(presentAsyncValidationFunction(present));
    return this;
  }

  @Override
  public BooleanReactiveTypeValidator nullable(boolean nullable) {
    reactiveValidationFunctions.add(nullableAsyncValidationFunction(nullable));
    return this;
  }
}
