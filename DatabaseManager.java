
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:sfa_gpa.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Standardized SFA grading scale conversion
    public static double convertGradeToPoints(String grade) {
        switch (grade.toUpperCase()) {
            case "A":
                return 4.0;
            case "B":
                return 3.0;
            case "C":
                return 2.0;
            case "D":
                return 1.0;
            case "F":
                return 0.0;
            default:
                return 0.0;
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {

            // Create the courses table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Courses ("
                    + "CourseId INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CourseName TEXT NOT NULL, "
                    + "Grade TEXT NOT NULL, "
                    + "CreditHours INTEGER NOT NULL"
                    + ");";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCourse(String name, String grade, int credits) {
        String insertSQL = "INSERT INTO Courses(CourseName, Grade, CreditHours) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setString(2, grade);
            pstmt.setInt(3, credits);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getAllCourses() {
        List<String[]> courses = new ArrayList<>();
        String querySQL = "SELECT CourseName, Grade, CreditHours FROM Courses";
        try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(querySQL)) {

            while (rs.next()) {
                courses.add(new String[]{
                    rs.getString("CourseName"),
                    rs.getString("Grade"),
                    String.valueOf(rs.getInt("CreditHours"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static void clearDatabase() {
        String deleteSQL = "DELETE FROM Courses";
        try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {
            stmt.execute(deleteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
