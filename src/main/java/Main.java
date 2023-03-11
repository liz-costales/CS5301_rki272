import controller.DateTimeTransformation;

import java.time.ZonedDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Please enter a datetime string in the format of \"MM-DD-YYYY HH24:MI:SS\", " +
                "where MM is the month, DD is the day, YYYY is the 4-digit year, HH24 is the hour in a 24-hour clock, " +
                "MI is the minutes, and SS is seconds: ");
        String givenDateTime = myObj.nextLine();

        DateTimeTransformation dtt = new DateTimeTransformation();

        if(dtt.isDateTimeValid(givenDateTime))
        {
            System.out.println("The given datetime string is: " + givenDateTime + " with timezone UTC.");

            System.out.println("Please enter a timezone to convert the given datetime to as a 3-character string. " +
                    "EX) \"CST, UTC\": ");
            String givenTimeZone = myObj.nextLine();

            if(dtt.isTimeZoneValid(givenTimeZone))
            {
                System.out.println("The given timezone to convert to is: " + givenTimeZone);
                ZonedDateTime convertedDateTime = null;

                try
                {
                    convertedDateTime = dtt.getConvertedDateTime(givenDateTime, givenTimeZone);
                }
                catch (Exception e)
                {
                    System.out.println("Exception thrown: " + e);
                }
                System.out.println("The converted datetime is: " + convertedDateTime);
            }
            else {
                System.out.println("**********************************************");
                System.out.println("The given timezone string is invalid. Exiting...");
                System.out.println("**********************************************");

            }
        }
        else {
            System.out.println("**********************************************");
            System.out.println("The given datetime string is invalid. Exiting...");
            System.out.println("**********************************************");
        }
    }
}