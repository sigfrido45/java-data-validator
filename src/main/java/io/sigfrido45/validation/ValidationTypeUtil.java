package io.sigfrido45.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationTypeUtil {


  public static CastInfo<Integer> getIntCastInfo(Object value) {
    var castedInfo = new CastInfo<Integer>();
    var strValue = String.valueOf(value);
    if (strValue.equalsIgnoreCase("null")) {
      castedInfo.setCasted(null);
      castedInfo.setValid(true);
    } else {
      try {
        castedInfo.setCasted(Integer.valueOf(strValue));
        castedInfo.setValid(true);
      } catch (Exception e) {
        castedInfo.setValid(false);
      }
    }
    return castedInfo;
  }

  public static CastInfo<List<Object>> getListCastInfo(Object value) {
    var castedInfo = new CastInfo<List<Object>>();
    try {
      var newList = (List<?>) value;
      var another = new ArrayList<>();
      another.addAll(newList);
      castedInfo.setCasted(another);
      castedInfo.setValid(true);
    } catch (Exception e) {
      castedInfo.setValid(false);
    }
    return castedInfo;
  }

  public static CastInfo<Long> getLongCastInfo(Object value) {
    var castedInfo = new CastInfo<Long>();
    var strValue = String.valueOf(value);
    if (strValue.equalsIgnoreCase("null")) {
      castedInfo.setCasted(null);
      castedInfo.setValid(true);
    } else {
      try {
        castedInfo.setCasted(Long.valueOf(strValue));
        castedInfo.setValid(true);
      } catch (Exception e) {
        castedInfo.setValid(false);
      }
    }
    return castedInfo;
  }

  public static CastInfo<String> getStringCastInfo(Object value) {
    var castedInfo = new CastInfo<String>();
    var strVal = String.valueOf(value);
    if (strVal.equalsIgnoreCase("null")) {
      castedInfo.setCasted(null);
    } else {
      castedInfo.setCasted(strVal);
    }
    castedInfo.setValid(true);
    return castedInfo;
  }
}
