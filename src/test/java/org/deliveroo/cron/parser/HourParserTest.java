package org.deliveroo.cron.parser;

import org.deliveroo.cron.exception.InvalidCronExpressionException;
import org.deliveroo.cron.model.ExpressionOutputItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HourParserTest {

  AbstractBaseParser abstractBaseParser;

  @BeforeEach
  void setup(){
    abstractBaseParser = new HourParser();
  }

  @ParameterizedTest
  @ValueSource(strings = {"123", "-3", "x"})
  void shouldThrowExceptionForInvalidValues(String expression) {
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining(String.format("Incorrect %s", abstractBaseParser.getCronSegment()));
  }


  @ParameterizedTest
  @ValueSource(strings = {"3-27", "-3-17", "x-12", "3-", "-", "0-33"})
  void shouldThrowExceptionForInvalidRanges(String expression) {
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining(String.format("Incorrect %s", abstractBaseParser.getCronSegment()));
  }

  @ParameterizedTest
  @ValueSource(strings = {"3-17/24", "-3-17/10", "x-12/", "3-33/11", "*-/12"})
  void shouldThrowExceptionForInvalidCadence(String expression) {
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining(String.format("Incorrect %s", abstractBaseParser.getCronSegment()));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1,13,26", "-1,3,7", "38"})
  void shouldThrowExceptionForInvalidMultipleValues(String expression) {
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining(String.format("Incorrect %s", abstractBaseParser.getCronSegment()));
  }

  @Test
  void shouldParseWildCardWithAllPossibleValuesInRange() {
    ExpressionOutputItem outputItem = abstractBaseParser.parse("*");
    String expectedString = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseRangeAsExpected() {
    ExpressionOutputItem outputItem = abstractBaseParser.parse("3-17");
    String expectedString = "3 4 5 6 7 8 9 10 11 12 13 14 15 16 17";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseCadenceAsExpected() {
    ExpressionOutputItem outputItem = abstractBaseParser.parse("*/4");
    String expectedString = "0 4 8 12 16 20";
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