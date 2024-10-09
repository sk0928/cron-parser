# Cron Parser

This is a **Cron Parser** build on Java 21. It can parse standard cron expressions with 5 segments i.e. (minute, hour, day of month, month, and day of week) and also supports a command argument.
The use of the parser is to show the exact times the scheduled cron task will run. The output is in table format.

The parser reads cron expressions from command line and prints the parsed output or errors in the console.

Invalid cron expressions e.g. containing invalid characters or negative values, are handled and displays useful error messages.

### Example

Given the cron expression:

```*/15 0 1,15 * 1-5 /usr/bin/find```

The output will be:

```
Parsed cron expression: */15 0 1,15 * 1-5 /usr/bin/find
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```
## Features

- Supports all basic cron fields including ranges (`2-5`), lists (`1,2,3`), wildcards (`*`), and intervals with cadence (`*/10`).
- Edge Case Handling:
    - Invalid characters in cron fields.
    - Out-of-range values (e.g., minute > 59 or negatives).
    - Fail fast and error reporting for invalid cron expressions.

## Requirements

To run this project, you need to have **Java 21**
Note: This project uses gradle wrapper and invokes it with gradlew hence gradle installation is not required

- **Install Java 21:**
  - Mac
     ```bash
     brew install openjdk@21
  
  - Linux
    ```bash
    sudo apt install openjdk-21-jdk

- **Verify:**
   ```bash
   java -version

## How To Run
- **Run The Program:**
   ```bash
   ./gradlew run --args="*/15 0 1,15 * 1-5 /usr/bin/find"

- **Run The Tests:**
    ```bash
     ./gradlew test
  
Future Improvement:
- Added Logging
- Add more test for other parsers
- Modularize the Classes
