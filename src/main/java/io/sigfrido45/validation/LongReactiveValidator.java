package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.LongInterval;
import io.sigfrido45.validation.actions.Presence;
import reactor.core.publisher.Mono;

public class LongReactiveValidator extends AbstractTypeValidator<Long> implements Presence<Long>, LongInterval<Long>, TypeValidator<Long> {

  public LongReactiveValidator(String attrName) {
    super(attrName);
  }

  public LongReactiveValidator() {
    super();
  }

  @Override
  public LongReactiveValidator cast() {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating)
          return validateCast(ValidationTypeUtil.getLongCastInfo(valueInfo.getValue()));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  @Override
  public LongReactiveValidator gte(Long min) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value < min)
          return getMsg("validation.number.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  @Override
  public LongReactiveValidator lte(Long max) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value > max)
          return getMsg("validation.number.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  @Override
  public LongReactiveValidator gt(Long min) {
    return gte(min + 1);
  }

  @Override
  public LongReactiveValidator lt(Long max) {
    return lte(max - 1);
  }

  @Override
  public LongReactiveValidator present(boolean present) {
    reactiveValidationFunctions.add(()->presentAsyncValidationFunction(present));
    return this;
  }

  @Override
  public LongReactiveValidator nullable(boolean nullable) {
    reactiveValidationFunctions.add(()->nullableAsyncValidationFunction(nullable));
    return this;
  }
}
