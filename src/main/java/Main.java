import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Please enter a datetime string in the format of \"MM-DD-YYYY HH24:MI:SS\", " +
                "where MM is the month, DD is the day, YYYY is the 4-digit year, HH24 is the hour in a 24-hour clock, " +
                "MI is the minutes, and SS is seconds: ");

        String givenDateTime = myObj.nextLine();

        System.out.println("Please enter a timezone to convert the given datetime to as a 3-character string. " +
                "EX) \"CST, UTC\": ");

        String givenTimeZone = myObj.nextLine();

        //For validation TODO: Remove these sysouts
        System.out.println("The datetime string is: " + givenDateTime);
        System.out.println("The timezone string is: " + givenTimeZone);
    }
}