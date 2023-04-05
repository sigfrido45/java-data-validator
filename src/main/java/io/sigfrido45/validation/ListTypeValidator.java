package io.sigfrido45.validation;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.actions.Iterable;
import io.sigfrido45.validation.actions.Presence;

import java.util.*;

public class ListTypeValidator extends AbstractTypeValidator<List<Object>> implements Presence<List<Object>>, TypeValidator<List<Object>>, Iterable<List<Object>> {

  private final Class<?> clazz;

  public ListTypeValidator(String attrName, Class<?> clazz) {
    super(attrName);
    this.clazz = clazz;
  }

  @Override
  public ListTypeValidator cast() {
    validationFunctions.add(
      () -> {
        if (continueValidating)
          return validateCast();
        return null;
      }
    );
    return this;
  }

  @Override
  public ListTypeValidator present(boolean present) {
    validationFunctions.add(presentValidationFunction(present));
    return this;
  }

  @Override
  public ListTypeValidator nullable(boolean nullable) {
    validationFunctions.add(nullableValidationFunction(nullable));
    return this;
  }

  @Override
  public ListTypeValidator forEach(ParentNode<?> schemaNode) {
    validationFunctions.add(
      () -> {
        if (continueValidating) {
          for (int i = 0; i < _value.size(); i++) {
            var res = NodeValidator.validateNode(schemaNode.getChildNodes(), _value.get(i));
            if (res.isValid()) {
              _value.set(i, res.getValidated());
            } else {
              System.out.println("no valid " + res.getErrors());
              return new Error(getMsg("validation.list " + res.getErrors().get(0), getAttr(attrName), String.valueOf(i + 1)));
            }
          }
          return null;
        }
        return null;
      }
    );
    return this;
  }

  private Error validateCast() {
    _value = getCasted(valueInfo.getValue());
    return _value != null ? null : new Error(
      getMsg("validation.type", getAttr(attrName))
    );
  }

  private List<Object> getCasted(Object value) {
    try {
      var newList = (List<?>) value;
      var another = new ArrayList<>();
      for (Object v : newList) {
        another.add(clazz.cast(v));
      }
      return another;
    } catch (Exception e) {
      return null;
    }
  }
}
