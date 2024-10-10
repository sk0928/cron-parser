package org.deliveroo.cron.parser;

import lombok.extern.slf4j.Slf4j;
import org.deliveroo.cron.model.CronSegment;

import java.util.regex.Pattern;

@Slf4j
public class MinuteParser extends AbstractBaseParser {
  //regex for field for a range of values, e.g. 5-10
  private static final Pattern RANGE_EXP = Pattern.compile("^(\\d|[0-5]\\d)-(\\d|[0-5]\\d)$");

  //regex for field that contains a step, ie a slash, e.g. */15
  private static final Pattern CADENCE_EXP = Pattern.compile("^(\\*|(\\d|[0-5]\\d)-(\\d|[0-5]\\d))/(\\d|[0-5]\\d)$");

  //regex for field that contains a single value or a comma separated list, e.g. 5 or 5,6,7,8
  private static final Pattern ONE_OR_MULTI_EXP = Pattern.compile("^(?:\\d|[1-5][\\d)])(?:,(?:\\d|[1-5][\\d)]))*$");

  @Override
  public CronSegment getCronSegment() {
    return CronSegment.MINUTE;
  }

  @Override
  Pattern getRangeExpressionPattern() {
    return RANGE_EXP;
  }

  @Override
  Pattern getCadenceExpressionPattern() {
    return CADENCE_EXP;
  }

  @Override
  Pattern getOneOrMultiExpressionPattern() {
    return ONE_OR_MULTI_EXP;
  }
}
