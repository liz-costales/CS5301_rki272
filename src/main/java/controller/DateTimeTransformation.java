package controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class DateTimeTransformation {

    private static final String DATE_FORMAT = "MM-dd-yyyy HH:mm";
    private static final String DATE_FORMAT_DOW = "EEEE MM-dd-yyyy HH:mm";


    public Map<String, String> getTimes(String givenDateTime, String givenTimeZone, String considerDaylightSavings) throws Exception {

        String timeZone = ZoneId.SHORT_IDS.get(givenTimeZone);
        Map<String, String> myTimes = new HashMap<>();
        String utcDateTime = getUTCDateTimeString(givenDateTime);

        myTimes.put("given", getUTCDateTimeString(givenDateTime));
        myTimes.put("converted", getConvertedDateTimeString(utcDateTime, givenTimeZone, considerDaylightSavings));

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

    ZonedDateTime getUTCDateTime (String givenDateTime)
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

    String getUTCDateTimeString(String givenDateTime)
    {
        String myUTCDateTimeString;

        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneId.of("UTC"));
            myUTCDateTimeString = fmt.format(getUTCDateTime(givenDateTime));
        } catch (Exception e) {
            System.out.println("The datetime or timezone string provided was null or invalid. Cannot convert " +
                    "string to datetime. Exception is: " + e);
            throw e;
        }

        return myUTCDateTimeString;
    }

    String getConvertedDateTimeString (String givenDateTime, String givenTimeZone)
    {
        String myNewDateTimeString;

        try {
            DateTimeFormatter dowFmt = DateTimeFormatter.ofPattern(DATE_FORMAT_DOW).withZone(ZoneId.of(ZoneId.SHORT_IDS.get(givenTimeZone)));
            ZonedDateTime zdt = getUTCDateTime(givenDateTime);
            myNewDateTimeString = dowFmt.format(zdt);

        } catch (Exception e) {
            System.out.println("The datetime or timezone string provided was null or invalid. Cannot convert " +
                    "string to datetime. Exception is: " + e);
            throw e;
        }

        return myNewDateTimeString;
    }

    boolean considerDaylightSavings (String userInput)
    {
        if (userInput.equalsIgnoreCase("yes"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
