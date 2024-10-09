package org.deliveroo.cron;

import lombok.extern.slf4j.Slf4j;
import org.deliveroo.cron.parser.InputArgumentParser;

@Slf4j
public class CronApp {
  public static void main(String[] args) {
    var cronExpressionResult = InputArgumentParser.parseAndCollectResponse(args);
    cronExpressionResult
        .forEach(item -> System.out.println(item.cronSegment().getLabel()+" ".repeat(14-item.cronSegment().getLabel().length())+item.value()));
  }
}