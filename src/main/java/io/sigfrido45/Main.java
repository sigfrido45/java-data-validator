package io.sigfrido45;

import io.sigfrido45.payload.NodeMapValidator;
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
          put("name", "");
        }});
      }});
    }};

    var parentNode = new ParentNode<Map<String, Object>>("data");
    var listSchema = TypeValidator.list("persons")
        .cast().forEach()

    parentNode
      .addNode(
        new ChildNode<List<HashMap<String, Object>>>().setValidator(listSchema)
      );

    var nodeValidator = NodeMapValidator.validateNode(parentNode, payload);
    System.out.println(nodeValidator.getErrors());
    System.out.println(nodeValidator.getValidated());
  }
}