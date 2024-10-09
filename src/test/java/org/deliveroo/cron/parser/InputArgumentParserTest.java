package org.deliveroo.cron.parser;

import org.deliveroo.cron.exception.InvalidArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputArgumentParserTest {

  InputArgumentParser inputArgumentParser;

  @Test
  void shouldThrowExceptionWhenInvalidNumberOfArgumentsPassed() {
    String[] args = "*/15 0 1,15 * 1-5".split(" ");
    assertThrows(InvalidArgumentException.class, () -> InputArgumentParser.parseAndCollectResponse(args));
  }

  @Test
  void shouldThrowExceptionWhenEmptyArgPassed() {
    String[] args = "*/15 0  ,15 * 1-5 /usr/bin/find".split(" ");
    assertThrows(InvalidArgumentException.class, () -> InputArgumentParser.parseAndCollectResponse(args));
  }

  @Test
  void shouldThrowExceptionWhenMoreThanExpectedArgsArePassed() {
    String[] args = "*/15 0 1 1,15 * 1-5 /usr/bin/find".split(" ");
    assertThrows(InvalidArgumentException.class, () -> InputArgumentParser.parseAndCollectResponse(args));
  }
}