import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GPACalculatorGUI extends JFrame {
    private JTextField txtCourseName;
    private JComboBox<String> comboGrade;
    private JComboBox<Integer> comboCredits;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JLabel lblGPA;

    public GPACalculatorGUI() {
        setTitle("SFA Course Planner & GPA Calculator");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- TOP PANEL: Input Fields ---
        JPanel inputPanel = new JPanel(new FlowLayout());
        
        inputPanel.add(new JLabel("Course Name:"));
        txtCourseName = new JTextField(10);
        inputPanel.add(txtCourseName);

        inputPanel.add(new JLabel("Grade:"));
        String[] grades = {"A", "B", "C", "D", "F"};
        comboGrade = new JComboBox<>(grades);
        inputPanel.add(comboGrade);

        inputPanel.add(new JLabel("Credits:"));
        Integer[] credits = {1, 2, 3, 4};
        comboCredits = new JComboBox<>(credits);
        inputPanel.add(comboCredits);

        JButton btnAdd = new JButton("Add Course");
        inputPanel.add(btnAdd);
        add(inputPanel, BorderLayout.NORTH);

        // --- CENTER PANEL: Table Display ---
        String[] columnNames = {"Course Name", "Grade", "Credit Hours"};
        tableModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM PANEL: GPA Status & Control ---
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        
        lblGPA = new JLabel("Cumulative GPA: 0.00", SwingConstants.CENTER);
        lblGPA.setFont(new Font("Arial", Font.BOLD, 18));
        bottomPanel.add(lblGPA);

        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton btnClear = new JButton("Clear All Records");
        controlPanel.add(btnClear);
        bottomPanel.add(controlPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        // --- BUTTON ACTIONS ---
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtCourseName.getText().trim();
                String grade = (String) comboGrade.getSelectedItem();
                int credits = (Integer) comboCredits.getSelectedItem();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a course name!");
                    return;
                }

                DatabaseManager.addCourse(name, grade, credits);
                txtCourseName.setText(""); // Reset input field
                refreshTableAndGPA();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseManager.clearDatabase();
                refreshTableAndGPA();
            }
        });

        // Load existing data on startup
        refreshTableAndGPA();
    }

    private void refreshTableAndGPA() {
        // Clear current UI table rows
        tableModel.setRowCount(0);

        List<String[]> courses = DatabaseManager.getAllCourses();
        double totalPoints = 0.0;
        int totalCredits = 0;

        for (String[] course : courses) {
            tableModel.addRow(course);
            
            String grade = course[1];
            int credits = Integer.parseInt(course[2]);
            
            totalPoints += DatabaseManager.convertGradeToPoints(grade) * credits;
            totalCredits += credits;
        }

        // Calculate and update GPA label
        if (totalCredits > 0) {
            double gpa = totalPoints / totalCredits;
            lblGPA.setText(String.format("Cumulative GPA: %.2f", gpa));
        } else {
            lblGPA.setText("Cumulative GPA: 0.00");
        }
    }
}