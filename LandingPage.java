package sample;

import javafx.collections.FXCollections;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LandingPage extends JFrame{
    private JTextField textField1;
    private JButton button1;
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JLabel lblThread;
    private JLabel lblPassword;
    private JLabel lblMain;

    public LandingPage(){
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Landing Page");
        setParameters();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinderScreen fs = new FinderScreen(LandingPage.this,textField1.getText(), comboBox1.getSelectedIndex() + 1);
                Details d = new Details(fs, textField1.getText(), comboBox1.getSelectedIndex() + 1);
                setVisible(false);
                textField1.setText("");
                comboBox1.setSelectedIndex(0);
            }
        });
    }

    private void setParameters(){
        setContentPane(mainPanel);
        textField1.setSize(100, 40);
        textField1.setLocation(300, 300);
        textField1.setVisible(true);
        button1.setSize(600, 40);
        button1.setVisible(true);
        button1.setText("Potvrď");
        for (int i = 1; i<17; i++){
            comboBox1.addItem(i);
        }
        comboBox1.setVisible(true);
        lblThread.setText("Počet vlákien");
        lblPassword.setText("Zadajte heslo: ");
        lblMain.setText("Uniknuté heslá");

    }

}
