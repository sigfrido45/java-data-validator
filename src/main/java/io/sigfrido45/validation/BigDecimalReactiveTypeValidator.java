package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.DecimalInterval;
import io.sigfrido45.validation.actions.Presence;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;

public class BigDecimalReactiveTypeValidator extends AbstractTypeValidator<BigDecimal> implements Presence<BigDecimal>, DecimalInterval<BigDecimal>, TypeValidator<BigDecimal> {

  public BigDecimalReactiveTypeValidator(String attrName) {
    super(attrName);
  }

  public BigDecimalReactiveTypeValidator() {
    super();
  }

  @Override
  public BigDecimalReactiveTypeValidator cast() {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating) {
          var castRes = validateCast(ValidationTypeUtil.getBigDecimalCastInfo(valueInfo.getValue()));
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
  public BigDecimalReactiveTypeValidator gte(double min) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value.compareTo(BigDecimal.valueOf(min)) < 0)
          return getMsg("validation.number.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  @Override
  public BigDecimalReactiveTypeValidator lte(double max) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value.compareTo(BigDecimal.valueOf(max)) > 0)
          return getMsg("validation.number.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  @Override
  public BigDecimalReactiveTypeValidator lt(double min) {
    return lte(min - 1);
  }

  @Override
  public BigDecimalReactiveTypeValidator gt(double max) {
    return gte(max + 1);
  }

  @Override
  public BigDecimalReactiveTypeValidator present(boolean present) {
    reactiveValidationFunctions.add(() -> presentAsyncValidationFunction(present));
    return this;
  }

  @Override
  public BigDecimalReactiveTypeValidator nullable(boolean nullable) {
    reactiveValidationFunctions.add(() -> nullableAsyncValidationFunction(nullable));
    return this;
  }

  public BigDecimalReactiveTypeValidator decimalsCount(int decimals) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value.scale() > decimals)
          return getMsg("validation.number.decimals-count", getAttr(FIELD_PREFIX + attrName), String.valueOf(decimals));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }
}
