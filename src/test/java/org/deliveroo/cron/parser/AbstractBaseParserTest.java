package org.deliveroo.cron.parser;

import org.deliveroo.cron.exception.InvalidCronExpressionException;
import org.deliveroo.cron.model.ExpressionOutputItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AbstractBaseParserTest {

  AbstractBaseParser abstractBaseParser;

  @BeforeEach
  void setup(){
    abstractBaseParser = new MonthParser();
  }

  @ParameterizedTest
  @ValueSource(strings = {"123", "-3", "x"})
  void shouldThrowExceptionForInvalidValues(String expression) {
    // testing for minutes (all patterns are same)
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining("Incorrect MONTH");
  }


  @ParameterizedTest
  @ValueSource(strings = {"3-17", "-3-17", "x-12", "3-", "-", "0-13"})
  void shouldThrowExceptionForInvalidRanges(String expression) {
    // testing for minutes (all patterns are same)
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining("Incorrect MONTH");
  }

  @ParameterizedTest
  @ValueSource(strings = {"3-17/24", "-3-17/10", "x-12/", "3-13/3", "*-/12"})
  void shouldThrowExceptionForInvalidCadence(String expression) {
    // testing for minutes (all patterns are same)
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining("Incorrect MONTH");
  }

  @ParameterizedTest
  @ValueSource(strings = {"1,13,16", "-1,3,7", "18"})
  void shouldThrowExceptionForInvalidMultipleValues(String expression) {
    // testing for minutes (all patterns are same)
    assertThatThrownBy(() -> abstractBaseParser.parse(expression))
        .isInstanceOf(InvalidCronExpressionException.class)
        .hasMessageContaining("Incorrect MONTH");
  }

  @Test
  void shouldParseWildCardWithAllPossibleValuesInRange() {
    // testing for months (all parsers follow same pattern)
    ExpressionOutputItem outputItem = abstractBaseParser.parse("*");
    String expectedString = "1 2 3 4 5 6 7 8 9 10 11 12";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseRangeAsExpected() {
    // testing for months (all parsers follow same pattern)
    ExpressionOutputItem outputItem = abstractBaseParser.parse("3-7");
    String expectedString = "3 4 5 6 7";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseCadenceAsExpected() {
    // testing for months (all parsers follow same pattern)
    ExpressionOutputItem outputItem = abstractBaseParser.parse("*/2");
    String expectedString = "1 3 5 7 9 11";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseCadenceWithRangeAsExpected() {
    // testing for months (all parsers follow same pattern)
    ExpressionOutputItem outputItem = abstractBaseParser.parse("1-7/2");
    String expectedString = "1 3 5 7";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }

  @Test
  void shouldParseMultipleValuesAsExpected() {
    // testing for months (all parsers follow same pattern)
    ExpressionOutputItem outputItem = abstractBaseParser.parse("1,3,4,7,8,9,12");
    String expectedString = "1 3 4 7 8 9 12";
    assertThat(outputItem.value()).isEqualTo(expectedString);
  }
}