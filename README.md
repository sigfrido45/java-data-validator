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
var node = (Node.<Map>build())
                .addNode(
                        Node.<String>build()
                                .setValidation(
                                        TypeValidator.str("name")
                                                .required(true)
                                                .min(1)
                                )
                )
                .addNode(
                        Node.<Map>build()
                                .setValidation(TypeValidator.map("another"))
                                .addNode(
                                        Node.<String>build()
                                                .setValidation(
                                                        TypeValidator.str("name")
                                                                .required(true)
                                                                .min(1)
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


