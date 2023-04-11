package io.sigfrido45.validation;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.Node;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.actions.Iterable;
import io.sigfrido45.validation.actions.Presence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListTypeValidator extends AbstractTypeValidator<List<Object>> implements Presence<List<Object>>, TypeValidator<List<Object>>, Iterable<List<Object>> {

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
              var res = NodeValidator.validateNode(parentNode.getChildNodes(), _value.get(i), additionalContext);
              if (res.isValid()) {
                if (!res.getValidated().isEmpty()) {
                  _value.set(i, res.getValidated());
                }
              } else {
                return res.getErrors().get(0);
              }
            }

            if (schemaNode instanceof ChildNode<?> childNode) {
              var childNodes = new ArrayList<Node<?>>();
              childNodes.add(childNode);
              var res = NodeValidator.validateNode(childNodes, _value.get(i), additionalContext);
              if (res.isValid()) {
                if (!res.getValidated().isEmpty()) {
                  _value.set(i, res.getValidated().values().toArray()[0]);
                }
              } else {
                return res.getErrors().get(0);
              }
            }
          }
        }
        return null;
      }
    );
    return this;
  }

  private Error validateCast() {
    _value = getCasted(valueInfo.getValue());
    return _value != null ? null : new Error(
      attrName, getMsg("validation.type", getAttr(attrName))
    );
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