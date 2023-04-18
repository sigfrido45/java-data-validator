package io.sigfrido45.validation;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.Node;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.actions.IntInterval;
import io.sigfrido45.validation.actions.Iterable;
import io.sigfrido45.validation.actions.Presence;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
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
    reactiveValidationFunctions.add(() -> {
      if (continueValidating) {
        return Flux.fromIterable(_value)
          .index()
          .flatMap(tuple -> {
            var i = tuple.getT1().intValue();
            var additionalContext = new HashMap<String, Object>();
            additionalContext.put("index", i);

            if (schemaNode instanceof ParentNode<?> parentNode) {
              return NodeValidator.validateNodeReactive(parentNode.getChildNodes(), tuple.getT2(), additionalContext, getMsgGetter())
                .map(res -> {
                  if (res.isValid()) {
                    if (!res.getValidated().isEmpty()) {
                      _value.set(i, res.getValidated());
                    }
                    return null;
                  } else {
                    setAttrName(String.format("%s.%d.%s", attrName, i, res.getErrors().get(0).getKey()));
                    return res.getErrors().get(0).getMessage();
                  }
                });
            }

            if (schemaNode instanceof ChildNode<?> childNode) {
              var childNodes = new ArrayList<Node<?>>();
              childNodes.add(childNode);
              return NodeValidator.validateNodeReactive(childNodes, tuple.getT2(), additionalContext, getMsgGetter())
                .map(res -> {
                  if (res.isValid()) {
                    if (!res.getValidated().isEmpty()) {
                      _value.set(i, res.getValidated().values().toArray()[0]);
                    }
                    return null;
                  } else {
                    setAttrName(String.format("%s.%d", attrName, i));
                    return res.getErrors().get(0).getMessage();
                  }
                });
            }

            return null;
          }).next();
      }
      return Mono.fromCallable(() -> null);
    });

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
