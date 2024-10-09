package org.deliveroo.cron.exception;

public class InvalidCronExpressionException extends RuntimeException{
  public InvalidCronExpressionException(String message) {
    super(message);
  }
}
