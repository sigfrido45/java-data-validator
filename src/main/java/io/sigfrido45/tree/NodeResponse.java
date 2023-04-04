package io.sigfrido45.tree;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
public class NodeResponse {

  private Map<Object, Object> validated;
  private Map<Object, Object> errors;

  public Map<Object, Object> getValidated() {
    if (!isValid())
      validated.clear();
    return validated;
  }

  public Map<Object, Object> getErrors() {
    return errors;
  }

  public boolean isValid() {
    return errors.isEmpty();
  }
}
