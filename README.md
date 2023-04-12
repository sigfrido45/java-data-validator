# Java data validator

Validates data from a map type. It let you define a validator schema for each attribute in the map. <br>
<strong>It's still on development</strong>

## Validation

### Payload

Let suppose that you have a payload like this

```java
var payload = new HashMap<>() {{
    put("name", "123");
    put("another", new HashMap<>() {{
        put("name", "lmao");
    }});
}};
```

### Schema

We define the schema validator for that payload like this

```java
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
          TypeValidator.str("name").present(true).nullable(false).cast().min(5)
        )
      )
      .addNode(
        ParentNode.build("another").addNode(
          ChildNode.<String>build().setValidator(
            TypeValidator.str("name2").present(true).nullable(false).cast().min(1)
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
                TypeValidator.str("name").present(true).nullable(false).cast().min(1)
              )
            )
          )
        )
      );
```


### Results
Finally we validate that schema 

```java
var nodeValidator = NodeValidator.validateNode(node, payload);
System.out.println(nodeValidator.getErrors());
System.out.println(nodeValidator.getValidated());
```


