package io.sigfrido45.validation;

import io.sigfrido45.validation.actions.IntInterval;
import io.sigfrido45.validation.actions.Presence;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class StringReactiveTypeValidator extends AbstractTypeValidator<String> implements Presence<String>, IntInterval<String>, TypeValidator<String> {

  public StringReactiveTypeValidator() {
    super();
  }

  public StringReactiveTypeValidator(String attrName) {
    super(attrName);
  }


  @Override
  public StringReactiveTypeValidator cast() {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating)
          return validateCast(ValidationTypeUtil.getStringCastInfo(valueInfo.getValue()));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  @Override
  public StringReactiveTypeValidator gte(int min) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value.length() < min)
          return getMsg("validation.string.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  @Override
  public StringReactiveTypeValidator lte(int max) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value.length() > max)
          return getMsg("validation.string.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  @Override
  public StringReactiveTypeValidator lt(int min) {
    return lte(min - 1);
  }

  @Override
  public StringReactiveTypeValidator gt(int max) {
    return gte(max + 1);
  }

  @Override
  public StringReactiveTypeValidator present(boolean present) {
    reactiveValidationFunctions.add(() -> presentAsyncValidationFunction(present));
    return this;
  }

  @Override
  public StringReactiveTypeValidator nullable(boolean nullable) {
    reactiveValidationFunctions.add(() -> nullableAsyncValidationFunction(nullable));
    return this;
  }

  public StringReactiveTypeValidator regex(Pattern pattern) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && !pattern.matcher(_value).matches())
          return getMsg("validation.string.regex", getAttr(FIELD_PREFIX + attrName));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  public StringReactiveTypeValidator notEmpty() {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value.trim().isEmpty())
          return getMsg("validation.string.empty", getAttr(FIELD_PREFIX + attrName));
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }

  public StringReactiveTypeValidator in(String commaSeparated) {
    return in(commaSeparated.split(","));
  }

  public StringReactiveTypeValidator in(String[] args) {
    return in(List.of(args));
  }

  public StringReactiveTypeValidator in(List<String> args) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating) {
          var find = args.stream().filter(a -> a.equalsIgnoreCase(_value)).findFirst().orElse(null);
          if (Objects.isNull(find)) {
            return getMsg("validation.string.in", getAttr(FIELD_PREFIX + attrName), String.join(",", args));
          }
        }
        return AbstractTypeValidator.NULL_STR_VALUE;
      })
    );
    return this;
  }
}
