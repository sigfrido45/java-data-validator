package io.sigfrido45.validation;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.Node;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.actions.IntInterval;
import io.sigfrido45.validation.actions.Iterable;
import io.sigfrido45.validation.actions.Presence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListTypeValidator extends AbstractTypeValidator<List<Object>> implements Presence<List<Object>>, TypeValidator<List<Object>>, Iterable<List<Object>>, IntInterval<List<Object>> {

  public ListTypeValidator() {
    super();
  }

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
  public ListTypeValidator forEach(Node<?> schemaNode) {
    validationFunctions.add(
      () -> {
        if (continueValidating) {
          var additionalContext = new HashMap<String, Object>();
          for (int i = 0; i < _value.size(); i++) {
            if (additionalContext.containsKey("index")) {
              additionalContext.replace("index", i);
            } else {
              additionalContext.put("index", i);
            }
            if (schemaNode instanceof ParentNode<?> parentNode) {
              var res = NodeValidator.validateNode(parentNode.getChildNodes(), _value.get(i), additionalContext, getMsgGetter());
              if (res.isValid()) {
                if (!res.getValidated().isEmpty()) {
                  _value.set(i, res.getValidated());
                }
              } else {
                setAttrName(String.format("%s.%d.%s", attrName, i, res.getErrors().get(0).getKey()));
                return res.getErrors().get(0).getMessage();
              }
            }

            if (schemaNode instanceof ChildNode<?> childNode) {
              var childNodes = new ArrayList<Node<?>>();
              childNodes.add(childNode);
              var res = NodeValidator.validateNode(childNodes, _value.get(i), additionalContext, getMsgGetter());
              if (res.isValid()) {
                if (!res.getValidated().isEmpty()) {
                  _value.set(i, res.getValidated().values().toArray()[0]);
                }
              } else {
                setAttrName(String.format("%s.%d", attrName, i));
                return res.getErrors().get(0).getMessage();
              }
            }
          }
        }
        return null;
      }
    );
    return this;
  }

  @Override
  public ListTypeValidator gte(int min) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value.size() <= min)
          return getMsg("validation.list.min", getAttr(FIELD_PREFIX + attrName), String.valueOf(min));
        return null;
      }
    );
    return this;
  }

  @Override
  public ListTypeValidator lte(int max) {
    validationFunctions.add(
      () -> {
        if (continueValidating && _value.size() >= max)
          return getMsg("validation.list.max", getAttr(FIELD_PREFIX + attrName), String.valueOf(max));
        return null;
      }
    );
    return this;
  }

  @Override
  public ListTypeValidator lt(int min) {
    return lte(min - 1);
  }

  @Override
  public ListTypeValidator gt(int max) {
    return gte(max - 1);
  }

  private String validateCast() {
    _value = getCasted(valueInfo.getValue());
    return _value != null ? null : getMsg("validation.type", getAttr(FIELD_PREFIX + attrName));
  }

  private List<Object> getCasted(Object value) {
    try {
      var newList = (List<?>) value;
      var another = new ArrayList<>();
      another.addAll(newList);
      return another;
    } catch (Exception e) {
      return null;
    }
  }
}
