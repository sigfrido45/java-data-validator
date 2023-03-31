package io.sigfrido45;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.payload.TypeValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.ParentNode;

import java.util.HashMap;
import java.util.Map;

public class Main {
  public static void main(String[] args) {

    var payload = new HashMap<>() {{
      put("name", "13");
      put("quantity", "2");
      put("another", new HashMap<>() {{
        put("lmao", "lmao");
      }});
    }};

    var parentNode = new ParentNode<Map<String, Object>>("person");

    parentNode
      .addNode(
        new ChildNode<String>().setValidator(
          TypeValidator.str("name").ifPresent().cast().required(true).min(1)
        )
      )
      .addNode(
        new ChildNode<Long>().setValidator(
          TypeValidator.long_("quantity").ifPresent().cast().required(true).max(5)
        )
      )
      .addNode(
        (new ParentNode<Map<String, Object>>("another"))
          .addNode(
            new ChildNode<String>().setValidator(TypeValidator.str("lmao").ifPresent().cast().required(true).min(10))
          )
      );


    var nodeValidator = NodeValidator.validateNode(parentNode, payload);
    System.out.println(nodeValidator.getErrors());
    System.out.println(nodeValidator.getValidated());
  }
}