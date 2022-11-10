package io.sigfrido45.tree;

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

            var extractedData = data instanceof Map ? ((Map<?, ?>) data).get(node.getData().getAttrName()) : data;
            node.getData().setValue(extractedData);
            node.getData().validate();

            var atr = (attr.isEmpty() ? attr : attr + ".") + node.getData().getAttrName();
            if (node.getData().isValid()) {
                validated.put(node.getData().getAttrName(), node.getData().validated());
            } else {
                errors.put(atr, node.getData().errors());
            }

            validate(node.getNodes(), extractedData, atr);
        }
    }
}
