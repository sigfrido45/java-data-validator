package io.sigfrido45;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.payload.TypeValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.Error;
import io.sigfrido45.validation.IntTypeValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) {

    var payload = new HashMap<>() {{
      put("numbers", new ArrayList<>() {{
        add(1);
        add("2");
        add(null);
      }});
    }};

    var node = ParentNode.<Map<String, Object>>build("data")
      .addNode(
        ChildNode.<List<Object>>build()
          .setValidator(
            TypeValidator.list("numbers", Integer.class).present(true).nullable(false).cast()
              .forEach(
                ChildNode.<Integer>build().setValidator(
                  TypeValidator.int_().present(true).nullable(false).cast().custom(
                    context -> {
                      var val = (IntTypeValidator) context.get("val");
                      System.out.println("val " + val.validated());
                      return new Error("");
                    }
                  )
                )
              )
          )
      );

    var nodeValidator = NodeValidator.validateNode((ParentNode<?>) node, payload);
    System.out.println(nodeValidator.getErrors());
    System.out.println(nodeValidator.getValidated());
  }
}