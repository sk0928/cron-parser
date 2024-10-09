package org.deliveroo.cron.model;

public record SegmentRange(int min, int max) {

  public boolean isValidEntry(int number) {
    return number >= min && number <= max;
  }
}
