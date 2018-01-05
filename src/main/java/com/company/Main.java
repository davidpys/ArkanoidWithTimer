package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        JFrame mainFrame = new JFrame();
        Gameplay gameplay = new Gameplay();

        mainFrame.setBounds(10, 10, 700, 600);
        mainFrame.setTitle("My arkanoid");
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);


        mainFrame.add(gameplay);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
