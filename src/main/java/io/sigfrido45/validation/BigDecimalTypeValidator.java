package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.DecimalInterval;
import io.sigfrido45.validation.actions.Presence;

import java.math.BigDecimal;

public class BigDecimalTypeValidator extends AbstractTypeValidator<BigDecimal> implements Presence<BigDecimal>, DecimalInterval<BigDecimal>, TypeValidator<BigDecimal> {

  public BigDecimalTypeValidator(String attrName) {
    super(attrName);
  }

  public BigDecimalTypeValidator() {
    super();
  }

  @Override
  public BigDecimalTypeValidator cast() {
    validationFunctions.add(
      () -> {
        if (continueValidating) {
          return validateCast(ValidationTypeUtil.getBigDecimalCastInfo(valueInfo.getValue()));
        }
        return null;
      }
    );
    return this;
  }

  @Override
  public BigDecimalTypeValidator gte(double min) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value.compareTo(BigDecimal.valueOf(min)) < 0)
          return getMsg("validation.number.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return null;
      }
    );
    return this;
  }

  @Override
  public BigDecimalTypeValidator lte(double max) {
    validationFunctions.add(() -> {
      if (continueValidating && _value.compareTo(BigDecimal.valueOf(max)) > 0)
        return getMsg("validation.number.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
      return null;
    });
    return this;
  }

  @Override
  public BigDecimalTypeValidator lt(double min) {
    return lte(min - 1);
  }

  @Override
  public BigDecimalTypeValidator gt(double max) {
    return gte(max + 1);
  }

  @Override
  public BigDecimalTypeValidator present(boolean present) {
    validationFunctions.add(presentValidationFunction(present));
    return this;
  }

  @Override
  public BigDecimalTypeValidator nullable(boolean nullable) {
    validationFunctions.add(nullableValidationFunction(nullable));
    return this;
  }

  public BigDecimalTypeValidator decimalsCount(int decimals) {
    validationFunctions.add(() -> {
      if (continueValidating && _value.scale() > decimals)
        return getMsg("validation.number.decimals-count", getAttr(FIELD_PREFIX + attrName), String.valueOf(decimals));
      return null;
    });
    return this;
  }
}
