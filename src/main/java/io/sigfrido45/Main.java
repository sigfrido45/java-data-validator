package io.sigfrido45;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.payload.TypeValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.ParentNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) {

    var payload = new HashMap<>() {{
      put("persons", new ArrayList<>() {{
        add(new HashMap<>() {{
          put("name", "123");
        }});
      }});
    }};

    var parentNode = new ParentNode<Map<String, Object>>("data");

    var listSchema = TypeValidator.list("persons", Map.class)
      .cast().forEach(
        (ParentNode<?>) new ParentNode<Map<String, Object>>().addNode(
          new ChildNode<String>().setValidator(TypeValidator.str("name").ifPresent().required(true).min(2))
        )
      );

    parentNode
      .addNode(
        new ChildNode<List<Object>>().setValidator(listSchema)
      );

    var nodeValidator = NodeValidator.validateNode(parentNode, payload);
    System.out.println(nodeValidator.getErrors());
    System.out.println(nodeValidator.getValidated());
  }
}