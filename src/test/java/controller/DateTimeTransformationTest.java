package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
        String considerDS = "No";
        Map<String, String> myTimes = dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
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
        String considerDS = "No";
        Map<String, String> myTimes = dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
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
        String considerDS = "No";
        Map<String, String> myTimes = dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
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
        String considerDS = "Yes";
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone,considerDS);
        }, "NullPointerException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeEmpty(){
        String myDateTime = "";
        String myTimeZone = "MST";
        String considerDS = "Yes";
        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeWithSecondsInvalid(){
        String myDateTime = "02-02-2023 13:00:00";
        String myTimeZone = "MST";
        String considerDS = "No";
        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeNoMinutesInvalid(){
        String myDateTime = "02-02-2023 13::00";
        String myTimeZone = "MST";
        String considerDS = "Yes";
        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_datetimeFormatInvalid(){
        String myDateTime = "02/13/2023 22:00:00";
        String myTimeZone = "MST";
        String considerDS = "no";
        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
        }, "DateTimeParseException was expected for invalid datetime format");    }

    @Test
    public void test_timezoneNull(){
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = null;
        String considerDS = "Yes";
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
        }, "NullPointerException was expected for invalid datetime format");    }

    @Test
    public void test_timezoneEmpty(){
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = "";
        String considerDS = "Yes";
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
        }, "NullPointerException was expected for invalid datetime format");    }

    @Test
    public void test_datetimeTimeZoneInvalid() {
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = "ABC";
        String considerDS = "Yes";
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
        }, "NullPointerException was expected for invalid timezone format");
    }

    @Test
    public void test_datetimeTimeZoneFormatInvalid() {
        String myDateTime = "02-02-2023 13:00";
        String myTimeZone = "World";
        String considerDS = "Yes";

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            dateTimeTransformation.getTimes(myDateTime, myTimeZone, considerDS);
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
    public void test_getUTCZonedDateTime_HappyPath() {
        String givenDateTime = "03-21-2023 13:00";
        String expectedUTCZone = "UTC";
        String considerDS = "Yes";

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").withZone(ZoneId.of(expectedUTCZone));
        ZonedDateTime expectedUTCTime = ZonedDateTime.parse("03-21-2023 13:00", fmt);

        Assertions.assertEquals(expectedUTCTime, dateTimeTransformation.getUTCZonedDateTime(givenDateTime));
        Assertions.assertEquals(expectedUTCZone, dateTimeTransformation.getUTCZonedDateTime(givenDateTime).getZone().getId());
    }

    @Test
    public void test_getUTCZonedDateTime_Fails() {
        String givenDateTime = "03-21-2023 13:00";
        String unexpectedUTCZone = "CST";
        String expectedUTCZone = "UTC";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").withZone(ZoneId.of(ZoneId.SHORT_IDS.get(unexpectedUTCZone)));
        ZonedDateTime expectedUTCTime = ZonedDateTime.parse("03-21-2023 13:00", fmt);

        Assertions.assertNotEquals(unexpectedUTCZone, dateTimeTransformation.getUTCZonedDateTime(givenDateTime).getZone().getId());
        Assertions.assertEquals(expectedUTCZone, dateTimeTransformation.getUTCZonedDateTime(givenDateTime).getZone().getId());
    }

    @Test
    public void test_getUTCLocalDateTime_HappyPath() {
        String givenDateTime = "06-21-2023 13:00";
        String expectedUTCZone = "UTC";
        String considerDS = "No";

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").withZone(ZoneId.of(expectedUTCZone));
        LocalDateTime expectedUTCTime = LocalDateTime.parse("06-21-2023 13:00", fmt);

        Assertions.assertEquals(expectedUTCTime, dateTimeTransformation.getUTCLocalDateTime(givenDateTime));

        String givenDateTime2 = "01-21-2023 13:00";
        LocalDateTime expectedUTCTime2 = LocalDateTime.parse("01-21-2023 13:00", fmt);
        Assertions.assertEquals(expectedUTCTime2, dateTimeTransformation.getUTCLocalDateTime(givenDateTime2));
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
        String considerDS = "No";
        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(givenDateTime, myTimeZone, considerDS);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_getConvertedDateTimeString_NoDaylightSavings_HappyPath() throws Exception {
        String givenDateTime = "03-21-2023 13:00";
        String givenTimeZone = "CST";
        String considerDS = "NO";

       Assertions.assertEquals("Tuesday 03-21-2023 08:00",
               dateTimeTransformation.getConvertedDateTimeString(givenDateTime,givenTimeZone,considerDS));
    }

    @Test
    public void test_getConvertedDateTimeString_YesDaylightSavings_HappyPath() throws Exception {
        String considerDS = "Yes";

        //This test will show the difference between when daylight savings is active vs not.
        //In January the difference with UTC is -5 during DST and -6 during Standard Time.

        //Standard time, DST not active
        String givenDateTime = "01-21-2023 13:00";
        String givenTimeZone = "CST";
        Assertions.assertEquals("Saturday 01-21-2023 07:00",
                dateTimeTransformation.getConvertedDateTimeString(givenDateTime,givenTimeZone,considerDS));

        //Daylight Savings time active
        String givenDateTime2 = "06-21-2023 13:00";
        String givenTimeZone2 = "CST";
        Assertions.assertEquals("Wednesday 06-21-2023 08:00",
                dateTimeTransformation.getConvertedDateTimeString(givenDateTime2,givenTimeZone2,considerDS));
    }

    @Test
    public void test_daylightSavings_NoDaylightSavings_HappyPath() throws Exception {
        //This test will show the difference between when daylight savings is chosen vs when
        // the user chooses not to consider daylight savings
        String givenDateTime = "06-21-2023 13:00";
        String givenTimeZone = "CST";

        String considerDS = "Yes";
        Assertions.assertEquals("Wednesday 06-21-2023 08:00",
                dateTimeTransformation.getConvertedDateTimeString(givenDateTime,givenTimeZone,considerDS));

        String considerDS2 = "No";
        Assertions.assertEquals("Wednesday 06-21-2023 08:00",
                dateTimeTransformation.getConvertedDateTimeString(givenDateTime,givenTimeZone,considerDS2));
    }

    @Test
    public void test_getConvertedDateTimeString_Fails() {
        String givenDateTime = "03-21-2023 13:00:00";
        String myTimeZone = "CST";
        String considerDS = "No";
        DateTimeParseException thrown = Assertions.assertThrows(DateTimeParseException.class, () -> {
            dateTimeTransformation.getTimes(givenDateTime, myTimeZone, considerDS);
        }, "DateTimeParseException was expected for invalid datetime format");
    }

    @Test
    public void test_considerDaylightSavings_True() throws Exception
    {
        String userInput = "yes";

        Assertions.assertTrue(dateTimeTransformation.considerDaylightSavings(userInput));
    }

    @Test
    public void test_considerDaylightSavings_False() throws Exception
    {
        String userInput = "NO";

        Assertions.assertFalse(dateTimeTransformation.considerDaylightSavings(userInput));
    }

    @Test
    public void test_considerDaylightSavings_Invalid() throws Exception
    {
        String userInput = "Something";

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            dateTimeTransformation.considerDaylightSavings(userInput);
        }, "Exception was expected for invalid daylight savings time string.");
    }
}
