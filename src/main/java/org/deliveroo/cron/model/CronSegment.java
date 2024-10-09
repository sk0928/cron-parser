package org.deliveroo.cron.model;

import lombok.Getter;

@Getter
public enum CronSegment {
  MINUTE("minute", 1, new SegmentRange(0, 59)),
  HOUR("hour", 2, new SegmentRange(0, 23)),
  DAY_OF_MONTH("day of month", 3, new SegmentRange(1, 31)),
  MONTH("month", 4, new SegmentRange(1, 12)),
  DAY_OF_WEEK("day of week", 5, new SegmentRange(1, 7)),
  COMMAND("command", 6, null);

  private String label;
  private int order;
  private SegmentRange segmentRange;

  CronSegment(String label, int order, SegmentRange segmentRange) {
    this.label = label;
    this.order = order;
    this.segmentRange = segmentRange;
  }
}
