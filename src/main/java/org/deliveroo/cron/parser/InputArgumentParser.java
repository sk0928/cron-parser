package org.deliveroo.cron.parser;

import lombok.extern.slf4j.Slf4j;
import org.deliveroo.cron.exception.InvalidArgumentException;
import org.deliveroo.cron.model.ExpressionOutputItem;
import org.deliveroo.cron.model.CronSegment;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
public class InputArgumentParser {

  private final static Map<String, AbstractBaseParser> PARSER_ORDERED_BY_EXPRESSION = collectParsersByLabel();

  private static Map<String, AbstractBaseParser> collectParsersByLabel() {
    return Map.of(
        CronSegment.MINUTE.getLabel(), new MinuteParser(),
        CronSegment.HOUR.getLabel(), new HourParser(),
        CronSegment.DAY_OF_MONTH.getLabel(), new DayOfMonthParser(),
        CronSegment.MONTH.getLabel(), new MonthParser(),
        CronSegment.DAY_OF_WEEK.getLabel(), new DayOfWeekParser(),
        CronSegment.COMMAND.getLabel(), new CommandParser()
    );
  }

  public static List<ExpressionOutputItem> parseAndCollectResponse(String[] args) {
    validateArgumentsOrThrow(args);
    var allExpressionsWithLabel = collectArgumentsWithLabel(args);
    return allExpressionsWithLabel.entrySet().stream()
        .map(entry -> {
          log.info("Parsing cron segment {} for expression {}", entry.getKey(), entry.getValue());
          return PARSER_ORDERED_BY_EXPRESSION.get(entry.getKey()).parse(entry.getValue());
        })
        .sorted(Comparator.comparingInt(item -> item.cronSegment().getOrder())).toList();
  }

  private static Map<String, String> collectArgumentsWithLabel(String[] args) {
    return Map.of(
        "minute", args[0],
        "hour", args[1],
        "day of month", args[2],
        "month", args[3],
        "day of week", args[4],
        "command", args[5]
    );
  }

  private static void validateArgumentsOrThrow(String[] args) {
    log.info("Validating Arguments");
    if (args.length != 6 || Arrays.stream(args).anyMatch(String::isBlank) ) {
      throw new InvalidArgumentException("Invalid argument passed, must consist all 6 fields(minute, hour, day of month, month, day of week, command)");
    }
  }
}
