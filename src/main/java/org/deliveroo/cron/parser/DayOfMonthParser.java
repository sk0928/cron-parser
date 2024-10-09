package org.deliveroo.cron.parser;

import org.deliveroo.cron.model.CronSegment;

import java.util.regex.Pattern;

public class DayOfMonthParser extends AbstractBaseParser {

  private static final String WILD_CARD = "*";
  //regex for field for a range of values, e.g. 5-10
  private static final Pattern RANGE_EXP = Pattern.compile("^([1-9]|[12]\\d|3[01])-([1-9]|[12]\\d|3[01])$");

  //regex for field that contains a step, ie a slash, e.g. */15
  private static final Pattern CADENCE_EXP = Pattern.compile("^(\\*|([1-9]|[12]\\d|3[01])-([1-9]|[12]\\d|3[01]))/([1-9]|[12]\\d|3[01])$");

  //regex for field that contains a single value or a comma separated list, e.g. 5 or 5,6,7,8
  private static final Pattern ONE_OR_MULTI_EXP = Pattern.compile("^(?:[1-9]|[12]\\d|3[01])(?:,(?:[1-9]|[12]\\d|3[01]))*$");

  @Override
  public CronSegment getCronSegment() {
    return CronSegment.DAY_OF_MONTH;
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
