# SFA Course Planner & GPA Calculator

A Java-based desktop application for managing courses and calculating cumulative GPA based on the Stephen F. Austin (SFA) grading scale.

## Features

- **Add Courses**: Input course name, grade, and credit hours
- **Calculate GPA**: Automatically computes cumulative GPA based on weighted grades
- **View Courses**: Display all saved courses in a table format
- **Clear Records**: Remove all course records from the database
- **Persistent Storage**: Saves data to a SQLite database for future sessions

## Requirements

- Java 11 or higher
- SQLite JDBC driver (sqlite-jdbc.jar)
- SLF4J logging libraries (slf4j-api.jar, slf4j-simple.jar)

## Installation & Setup

### 1. Download Required Libraries

The following JAR files are needed and should be placed in the `lib` folder:

- **sqlite-jdbc.jar** - SQLite JDBC driver
- **slf4j-api.jar** - SLF4J API
- **slf4j-simple.jar** - SLF4J Simple Logger

You can download them from Maven Central:
```powershell
cd lib
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.0.0/sqlite-jdbc-3.44.0.0.jar" -OutFile "sqlite-jdbc.jar"
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar" -OutFile "slf4j-api.jar"
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar" -OutFile "slf4j-simple.jar"
```

### 2. Create Required Directories

```powershell
mkdir bin
mkdir lib
```

## Compilation

From the project root directory, compile the Java files:

```bash
javac -cp "lib/sqlite-jdbc.jar;lib/slf4j-api.jar;lib/slf4j-simple.jar" -d bin *.java
```

## Running the Application

Execute the program with:

```bash
java -cp "bin;lib/sqlite-jdbc.jar;lib/slf4j-api.jar;lib/slf4j-simple.jar" Main
```

## Grading Scale

The application uses the following SFA grading scale:

| Grade | GPA Points |
|-------|-----------|
| A     | 4.0       |
| B     | 3.0       |
| C     | 2.0       |
| D     | 1.0       |
| F     | 0.0       |

## GPA Calculation

The cumulative GPA is calculated using the weighted average formula:

```
GPA = (Σ(Grade Points × Credit Hours)) / (Σ Credit Hours)
```

## File Structure

```
GPA Calculator/
├── Main.java                  # Entry point of the application
├── DatabaseManager.java       # Database operations and GPA calculations
├── GPACalculatorGUI.java      # User interface components
├── README.md                  # This file
├── bin/                       # Compiled class files
├── lib/                       # Required JAR libraries
└── sfa_gpa.db               # SQLite database (created at runtime)
```

## Usage

1. **Launch the Application**: Run the command above to start the GUI
2. **Add a Course**: 
   - Enter the course name
   - Select the grade (A, B, C, D, F)
   - Select the credit hours (1-4)
   - Click "Add Course"
3. **View GPA**: The cumulative GPA is displayed at the bottom of the window
4. **Clear Records**: Click "Clear All Records" to remove all courses and start fresh

## Database

The application automatically creates a SQLite database file (`sfa_gpa.db`) in the working directory. This database persists across sessions, so your course data will be retained even after closing the application.

## Troubleshooting

### "No suitable driver found for jdbc:sqlite:sfa_gpa.db"
- Ensure all JAR files are in the `lib` folder
- Verify the classpath includes all required libraries during compilation and execution

### "ClassNotFoundException: org.slf4j.LoggerFactory"
- Make sure both `slf4j-api.jar` and `slf4j-simple.jar` are in the `lib` folder
- Include them in the `-cp` (classpath) parameter

### Database file not found
- The database is created automatically in the working directory (where you run the `java` command)
- Make sure you have write permissions in that directory

## License

This project is provided as-is for educational purposes.
