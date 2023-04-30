import controller.DateTimeTransformation;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Do you want to consider daylight savings time in your date conversion? Please type \"yes\" or \"no\": ");
        String considerDaylightSavings = myObj.nextLine();

        System.out.println("Please enter a datetime string in the format of \"MM-DD-YYYY HH24:MI\", " +
                "where MM is the month, DD is the day, YYYY is the 4-digit year, HH24 is the hour in a 24-hour clock " +
                "and MI is the minutes: ");
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
                Map<String, String> myTimes = new HashMap<>();

                try
                {
                    myTimes = dtt.getTimes(givenDateTime, givenTimeZone, considerDaylightSavings);
                }
                catch (Exception e)
                {
                    System.out.println("Exception thrown: " + e);
                }
                System.out.println("The given datetime in UTC is: " + myTimes.get("given"));
                System.out.println("The converted datetime in " + givenTimeZone + " is: " + myTimes.get("converted"));
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