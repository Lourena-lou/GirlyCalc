import java.awt.*;
import java.awt.event.*;
import java.beans.JavaBean;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDPersianPink = new Color(247, 127, 190);
    Color customBlack = new Color(28, 28, 28);
    Color customDarkPink = new Color(231, 84, 128);
    Color customPastelPink = new Color(255,209,220);

String[] girlMessages={
    "You are smart and capable💖",
    "You've got this, princesa🧘‍♀️",
    "Math is your playground💓",
    "Girls can code — and calculate! 👩🏽‍💻",
    "Believe in your beautiful brain 🧠✨",

};

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};
    
    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    JButton historyButton = new JButton("🎀");
    JLabel messageLabel= new JLabel();

    //A+B, A-B, A*B, A/B
    String A = "0";
    String operator = null;
    String B = null;

    Calculator() {
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customPastelPink);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);        // ...existing code...
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(messageLabel,BorderLayout.SOUTH);
        displayPanel.add(historyButton, BorderLayout.WEST); // Add history button to the left
        displayPanel.add(displayLabel, BorderLayout.CENTER); 
        frame.add(displayPanel, BorderLayout.NORTH);

messageLabel.setForeground(customBlack);
messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
messageLabel.setHorizontalAlignment(JLabel.CENTER);
messageLabel.setText("Welcome to the Girly Calculator 💗");


        buttonsPanel.setLayout(new GridLayout(5, 4));
        historyButton.setFont(new Font("Arial", Font.PLAIN, 40));
        historyButton.setBackground(customBlack);
        historyButton.setForeground(customBlack);
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);


        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(Color.white);
                button.setForeground(customBlack);
            }
            else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customDarkPink);
                button.setForeground(Color.white);
            }
            else {
                button.setBackground(customDPersianPink);
                button.setForeground(Color.white);
            } 
        
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();

                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if(buttonValue== "=") {
                            if(A != null){
                                B= displayLabel.getText();
                                double numA= Double.parseDouble(A);
                                double numB= Double.parseDouble(B);
                                TestConnection.saveCalculation(displayLabel.getText(), B);

                                
                                if(operator=="+") {
                                    displayLabel.setText(removeZeroDecimal(numA + numB));
                                
                            }else if(operator=="-") {
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                        }else if(operator=="×"){
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                }else if(operator=="÷") {
                                    displayLabel.setText(removeZeroDecimal(numA / numB));
                                    
                        } // Show a new inspirational message
        Random rand = new Random();
        int index = rand.nextInt(girlMessages.length);
        String inspirationalMessage = girlMessages[index];
        messageLabel.setText(inspirationalMessage);

        clearAll();
                            
                            }
                        }
                        else if("+-×÷".contains(buttonValue)) {
                            if(operator == null){
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                        // Handle right symbols
                    }
                    else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if(buttonValue== "AC") {
                            clearAll();
                            displayLabel.setText("0");



                        }
                        else if (buttonValue.equals("+/-")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                            // Implement +/- functionality here
                        }
                        else if (buttonValue.equals("%")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));// Implement % functionality here
                        }
                    }
                    else { // digits, ".", or "√"
                        if (buttonValue.equals(".")) {
                            if (!displayLabel.getText().contains(".")) {
                                displayLabel.setText(displayLabel.getText() + ".");
                            }
                        }
                        else if (buttonValue.equals("√")) {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            if (numDisplay < 0) {
                                displayLabel.setText("Error");
                            } else {
                                numDisplay = Math.sqrt(numDisplay);
                                displayLabel.setText(removeZeroDecimal(numDisplay));
                            }
                        }
                        else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
        }
        frame.setVisible(true); 
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }
    String removeZeroDecimal(double numDisplay) {
        
        if (numDisplay % 1 == 0) {
            return Integer.toString((int)numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
