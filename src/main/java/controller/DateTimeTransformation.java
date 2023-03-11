package controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeTransformation {

    public ZonedDateTime getConvertedDateTime(String givenDateTime, String givenTimeZone) throws Exception {

        ZonedDateTime convertedDateTime;
        String timeZone = ZoneId.SHORT_IDS.get(givenTimeZone);

        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss").withZone(ZoneId.of(timeZone));
            convertedDateTime = ZonedDateTime.parse(givenDateTime, fmt);

        } catch (Exception e) {
            System.out.println("The datetime or timezone string provided was invalid. Cannot convert " +
                    "string to datetime. Exception is: " + e);
            throw e;
        }

//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return convertedDateTime;
    }
    public boolean isDateTimeValid(String givenDateTime)
    {
        if(givenDateTime.length() == 19)
        {
            try {
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").parse(givenDateTime).toString();
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
        if(ZoneId.SHORT_IDS.containsKey(givenTimeZone))
        {
            return true;
        }
        return false;
    }
}
