package org.deliveroo.cron.parser;

import org.deliveroo.cron.exception.InvalidCronExpressionException;
import org.deliveroo.cron.model.ExpressionOutputItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MinuteParserTest {

  AbstractBaseParser abstractBaseParser;

  @BeforeEach
  void setup(){
    abstractBaseParser = new MinuteParser();
  }

  @ParameterizedTest
  @ValueSource(strings = {"123", "-3", "x"})
  void shouldThrowExceptionForInvalidValues(String expression) {
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining(String.format("Incorrect %s", abstractBaseParser.getCronSegment()));
  }


  @ParameterizedTest
  @ValueSource(strings = {"13-77", "-3-17", "x-12", "3-", "-", "0-63", "59-43"})
  void shouldThrowExceptionForInvalidRanges(String expression) {
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining(String.format("Incorrect %s", abstractBaseParser.getCronSegment()));
  }

  @ParameterizedTest
  @ValueSource(strings = {"3-77/24", "-3-17/10", "x-12/", "31-13/3", "*-/12"})
  void shouldThrowExceptionForInvalidCadence(String expression) {
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining(String.format("Incorrect %s", abstractBaseParser.getCronSegment()));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1,13,16,47,89", "-1,3,7", "78"})
  void shouldThrowExceptionForInvalidMultipleValues(String expression) {
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining(String.format("Incorrect %s", abstractBaseParser.getCronSegment()));
  }

  @Test
  void shouldParseWildCardWithAllPossibleValuesInRange() {
    ExpressionOutputItem outputItem = abstractBaseParser.parse("*");
    String expectedString = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseRangeAsExpected() {
    ExpressionOutputItem outputItem = abstractBaseParser.parse("3-7");
    String expectedString = "3 4 5 6 7";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseCadenceAsExpected() {
    ExpressionOutputItem outputItem = abstractBaseParser.parse("*/6");
    String expectedString = "0 6 12 18 24 30 36 42 48 54";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseCadenceWithRangeAsExpected() {
    ExpressionOutputItem outputItem = abstractBaseParser.parse("1-7/2");
    String expectedString = "1 3 5 7";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseMultipleValuesAsExpected() {
    ExpressionOutputItem outputItem = abstractBaseParser.parse("1,3,4,7,8,9,12");
    String expectedString = "1 3 4 7 8 9 12";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }
}
