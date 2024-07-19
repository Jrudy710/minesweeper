import javax.swing.*;
import java.awt.*;

public class RadioButtonDemo extends JFrame {
    public RadioButtonDemo() {
        this.setLayout(null);

        JRadioButton radioButton1 = new JRadioButton("Undergraduate");
        JRadioButton radioButton2 = new JRadioButton("Graduate");
        JButton button = new JButton("Click");
        JLabel label = new JLabel("Selected option:");

        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);

        radioButton1.setBounds(50, 30, 120, 50);
        radioButton2.setBounds(250, 30, 80, 50);
        button.setBounds(150, 100, 100, 30);
        label.setBounds(50, 150, 200, 30);

        button.addActionListener(e -> {
            if (radioButton1.isSelected()) {
                label.setText("Selected option: Undergraduate");
            } else if (radioButton2.isSelected()) {
                label.setText("Selected option: Graduate");
            }
        });

        this.add(radioButton1);
        this.add(radioButton2);
        this.add(button);
        //this.add(label);

        this.setTitle("Radio Buttons");
        this.setSize(400, 200);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new RadioButtonDemo();
    }
}
