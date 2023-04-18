package io.sigfrido45.validation;

import io.sigfrido45.tree.Node;
import io.sigfrido45.validation.actions.IntInterval;
import io.sigfrido45.validation.actions.Iterable;
import io.sigfrido45.validation.actions.Presence;
import reactor.core.publisher.Mono;

import java.util.List;

public class ListReactiveValidator extends AbstractTypeValidator<List<Object>> implements Presence<List<Object>>, TypeValidator<List<Object>>, Iterable<List<Object>>, IntInterval<List<Object>> {

  public ListReactiveValidator() {
    super();
  }

  public ListReactiveValidator(String attrName) {
    super(attrName);
  }

  @Override
  public ListReactiveValidator cast() {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating)
          return validateCast(ValidationTypeUtil.getListCastInfo(valueInfo.getValue()));
        return null;
      })
    );
    return this;
  }

  @Override
  public ListReactiveValidator gte(int min) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value.size() <= min)
          return getMsg("validation.list.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return null;
      })
    );
    return this;
  }

  @Override
  public ListReactiveValidator lte(int max) {
    reactiveValidationFunctions.add(() ->
      Mono.fromCallable(() -> {
        if (continueValidating && _value.size() >= max)
          return getMsg("validation.list.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
        return null;
      })
    );
    return this;
  }

  @Override
  public ListReactiveValidator lt(int min) {
    return lte(min - 1);
  }

  @Override
  public ListReactiveValidator gt(int max) {
    return gte(max + 1);
  }

  @Override
  public ListReactiveValidator forEach(Node<?> schemaNode) {
    //todo
    return this;
  }

  @Override
  public ListReactiveValidator present(boolean present) {
    reactiveValidationFunctions.add(presentAsyncValidationFunction(present));
    return this;
  }

  @Override
  public ListReactiveValidator nullable(boolean nullable) {
    reactiveValidationFunctions.add(nullableAsyncValidationFunction(nullable));
    return this;
  }
}
