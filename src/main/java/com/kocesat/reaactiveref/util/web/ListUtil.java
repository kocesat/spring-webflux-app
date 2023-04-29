package com.kocesat.reaactiveref.util.web;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

@UtilityClass
public final class ListUtil {

  public static <T> int lastIndex(List<T> sourceList) {
    return sourceList.size() - 1;
  }

  public static <T> List<T> paginate(List<T> list, int page, int pageSize) {
    int fromIndex = (page - 1) * pageSize;
    if (fromIndex > list.size() - 1) {
      return Collections.emptyList();
    }
    int lastIndex = Math.min(fromIndex + pageSize, list.size());
    return list.subList(fromIndex, lastIndex);
  }

}
