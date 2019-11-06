package ir.ac.kntu.exception;

import java.time.format.DateTimeParseException;

public class InvalidDateTimeException extends DateTimeParseException {
    public InvalidDateTimeException(String message, CharSequence parsedData, int errorIndex) {
        super(message, parsedData, errorIndex);
    }

    public InvalidDateTimeException(String message, CharSequence parsedData, int errorIndex, Throwable cause) {
        super(message, parsedData, errorIndex, cause);
    }
}
