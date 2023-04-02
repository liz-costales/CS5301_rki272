# Project: CS5301_rki272_ElizabethCostales
### CS5301 Project - Spring 2023

---
Topic: Date Time Transformation
--- ---

Code written in and compiled to JavaSE 11

Acceptable Timezones are found:
https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3

---
How to run: 
```./gradlew clean build```

```./gradlew -PmainClass=Main run```

Then input in your date in the correct format of "MM-DD-YYYY HH24:MI", press enter.

Then input in the timezone in 3-digit format (i.e. CST/MST/etc, and hit enter.

---
### Example flow with the system:
```
Please enter a datetime string in the format of "MM-DD-YYYY HH24:MI", where MM is the month, DD is the day, YYYY is the 4-digit year, HH24 is the hour in a 24-hour clock and MI is the minutes: 
04-05-1987 16:45
The given datetime string is: 04-05-1987 16:45 with timezone UTC.
```
```
Please enter a timezone to convert the given datetime to as a 3-character string. EX) "CST, UTC": 
EST
The given timezone to convert to is: EST
```
```
The given datetime in UTC is: 04-02-2023 16:45
The converted datetime in EST is: Sunday 04-02-2023 11:45
```
Invalid entries will result in an error message being returned.
