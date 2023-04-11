package io.sigfrido45.tree;

import io.sigfrido45.validation.Error;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class NodeResponse {

  private Map<Object, Object> validated;
  private List<Error> errors;

  public Map<Object, Object> getValidated() {
    if (!isValid())
      validated.clear();
    return validated;
  }

  public List<Error> getErrors() {
    return errors;
  }

  public boolean isValid() {
    return errors.isEmpty();
  }
}
