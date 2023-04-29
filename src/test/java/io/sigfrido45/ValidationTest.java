package io.sigfrido45;

import io.sigfrido45.payload.NodeValidator;
import io.sigfrido45.payload.TypeValidator;
import io.sigfrido45.tree.ChildNode;
import io.sigfrido45.tree.ParentNode;
import io.sigfrido45.validation.MessageGetter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationTest {


  @Test
  void validatePayloadError() {
    var payload = new HashMap<>() {{
      put("name", "1");
      put("another", new HashMap<>() {{
        put("name2", "");
      }});
      put("numbers", new ArrayList<>() {{
        add("1");
        add(2);
        add(null);
      }});
      put("persons", new ArrayList<>() {{
        add(new HashMap<>() {{
          put("name", "hole");
        }});
        add(new HashMap<>() {{
          put("name", "");
        }});
      }});
    }};

    var schema = ParentNode.build()
      .addNode(
        ChildNode.<String>build().setValidator(
          TypeValidator.str("name").present(true).nullable(false).cast().gte(5)
        )
      )
      .addNode(
        ParentNode.build("another").addNode(
          ChildNode.<String>build().setValidator(
            TypeValidator.str("name2").present(true).nullable(false).cast().gte(1)
          )
        )
      )
      .addNode(
        ChildNode.<List<Object>>build().setValidator(
          TypeValidator.list("numbers").present(true).nullable(false).cast().forEach(
            ChildNode.<Integer>build().setValidator(
              TypeValidator.int_().present(true).nullable(false).cast()
            )
          )
        )
      )
      .addNode(
        ChildNode.<List<Object>>build().setValidator(
          TypeValidator.list("persons").present(true).nullable(false).cast().forEach(
            ParentNode.build().addNode(
              ChildNode.<String>build().setValidator(
                TypeValidator.str("name").present(true).nullable(false).cast().gte(1)
              )
            )
          )
        )
      );
    var response = NodeValidator.validateNode((ParentNode<?>) schema, payload, new CustomMessageGetter());
    assertFalse(response.isValid());
    assertFalse(response.getErrors().isEmpty());
  }

  @Test
  void validatePayloadOk() {
    var payload = new HashMap<>() {{
      put("name", "asdfasdf");
      put("another", new HashMap<>() {{
        put("name2", "asdfasd");
      }});
      put("numbers", new ArrayList<>() {{
        add("1");
        add(2);
        add(3);
      }});
      put("persons", new ArrayList<>() {{
        add(new HashMap<>() {{
          put("name", "hole");
        }});
        add(new HashMap<>() {{
          put("name", "asdfasdf");
        }});
      }});
    }};
    var schema = ParentNode.build()
      .addNode(
        ChildNode.<String>build().setValidator(
          TypeValidator.str("name").present(true).nullable(false).cast().gte(5)
        )
      )
      .addNode(
        ParentNode.build("another").addNode(
          ChildNode.<String>build().setValidator(
            TypeValidator.str("name2").present(true).nullable(false).cast().gte(1)
          )
        )
      )
      .addNode(
        ChildNode.<List<Object>>build().setValidator(
          TypeValidator.list("numbers").present(true).nullable(false).cast().forEach(
            ChildNode.<Integer>build().setValidator(
              TypeValidator.int_().present(true).nullable(false).cast()
            )
          )
        )
      )
      .addNode(
        ChildNode.<List<Object>>build().setValidator(
          TypeValidator.list("persons").present(true).nullable(false).cast().forEach(
            ParentNode.build().addNode(
              ChildNode.<String>build().setValidator(
                TypeValidator.str("name").present(true).nullable(false).cast().gte(1)
              )
            )
          )
        )
      );
    var response = NodeValidator.validateNode((ParentNode<?>) schema, payload, new CustomMessageGetter());
    assertTrue(response.isValid());
    assertTrue(response.getErrors().isEmpty());
  }

  @Test
  public void validateReactiveFails() {
    var payload = new HashMap<>() {{
      put("persons", new ArrayList<>() {{
        add(new HashMap<>() {{
          put("name", "");
        }});
        add(new HashMap<>() {{
          put("name", null);
        }});
      }});
    }};

    var schema = ParentNode.build()
      .addNode(
        ChildNode.<List<Object>>build().setValidator(
          TypeValidator.listReactive("persons").present(true).nullable(false).cast().forEach(
            ParentNode.build().addNode(
              ChildNode.<String>build().setValidator(
                TypeValidator.strReactive("name").present(true).nullable(false).cast().gte(1)
              )
            )
          )
        )
      );

    var res = NodeValidator.validateNodeReactive((ParentNode<?>) schema, payload, new CustomMessageGetter()).block();
    System.out.println(res.isValid());
    System.out.println(res.getValidated());
    System.out.println(res.getErrors());
//    assertFalse(res.isValid());
//    assertFalse(res.getErrors().isEmpty());
  }

  @Test
  public void validateReactiveOk() {
    var payload = new HashMap<>() {{
      put("name", "asdfasdf");
      put("another", new HashMap<>() {{
        put("name2", "asdfasd");
      }});
      put("numbers", new ArrayList<>() {{
        add("1");
        add(2);
        add(3);
      }});
      put("persons", new ArrayList<>() {{
        add(new HashMap<>() {{
          put("name", "hole");
        }});
        add(new HashMap<>() {{
          put("name", "asdfasdf");
        }});
      }});
    }};
    var schema = ParentNode.build()
      .addNode(
        ChildNode.<String>build().setValidator(
          TypeValidator.strReactive("name").present(true).nullable(false).cast().gte(5)
        )
      )
      .addNode(
        ParentNode.build("another").addNode(
          ChildNode.<String>build().setValidator(
            TypeValidator.strReactive("name2").present(true).nullable(false).cast().gte(1)
          )
        )
      )
      .addNode(
        ChildNode.<List<Object>>build().setValidator(
          TypeValidator.listReactive("numbers").present(true).nullable(false).cast().forEach(
            ChildNode.<Integer>build().setValidator(
              TypeValidator.intReactive().present(true).nullable(false).cast()
            )
          )
        )
      )
      .addNode(
        ChildNode.<List<Object>>build().setValidator(
          TypeValidator.listReactive("persons").present(true).nullable(false).cast().forEach(
            ParentNode.build().addNode(
              ChildNode.<String>build().setValidator(
                TypeValidator.strReactive("name").present(true).nullable(false).cast().gte(1)
              )
            )
          )
        )
      );
    var res = NodeValidator.validateNodeReactive((ParentNode<?>) schema, payload, new CustomMessageGetter()).block();
    assertTrue(res.isValid());
    assertTrue(res.getErrors().isEmpty());
  }

  public static class CustomMessageGetter implements MessageGetter {

    @Override
    public String getMessage(String code, String... args) {
      return "xd";
    }

    @Override
    public String getMessage(String code) {
      return "xd";
    }
  }

}