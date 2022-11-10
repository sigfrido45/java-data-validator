package io.sigfrido45.payload;

import io.sigfrido45.tree.Node;
import io.sigfrido45.tree.NodeResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeValidator {

    private final Map<Object, Object> validated;
    private final Map<Object, Object> errors;

    public static <T> NodeResponse validateNode(Node<T> node, Map<Object, Object> data) {
        var validator = new NodeValidator();
        validator.validate(node.getNodes(), data, "");
        return new NodeResponse(validator.validated, validator.errors);
    }

    private NodeValidator() {
        errors = new HashMap<>();
        validated = new HashMap<>();
    }

    private void validate(List<Node> nodes, Object data, String attr) {

        for (Node<?> node : nodes) {

            var extractedData = data instanceof Map ? ((Map<?, ?>) data).get(node.getTypeValidation().getAttrName()) : data;
            node.getTypeValidation().setValue(extractedData);
            node.getTypeValidation().validate();

            var atr = (attr.isEmpty() ? attr : attr + ".") + node.getTypeValidation().getAttrName();
            if (node.getTypeValidation().isValid()) {
                validated.put(node.getTypeValidation().getAttrName(), node.getTypeValidation().validated());
            } else {
                errors.put(atr, node.getTypeValidation().errors());
            }

            validate(node.getNodes(), extractedData, atr);
        }
    }
}
