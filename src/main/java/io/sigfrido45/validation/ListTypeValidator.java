package io.sigfrido45.validation;

import io.sigfrido45.payload.NodeMapValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.actions.Iterable;
import io.sigfrido45.validation.actions.Presence;

import java.util.*;

public class ListTypeValidator extends AbstractTypeValidator<List<Map<Object, Object>>> implements Presence<List<Map<Object, Object>>>, TypeValidator<List<Map<Object, Object>>>, Iterable<List<Map<Object, Object>>> {

  public ListTypeValidator(String attrName) {
    super(attrName);
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
  public ListTypeValidator ifPresent() {
    validationFunctions.add(
      () -> {
        continueValidating = valueInfo.isPresent();
        return null;
      }
    );
    return this;
  }

  @Override
  public ListTypeValidator required(boolean required) {
    validationFunctions.add(
      () -> {
        if (continueValidating && required && (Objects.isNull(_value) || !valueInfo.isPresent()))
          return new Error(getMsg("validation.required", getAttr(attrName)));
        return null;
      }
    );
    return this;
  }

  @Override
  public ListTypeValidator nullable(boolean nullable) {
    validationFunctions.add(
      () -> {
        if (nullable && Objects.isNull(valueInfo.getValue())) {
          continueValidating = false;
        }
        return null;
      }
    );
    return this;
  }

  @Override
  public ListTypeValidator forEach(ParentNode<?> schemaNode) {
    validationFunctions.add(
      () -> {
        if (continueValidating) {
          for (int i = 0; i < _value.size(); i++) {
            var res = NodeMapValidator.validateNode(schemaNode, _value.get(i));
            if (res.isValid()) {
              _value.set(i, res.getValidated());
            } else {
              return new Error(getMsg("validation.list", getAttr(attrName), String.valueOf(i + 1)));
            }
          }
          return null;
        }
        return null;
      }
    );
    return this;
  }

  @Override
  public ListTypeValidator forEach(ChildNode<?> schemaNode) {
    validationFunctions.add(
      () -> {
        if (continueValidating) {
          for (int i = 0; i < _value.size(); i++) {
            var res = NodeMapValidator.validateNode(schemaNode, _value.get(i));
            if (res.isValid()) {
              _value.set(i, res.getValidated());
            } else {
              return new Error(getMsg("validation.list", getAttr(attrName), String.valueOf(i + 1)));
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

  private List<Map<Object, Object>> getCasted(Object value) {
    try {
      var newList = (List<?>) value;
      var another = new ArrayList<Map<Object, Object>>();
      for (Object v : newList) {
        if (v instanceof Map<?,?> mapped) {
          another.add(new HashMap<>(mapped));
        } else {
          throw new RuntimeException("Element is not a map ");
        }
      }
      return another;
    } catch (Exception E) {
      return null;
    }
  }
}
