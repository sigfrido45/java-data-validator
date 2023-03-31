package io.sigfrido45.payload;

import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.Node;
import io.sigfrido45.tree.NodeResponse;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.ValueInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeValidator {

  private final Map<Object, Object> validated;
  private final Map<Object, Object> errors;

  public static <T> NodeResponse validateNode(ParentNode<?> node, Map<Object, Object> data) {
    var validator = new NodeValidator();

    validator.validate(node.getChildNodes(), data, "");
    return new NodeResponse(validator.validated, validator.errors);
  }

  private NodeValidator() {
    errors = new HashMap<>();
    validated = new HashMap<>();
  }

  private void validate(List<Node<?>> nodes, Object data, String attr) {

    for (Node<?> node : nodes) {
      var newAttr = (attr.isEmpty() ? attr : attr + ".") + node.getTypeValidation().getAttrName();

      if (node instanceof ParentNode<?> newParentNode) {
        if (data instanceof Map<?, ?> newData) {
          validate(
            newParentNode.getChildNodes(),
            newData.get(newParentNode.getTypeValidation().getAttrName()),
            newAttr
          );
        } else {
          validate(newParentNode.getChildNodes(), data, newAttr);
        }
      }

      if (node instanceof ChildNode<?> childNode) {
        var nodeValidator = childNode.getTypeValidation();
        var valueInfo = getValueInfo(data, nodeValidator.getAttrName());
        nodeValidator.setValueInfo(valueInfo);
        nodeValidator.validate();

        if (nodeValidator.isValid()) {
          validated.put(nodeValidator.getAttrName(), nodeValidator.validated());
        } else {
          errors.put(newAttr, node.getTypeValidation().errors());
        }
      }
    }
  }

  private ValueInfo getValueInfo(Object data, String attrName) {
    if (data instanceof Map newData) {
      return new ValueInfo(newData.get(attrName), newData.containsKey(attrName));
    }
    return new ValueInfo(data, true);
  }
}
