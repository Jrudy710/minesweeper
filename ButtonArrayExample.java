import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonArrayExample {
    public static void main(String[] args) {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton) {
                    JButton button = (JButton) e.getSource();
                    String buttonText = button.getText();
                    String actionCommand = button.getActionCommand(); // Get the unique action command
                    // Handle the button click based on actionCommand
                    System.out.println("Button clicked: " + buttonText + " (Action Command: " + actionCommand + ")");
                }
            }
        };

        JPanel panel = new JPanel(new GridLayout(8, 10));
        JButton[] numArray = new JButton[80];
        int LCV = -1;
        for (int i = 0; i < numArray.length; i++) {
            if(i % 10 == 0){
               LCV++;
            }
            numArray[i] = new JButton();
            numArray[i].setActionCommand(makeCoordinate(LCV, i % 10)); // Set a unique action command
            numArray[i].addActionListener(listener);
            panel.add(numArray[i]);
        }

        JOptionPane.showMessageDialog(null, panel);
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
