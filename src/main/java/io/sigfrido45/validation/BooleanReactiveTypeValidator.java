package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.Presence;
import reactor.core.publisher.Mono;

import java.util.Objects;

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
        if (continueValidating) {
          var castRes = validateCast(ValidationTypeUtil.getBooleanCastInfo(valueInfo.getValue()));
          if (Objects.nonNull(castRes)) {
            return castRes;
          }
        }
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  @Override
  public BooleanReactiveTypeValidator present(boolean present) {
    reactiveValidationFunctions.add(() -> presentAsyncValidationFunction(present));
    return this;
  }

  @Override
  public BooleanReactiveTypeValidator nullable(boolean nullable) {
    reactiveValidationFunctions.add(() -> nullableAsyncValidationFunction(nullable));
    return this;
  }
}
