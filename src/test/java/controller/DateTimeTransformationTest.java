package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    @Test
    public void test_isDateTimeValid_isValid() {
        String myDateTime = "03-21-2023 13:00";

        Assertions.assertTrue(dateTimeTransformation.isDateTimeValid(myDateTime));
    }

    @Test
    public void test_isDateTimeValid_isNull() {
        Assertions.assertFalse(dateTimeTransformation.isDateTimeValid(null));
    }

    @Test
    public void test_isDateTimeValid_isInvalidLength() {
        String myDateTime = "03-21-2023 13:00:00";

        Assertions.assertFalse(dateTimeTransformation.isDateTimeValid(myDateTime));
    }

    @Test
    public void test_isDateTimeValid_isInvalidHour() {
        String myDateTime = "Sixteen characts";

        Assertions.assertFalse(dateTimeTransformation.isDateTimeValid(myDateTime));
    }

    @Test
    public void test_isTimeZoneValid_isValid() {
        String myTimeZone = "CST";

        Assertions.assertTrue(dateTimeTransformation.isTimeZoneValid(myTimeZone));
    }

    @Test
    public void test_isTimeZoneValid_isInvalid() {
        String myTimeZone = "Hawaii";

        Assertions.assertFalse(dateTimeTransformation.isTimeZoneValid(myTimeZone));
    }

    @Test
    public void test_getUTCDateTime_HappyPath() {
        String givenDateTime = "03-21-2023 13:00";
        String expectedUTCZone = "UTC";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").withZone(ZoneId.of(expectedUTCZone));
        ZonedDateTime expectedUTCTime = ZonedDateTime.parse("03-21-2023 13:00", fmt);

        Assertions.assertEquals(expectedUTCTime, dateTimeTransformation.getUTCDateTime(givenDateTime));
        Assertions.assertEquals(expectedUTCZone, dateTimeTransformation.getUTCDateTime(givenDateTime).getZone().getId());
    }

    @Test
    public void test_getUTCDateTime_Fails() {
        String givenDateTime = "03-21-2023 13:00";
        String unexpectedUTCZone = "CST";
        String expectedUTCZone = "UTC";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").withZone(ZoneId.of(ZoneId.SHORT_IDS.get(unexpectedUTCZone)));
        ZonedDateTime expectedUTCTime = ZonedDateTime.parse("03-21-2023 13:00", fmt);

        Assertions.assertNotEquals(unexpectedUTCZone, dateTimeTransformation.getUTCDateTime(givenDateTime).getZone().getId());
        Assertions.assertEquals(expectedUTCZone, dateTimeTransformation.getUTCDateTime(givenDateTime).getZone().getId());
    }

    @Test
    public void test_getUTCDateTimeString_HappyPath() {
        String givenDateTime = "03-21-2023 13:00";
        String expectedDateTime = "03-21-2023 13:00";

        Assertions.assertEquals(expectedDateTime, dateTimeTransformation.getUTCDateTimeString(givenDateTime));
    }

    @Test
    public void test_getUTCDateTimeString_Fails() {
        String givenDateTime = "03-21-2023 13:00:00";
        String myTimeZone = "CST";

        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(givenDateTime, myTimeZone);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_getConvertedDateTimeString_HappyPath() {
        String givenDateTime = "03-21-2023 13:00";
        String givenTimeZone = "CST";

       Assertions.assertEquals("Tuesday 03-21-2023 08:00",
               dateTimeTransformation.getConvertedDateTimeString(givenDateTime,givenTimeZone));
    }

    @Test
    public void test_getConvertedDateTimeString_Fails() {
        String givenDateTime = "03-21-2023 13:00:00";
        String myTimeZone = "CST";

        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(givenDateTime, myTimeZone);
        }, "DateTimeParseException was expected for invalid datetime format");
    }
}
