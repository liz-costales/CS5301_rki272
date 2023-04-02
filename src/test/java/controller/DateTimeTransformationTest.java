package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;
import java.util.Map;

public class DateTimeTransformationTest {

    DateTimeTransformation dateTimeTransformation = new DateTimeTransformation();

    @Test
    public void test_happyPath() throws Exception {
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = "CST";
        Map<String, String> myTimes = dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        String myUTCTime = myTimes.get("given");
        String myConvertedTime = myTimes.get("converted");

        Assertions.assertNotNull(myTimes);
        // The output of the given DateTime will be in DD-MM-YYYY HH:MI format
        Assertions.assertEquals("02-02-2023 13:00", myUTCTime);
        // The output of the Convert DateTime will include the Day of the Week.
        Assertions.assertEquals("Thursday 02-02-2023 07:00", myConvertedTime);
    }

    @Test
    public void test_happyPath2() throws Exception {
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = "HST";
        Map<String, String> myTimes = dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        String myUTCTime = myTimes.get("given");
        String myConvertedTime = myTimes.get("converted");

        Assertions.assertNotNull(myTimes);
        // The output of the given DateTime will be in DD-MM-YYYY HH:MI format
        Assertions.assertEquals("02-02-2023 13:00", myUTCTime);
        // The output of the Convert DateTime will include the Day of the Week.
        Assertions.assertEquals("Thursday 02-02-2023 03:00", myConvertedTime);
    }

    @Test
    public void test_happyPath_NextDay() throws Exception {
        String myDateTime = "02-02-2023 22:00";
        String myTimeZone = "JST";
        Map<String, String> myTimes = dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        String myUTCTime = myTimes.get("given");
        String myConvertedTime = myTimes.get("converted");

        Assertions.assertNotNull(myTimes);
        // The output of the given DateTime will be in DD-MM-YYYY HH:MI format
        // In UTC this string DateTime is Thursday 02-02-2023 13:00
        Assertions.assertEquals("02-02-2023 22:00", myUTCTime);
        // The output of the Convert DateTime will include the Day of the Week.
        Assertions.assertEquals("Friday 02-03-2023 07:00", myConvertedTime);
    }

    @Test
    public void test_datetimeNull() {
        String myDateTime = null;
        String myTimeZone = "MST";

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        }, "NullPointerException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeEmpty(){
        String myDateTime = "";
        String myTimeZone = "MST";

        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeWithSecondsInvalid(){
        String myDateTime = "02-02-2023 13:00:00";
        String myTimeZone = "MST";

        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeNoMinutesInvalid(){
        String myDateTime = "02-02-2023 13::00";
        String myTimeZone = "MST";

        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeFormatInvalid(){
        String myDateTime = "02/13/2023 22:00:00";
        String myTimeZone = "MST";

        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        }, "DateTimeParseException was expected for invalid datetime format");    }

    @Test
    public void test_timezoneNull(){
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = null;

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        }, "NullPointerException was expected for invalid datetime format");    }

    @Test
    public void test_timezoneEmpty(){
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = "";

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        }, "NullPointerException was expected for invalid datetime format");    }

    @Test
    public void test_datetimeTimeZoneInvalid() {
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = "ABC";

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        }, "NullPointerException was expected for invalid timezone format");
    }

    @Test
    public void test_datetimeTimeZoneFormatInvalid() {
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = "World";

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone);
        }, "NullPointerException was expected for invalid timezone format longer than 3 characters");
    }
}
