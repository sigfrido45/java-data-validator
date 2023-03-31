package io.sigfrido45;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.payload.TypeValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.Node;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.MapTypeValidator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static io.sigfrido45.tree.Node.build;

public class Main {
  public static void main(String[] args) {

    var payload = new HashMap<>() {{
      put("name", "");
      put("another", new HashMap<>() {{
        put("name", "lmao");
      }});
    }};

    var parentNode = new ParentNode<Map<String, Object>>();

    parentNode
      .addNode(
        new ChildNode<String>().setValidator(
          TypeValidator.str("name").ifPresent().cast().required(true).min(1)
        )
      );


    var nodeValidator = NodeValidator.validateNode(parentNode, payload);
    System.out.println(nodeValidator.getErrors());
    System.out.println(nodeValidator.getValidated());
  }
}