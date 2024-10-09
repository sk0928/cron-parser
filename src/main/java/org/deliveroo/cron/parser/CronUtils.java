package org.deliveroo.cron.parser;

import org.deliveroo.cron.model.SegmentRange;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CronUtils {

  public static String buildStringFromIntRange(int min, int max){
    return String.join(" ", IntStream.rangeClosed(min, max).boxed().map(Object::toString).toList());
  }

  public static String buildFromSegmentRange(SegmentRange segmentRange){
    return String.join(" ", IntStream.rangeClosed(segmentRange.min(), segmentRange.max())
        .boxed().map(Object::toString).toList());
  }

  public static String buildStringFromIterables(List<String> listOfItems) {
    return String.join(" ", listOfItems);
  }

  public static String buildStringFromCadence(int min, int max, int cadence) {
    List<String> items = new ArrayList<>();
    for(int i = min; i<=max; i+=cadence) {
      items.add(String.valueOf(i));
    }
    return String.join(" ", items);
  }
}
