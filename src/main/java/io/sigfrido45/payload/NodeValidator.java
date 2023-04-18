package io.sigfrido45.payload;

import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.Node;
import io.sigfrido45.tree.NodeResponse;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.Error;
import io.sigfrido45.validation.MessageGetter;
import io.sigfrido45.validation.ValueInfo;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeValidator {

  private final Map<Object, Object> validated;
  private final List<Error> errors;

  @Setter(AccessLevel.PRIVATE)
  private Map<String, Object> additionalContext;
  @Setter(AccessLevel.PRIVATE)
  private MessageGetter msgGetter;

  public static NodeResponse validateNode(List<Node<?>> nodes, Object data, Map<String, Object> additionalContext, MessageGetter msgGetter) {
    var validator = new NodeValidator(msgGetter);
    validator.setAdditionalContext(additionalContext);
    validator.validate(nodes, data, "");
    return new NodeResponse(validator.validated, validator.errors);
  }

  public static NodeResponse validateNode(List<Node<?>> nodes, Object data, Map<String, Object> additionalContext) {
    var validator = new NodeValidator();
    validator.setAdditionalContext(additionalContext);
    validator.validate(nodes, data, "");
    return new NodeResponse(validator.validated, validator.errors);
  }

  public static NodeResponse validateNode(ParentNode<?> node, Object data, MessageGetter msgGetter) {
    var validator = new NodeValidator(msgGetter);
    validator.validate(node.getChildNodes(), data, node.getAttrName());
    return new NodeResponse(validator.validated, validator.errors);
  }

  private NodeValidator() {
    errors = new ArrayList<>();
    validated = new HashMap<>();
    additionalContext = new HashMap<>();
  }

  private NodeValidator(MessageGetter msgGetter) {
    this.msgGetter = msgGetter;
    errors = new ArrayList<>();
    validated = new HashMap<>();
    additionalContext = new HashMap<>();
  }

  private void validate(List<Node<?>> nodes, Object data, String attr) {

    for (Node<?> node : nodes) {

      if (node instanceof ParentNode<?> newParentNode) {
        var newAttr = (attr.isEmpty() ? attr : attr + ".") + newParentNode.getAttrName();
        if (data instanceof Map<?, ?> newData) {
          validate(
            newParentNode.getChildNodes(),
            newData.get(newParentNode.getAttrName()),
            newAttr
          );
        } else {
          validate(newParentNode.getChildNodes(), data, newAttr);
        }
      }

      if (node instanceof ChildNode<?> childNode) {
        var nodeValidator = childNode.getTypeValidation();
        var valueInfo = getValueInfo(data, nodeValidator.getAttrName());
        nodeValidator.mergeAdditionalContext(additionalContext);
        nodeValidator.setMsgGetter(msgGetter);
        nodeValidator.setValueInfo(valueInfo);
        nodeValidator.validate();

        if (nodeValidator.isValid()) {
          if (valueInfo.isPresent()) {
            validated.put(nodeValidator.getAttrName(), nodeValidator.validated());
          }
        } else {
          var errorAttr = (attr.isEmpty() ? attr : attr + ".") + childNode.getTypeValidation().getAttrName();
          errors.add(new Error(errorAttr, node.getTypeValidation().errors().get(0)));
        }
      }
    }
  }

  private ValueInfo getValueInfo(Object data, String attrName) {
    if (data instanceof Map<?, ?> newData) {
      return new ValueInfo(newData.get(attrName), newData.containsKey(attrName));
    }
    return new ValueInfo(data, true);
  }
}
