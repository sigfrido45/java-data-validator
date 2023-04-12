package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.IntInterval;
import io.sigfrido45.validation.actions.Presence;

import java.util.List;
import java.util.Objects;

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
    return gte(max - 1);
  }

  @Override
  public StringTypeValidator cast() {
    validationFunctions.add(
      () -> {
        if (continueValidating)
          return validateCast();
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

  private String validateCast() {
    var castedInfo = getCasted(valueInfo.getValue());
    if (castedInfo.isValid()) {
      _value = castedInfo.getCasted();
      return null;
    }
    return getMsg("validation.type", getAttr(FIELD_PREFIX + attrName));
  }

  private CastInfo<String> getCasted(Object value) {
    var castedInfo = new CastInfo<String>();
    var strVal = String.valueOf(value);
    if (strVal.equalsIgnoreCase("null")) {
      castedInfo.setCasted(null);
    } else {
      castedInfo.setCasted(strVal);
    }
    castedInfo.setValid(true);
    return castedInfo;
  }
}
