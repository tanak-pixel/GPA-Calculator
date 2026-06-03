
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Step 1: Setup database file and tables
        DatabaseManager.initializeDatabase();

        // Step 2: Launch UI on the Swing Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GPACalculatorGUI().setVisible(true);
            }
        });
    }
}
