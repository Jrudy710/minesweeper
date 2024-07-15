import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonArrayExample2 {
    public static void main(String[] args) {
        int rows = 3;
        int cols = 3;

        JButton[][] buttonArray = new JButton[rows][cols];
        JPanel panel = new JPanel(new GridLayout(rows, cols));

        MouseAdapter mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JButton button = (JButton) e.getSource();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    System.out.println("Left button clicked on " + button.getActionCommand());
                    // Handle left-click behavior
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    System.out.println("Right button clicked on " + button.getActionCommand());
                    // Handle right-click behavior
                }
            }
        };

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttonArray[i][j] = new JButton("Button " + (i * cols + j));
                buttonArray[i][j].setActionCommand(makeCoordinate(i, j));
                buttonArray[i][j].addMouseListener(mouseListener);
                panel.add(buttonArray[i][j]);
            }
        }

        JFrame frame = new JFrame("Button Array Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static String makeCoordinate(int row, int column){                                                          // Method Block
      
                                                                                                               // VARIABLE DEFINITIONS
      String coordinate = "";                                                                                  // Defines coordinate
      //System.out.println("Values (" + row + ", " + column + ")");
      char rowVal = ' ';                                                                                       // Defines rowVal
      
      rowVal = (char)(row + 97);                                                                               // Sets the value of rowVal
      
      coordinate = rowVal + "" + column;                                                                       // Sets the value of coordinate
      
      return coordinate;                                                                                       // Returns the converted coordinate to the user 
   }
}
