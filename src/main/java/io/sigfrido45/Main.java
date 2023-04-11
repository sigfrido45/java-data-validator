package io.sigfrido45;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.payload.TypeValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.Error;
import io.sigfrido45.validation.MessageGetter;

import java.util.*;

public class Main {
  public static void main(String[] args) {

    var payload = new HashMap<>() {{
      put("numbers", new ArrayList<>() {{
        add(1);
        add("2");
        add(null);
      }});
    }};

    var node = ParentNode.<Map<String, Object>>build()
      .addNode(
        ChildNode.<List<Object>>build()
          .setValidator(
            TypeValidator.list("numbers").present(true).nullable(false).cast()
              .forEach(
                ChildNode.<Integer>build().setValidator(
                  TypeValidator.int_().present(true).nullable(false).cast().custom(
                    context -> {
                      return new Error("", "");
                    }
                  )
                )
              )
          )
      );

    var nodeValidator = NodeValidator.validateNode((ParentNode<?>) node, payload, new MessageGetter() {
      @Override
      public String getMessage(String code, String... args) {
        System.out.println("get message " + code + " - args "+ Arrays.toString(args));
        return "xd";
      }

      @Override
      public String getMessage(String code) {
        System.out.println("get message " + code);
        return "xd";
      }
    });
    System.out.println(nodeValidator.getErrors());
    System.out.println(nodeValidator.getValidated());
  }
}