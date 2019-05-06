package com.t3h.gui;

import javax.swing.*;

public class MyFrame extends JFrame{
    public static final int W_Frame = 750;
    public static final int H_Frame = 540;
    public MyPanel panel;

    public MyFrame() {
        setTitle("Mario");
        setSize(W_Frame, H_Frame);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panel = new MyPanel());
    }
}
