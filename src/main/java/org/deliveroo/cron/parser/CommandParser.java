package org.deliveroo.cron.parser;

import org.deliveroo.cron.exception.NotImplementedException;
import org.deliveroo.cron.model.ExpressionOutputItem;
import org.deliveroo.cron.model.CronSegment;

import java.util.regex.Pattern;

public class CommandParser extends AbstractBaseParser {
  @Override
  public ExpressionOutputItem parse(String expression) {
    return new ExpressionOutputItem(getCronSegment(), expression);
  }

  @Override
  public CronSegment getCronSegment() {
    return CronSegment.COMMAND;
  }

  @Override
  Pattern getRangeExpressionPattern() {
    throw new NotImplementedException();
  }

  @Override
  Pattern getCadenceExpressionPattern() {
    throw new NotImplementedException();
  }

  @Override
  Pattern getOneOrMultiExpressionPattern() {
    throw new NotImplementedException();
  }
}
