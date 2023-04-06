package io.sigfrido45.validation;

import lombok.Data;

@Data
public  class CastInfo<T> {
  private boolean isValid;
  private T casted;
}
