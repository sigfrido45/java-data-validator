package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.IntInterval;
import io.sigfrido45.validation.actions.Presence;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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
  public StringTypeValidator gte(int min) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value.length() <= min)
          return getMsg("validation.string.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return null;
      }
    );
    return this;
  }

  @Override
  public StringTypeValidator lte(int max) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value.length() >= max)
          return getMsg("validation.string.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
        return null;
      }
    );
    return this;
  }

  @Override
  public StringTypeValidator lt(int min) {
    return lte(min - 1);
  }

  @Override
  public StringTypeValidator gt(int max) {
    return gte(max + 1);
  }

  @Override
  public StringTypeValidator cast() {
    validationFunctions.add(
      () -> {
        if (continueValidating)
          return validateCast(ValidationTypeUtil.getStringCastInfo(valueInfo.getValue()));
        return null;
      }
    );
    return this;
  }

  public StringTypeValidator notEmpty() {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value.trim().isEmpty())
          return getMsg("validation.string.empty", getAttr(FIELD_PREFIX + attrName));
        return null;
      }
    );
    return this;
  }

  public StringTypeValidator regex(Pattern pattern) {
    validationFunctions.add(
      () -> {
        if (continueValidating && !pattern.matcher(_value).matches())
          return getMsg("validation.string.regex", getAttr(FIELD_PREFIX + attrName));
        return null;
      }
    );
    return this;
  }

  public StringTypeValidator in(String commaSeparated) {
    return in(commaSeparated.split(","));
  }

  public StringTypeValidator in(String[] args) {
    return in(List.of(args));
  }

  public StringTypeValidator in(List<String> args) {
    validationFunctions.add(
      () -> {
        if (continueValidating) {
          var find = args.stream().filter(a -> a.equalsIgnoreCase(_value)).findFirst().orElse(null);
          if (Objects.isNull(find)) {
            return getMsg("validation.string.in", getAttr(FIELD_PREFIX + attrName), String.join(",", args));
          }
        }
        return null;
      }
    );
    return this;
  }
}
