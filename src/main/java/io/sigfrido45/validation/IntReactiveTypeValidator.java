package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.IntInterval;
import io.sigfrido45.validation.actions.Presence;
import reactor.core.publisher.Mono;

public class IntReactiveTypeValidator extends AbstractTypeValidator<Integer> implements Presence<Integer>, IntInterval<Integer>, TypeValidator<Integer> {

  public IntReactiveTypeValidator(String attrName) {
    super(attrName);
  }

  public IntReactiveTypeValidator() {
    super();
  }

  @Override
  public IntReactiveTypeValidator cast() {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating)
          return validateCast(ValidationTypeUtil.getIntCastInfo(valueInfo.getValue()));
        return null;
      })
    );
    return this;
  }

  @Override
  public IntReactiveTypeValidator gte(int min) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value <= min)
          return getMsg("validation.string.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return null;
      })
    );
    return this;
  }

  @Override
  public IntReactiveTypeValidator lte(int max) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value >= max)
          return getMsg("validation.string.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
        return null;
      })
    );
    return this;
  }

  @Override
  public IntReactiveTypeValidator lt(int min) {
    return lte(min - 1);
  }

  @Override
  public IntReactiveTypeValidator gt(int max) {
    return gte(max + 1);
  }

  @Override
  public IntReactiveTypeValidator present(boolean present) {
    reactiveValidationFunctions.add(presentAsyncValidationFunction(present));
    return this;
  }

  @Override
  public IntReactiveTypeValidator nullable(boolean nullable) {
    validationFunctions.add(nullableValidationFunction(nullable));
    return this;
  }
}
