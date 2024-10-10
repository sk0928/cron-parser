package org.deliveroo.cron.parser;

import org.deliveroo.cron.exception.InvalidCronExpressionException;
import org.deliveroo.cron.model.CronSegment;
import org.deliveroo.cron.model.ExpressionOutputItem;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractBaseParser {

  String WILD_CARD = "*";

  abstract CronSegment getCronSegment();

  abstract Pattern getRangeExpressionPattern();

  abstract Pattern getCadenceExpressionPattern();

  abstract Pattern getOneOrMultiExpressionPattern();

  public ExpressionOutputItem parse(String expression) {
    var cronSegmentValue = "";
    var rangeMatcher = getRangeExpressionPattern().matcher(expression);
    var cadenceMatcher = getCadenceExpressionPattern().matcher(expression);
    var oneOrMultiMatcher = getOneOrMultiExpressionPattern().matcher(expression);
    if (expression.equals(WILD_CARD)) {
      cronSegmentValue = buildForWildCard();
    } else if (rangeMatcher.find()) {
      cronSegmentValue = buildFromRangeExpression(rangeMatcher, expression);
    } else if (cadenceMatcher.find()) {
      cronSegmentValue = buildFromCadenceExpression(cadenceMatcher, expression);
    } else if (oneOrMultiMatcher.find()) {
      cronSegmentValue = buildFromOneOrMultiExpression(expression);
    } else {
      throw new InvalidCronExpressionException(String.format("Incorrect %s: %s", getCronSegment(), expression));
    }
    return new ExpressionOutputItem(getCronSegment(), cronSegmentValue);
  }

  private String buildForWildCard() {
    // building for min and max
    return CronUtils.buildFromSegmentRange(getCronSegment().getSegmentRange());
  }

  private String buildFromRangeExpression(Matcher rangeMatcher, String expression) {
    int exp_min = Integer.parseInt(rangeMatcher.group(1));
    int exp_max = Integer.parseInt(rangeMatcher.group(2));
    if(exp_min > exp_max){
      throw new InvalidCronExpressionException(String.format("Incorrect %s: %s", getCronSegment(), expression));
    }
    return CronUtils.buildStringFromIntRange(exp_min, exp_max);
  }

  private String buildFromCadenceExpression(Matcher cadenceMatcher, String expression) {
    int min;
    int max;
    if(cadenceMatcher.group(1).equals(WILD_CARD)){
      min = getCronSegment().getSegmentRange().min();
      max = getCronSegment().getSegmentRange().max();
    } else {
      min = Integer.parseInt(cadenceMatcher.group(2));
      max = Integer.parseInt(cadenceMatcher.group(3));
      if(min > max){
        throw new InvalidCronExpressionException(String.format("Incorrect %s: %s", getCronSegment(), expression));
      }
    }
    int cadence = Integer.parseInt(cadenceMatcher.group(4));
    return CronUtils.buildStringFromCadence(min, max, cadence);
  }

  private String buildFromOneOrMultiExpression(String expression) {
    List<String> listItems = Arrays.stream(expression.split(",")).toList();
    return CronUtils.buildStringFromIterables(listItems);
  }

}
