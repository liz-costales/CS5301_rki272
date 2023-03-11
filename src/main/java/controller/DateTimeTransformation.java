package controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTransformation {

    public ZonedDateTime getConvertedDateTime(String givenDateTime, String givenTimeZone) throws Exception {

        ZonedDateTime convertedDateTime;
        String timeZone = ZoneId.SHORT_IDS.get(givenTimeZone);;

        try {
            DateTimeFormatter fmt;

            if (givenDateTime.length() == 19) {
                fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss").withZone(ZoneId.of(timeZone));
                convertedDateTime = ZonedDateTime.parse(givenDateTime, fmt);
            } else if (givenDateTime.length() == 16) {
                fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").withZone(ZoneId.of(timeZone));
                convertedDateTime = ZonedDateTime.parse(givenDateTime, fmt);
            } else if (givenDateTime.length() == 10) {
                fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy").withZone(ZoneId.of(timeZone));
                convertedDateTime = ZonedDateTime.parse(givenDateTime, fmt);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("The datetime string provided was invalid. Cannot convert " +
                    "string to datetime. Exception is: " + e);
            throw e;
        }

//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return convertedDateTime;
    }
}
