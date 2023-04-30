package controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class DateTimeTransformation {

    private static final String DATE_FORMAT = "MM-dd-yyyy HH:mm";
    private static final String DATE_FORMAT_DOW = "EEEE MM-dd-yyyy HH:mm";


    public Map<String, String> getTimes(String givenDateTime, String givenTimeZone, String considerDS) throws Exception {

        String timeZone = ZoneId.SHORT_IDS.get(givenTimeZone);
        Map<String, String> myTimes = new HashMap<>();
        String utcDateTime = getUTCDateTimeString(givenDateTime);

        myTimes.put("given", getUTCDateTimeString(givenDateTime));
        myTimes.put("converted", getConvertedDateTimeString(utcDateTime, givenTimeZone, considerDS));

        return myTimes;
    }

    public boolean isDateTimeValid(String givenDateTime)
    {
        if(givenDateTime != null && givenDateTime.length() == 16)
        {
            try {
                DateTimeFormatter.ofPattern(DATE_FORMAT).parse(givenDateTime).toString();
                return true;
            }
            catch (DateTimeParseException e) {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean isTimeZoneValid(String givenTimeZone)
    {
        if(givenTimeZone != null && ZoneId.SHORT_IDS.containsKey(givenTimeZone))
        {
            return true;
        }
        return false;
    }

    ZonedDateTime getUTCZonedDateTime (String givenDateTime)
    {
        ZonedDateTime utcDateTime;

        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneId.of("UTC"));
            utcDateTime = ZonedDateTime.parse(givenDateTime, fmt);
        } catch (Exception e) {
            System.out.println("The datetime or timezone string provided was null or invalid. Cannot convert " +
                    "string to datetime. Exception is: " + e);
            throw e;
        }

        return utcDateTime;
    }

    LocalDateTime getUTCLocalDateTime (String givenDateTime)
    {
        LocalDateTime utcDateTime;

        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneId.of("UTC"));
            utcDateTime = LocalDateTime.parse(givenDateTime, fmt);
        } catch (Exception e) {
            System.out.println("The datetime or timezone string provided was null or invalid. Cannot convert " +
                    "string to datetime. Exception is: " + e);
            throw e;
        }

        return utcDateTime;
    }

    String getUTCDateTimeString(String givenDateTime)
    {
        String myUTCDateTimeString;

        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneId.of("UTC"));
            myUTCDateTimeString = fmt.format(getUTCZonedDateTime(givenDateTime));
        } catch (Exception e) {
            System.out.println("The datetime or timezone string provided was null or invalid. Cannot convert " +
                    "string to datetime. Exception is: " + e);
            throw e;
        }

        return myUTCDateTimeString;
    }

    String getConvertedDateTimeString (String givenDateTime, String givenTimeZone, String considerDS) throws Exception {
        String myNewDateTimeString;

        try {

            if(considerDaylightSavings(considerDS).equals(Boolean.TRUE))
            {
                //Zoned date time considers daylight savings by default
                DateTimeFormatter dowFmt = DateTimeFormatter.ofPattern(DATE_FORMAT_DOW).withZone(ZoneId.of(ZoneId.SHORT_IDS.get(givenTimeZone)));
                ZonedDateTime zdt = getUTCZonedDateTime(givenDateTime);
                myNewDateTimeString = dowFmt.format(zdt);
            }
            else if(considerDaylightSavings(considerDS).equals(Boolean.FALSE))
            {
                //Local date time does not consider daylight savings time
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT);
                DateTimeFormatter dowFmt = DateTimeFormatter.ofPattern(DATE_FORMAT_DOW).withZone(ZoneId.of(ZoneId.SHORT_IDS.get(givenTimeZone)));

                LocalDateTime oldDateTime = LocalDateTime.parse(givenDateTime, fmt);
                ZoneId oldZone = ZoneId.of("UTC");
                ZoneId newZone = ZoneId.of(ZoneId.SHORT_IDS.get(givenTimeZone));
                LocalDateTime newDateTime = oldDateTime.atZone(oldZone).withZoneSameInstant(newZone).toLocalDateTime();
                myNewDateTimeString = dowFmt.format(newDateTime);
            }
            else
            {
                return null;
            }
        } catch (Exception e) {
            System.out.println("The datetime, timezone, or daylight savings string provided was null or invalid. Cannot convert " +
                    "string to datetime. Exception is: " + e);
            throw e;
        }

        return myNewDateTimeString;
    }

    Boolean considerDaylightSavings (String userInput) throws Exception
    {
        if(userInput.equalsIgnoreCase("yes"))
        {
            return true;
        }
        else if(userInput.equalsIgnoreCase("no"))
        {
            return false;
        }

        throw new Exception("Invalid entry for consideration of Daylight Savings Time. The value should be " +
                "\"YES\" or \"NO\"");
    }
}
