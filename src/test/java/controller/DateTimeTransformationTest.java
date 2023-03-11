package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;


public class DateTimeTransformationTest {

    DateTimeTransformation dateTimeTransformation = new DateTimeTransformation();

    @Test
    public void test_happyPath() throws Exception{
        String myDateTime = "02-02-2023 13:00:00";
        String myTimeZone = "CST";
        ZonedDateTime convertedDateTime = dateTimeTransformation.getConvertedDateTime(myDateTime, myTimeZone);

        Assertions.assertNotNull(convertedDateTime);
        Assertions.assertEquals(TimeZone.getTimeZone(myTimeZone).toZoneId(), convertedDateTime.getZone());
    }

    @Test
    public void test_datetimeNoSecondsInvalid(){
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = "MST";

        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getConvertedDateTime(myDateTime, myTimeZone);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeNoMinutesInvalid(){
        String myDateTime = "02-02-2023 13::00";
        String myTimeZone = "MST";

        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getConvertedDateTime(myDateTime, myTimeZone);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeFormatInvalid(){
        String myDateTime = "02/13/2023 22:00";
        String myTimeZone = "MST";

        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getConvertedDateTime(myDateTime, myTimeZone);
        }, "DateTimeParseException was expected for invalid datetime format");    }

    @Test
    public void test_datetimeTimeZoneInvalid() {
        String myDateTime = "02-02-2023 13:00:00";
        String myTimeZone = "ABC";

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getConvertedDateTime(myDateTime, myTimeZone);
        }, "NullPointerException was expected for invalid timezone format");
    }

    @Test
    public void test_datetimeTimeZoneFormatInvalid() {
        String myDateTime = "02-02-2023 13:00:00";
        String myTimeZone = "World";

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getConvertedDateTime(myDateTime, myTimeZone);
        }, "NullPointerException was expected for invalid timezone format longer than 3 characters");
    }
}
