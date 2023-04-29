package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.Date;
import io.sigfrido45.validation.actions.DateInterval;
import io.sigfrido45.validation.actions.Presence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTypeValidator extends AbstractTypeValidator<LocalDate> implements Date<LocalDate>, Presence<LocalDate>, DateInterval<LocalDate>, TypeValidator<LocalDate> {

  public DateTypeValidator() {
    super();
  }

  public DateTypeValidator(String attrName) {
    super(attrName);
  }


  @Override
  public DateTypeValidator format(String format) {
    validationFunctions.add(
      () -> {
        if (continueValidating) {
          try {
            _value = LocalDate.parse(String.valueOf(valueInfo.getValue()), DateTimeFormatter.ofPattern(format));
          } catch (Exception e) {
            return getMsg("validation.date.format", getAttr(FIELD_PREFIX + attrName));
          }
        }
        return null;
      }
    );
    return this;
  }

  @Override
  public DateTypeValidator cast() {
    validationFunctions.add(
      () -> {
        if (continueValidating)
          return validateCast(ValidationTypeUtil.getDateCastInfo(valueInfo.getValue()));
        return null;
      }
    );
    return this;
  }

  @Override
  public DateTypeValidator gte(LocalDate min) {
    validationFunctions.add(() -> {
      if (continueValidating && _value.isBefore(min)) {
        return getMsg("validation.date.gte", getAttr(FIELD_PREFIX + attrName), min.toString());
      }
      return null;
    });
    return this;
  }

  @Override
  public DateTypeValidator lte(LocalDate max) {
    validationFunctions.add(() -> {
      if (continueValidating && _value.isAfter(max)) {
        return getMsg("validation.date.lte", getAttr(FIELD_PREFIX + attrName), max.toString());
      }
      return null;
    });
    return this;
  }

  @Override
  public DateTypeValidator lt(LocalDate min) {
    return lte(min.minusDays(1L));
  }

  @Override
  public DateTypeValidator gt(LocalDate max) {
    return gte(max.plusDays(1L));
  }

  @Override
  public DateTypeValidator present(boolean present) {
    validationFunctions.add(presentValidationFunction(present));
    return this;
  }

  @Override
  public DateTypeValidator nullable(boolean nullable) {
    validationFunctions.add(nullableValidationFunction(nullable));
    return this;
  }
}
