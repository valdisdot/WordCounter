package com.valdisdot.customersupport;

import com.valdisdot.customersupport.gui.CounterPanel;
import com.valdisdot.customersupport.util.Resources;

import javax.swing.*;

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.add(new CounterPanel(() -> {
                f.revalidate();
                f.pack();
            }));
            f.pack();
            f.setIconImage(Resources.getFrameIco());
            f.setTitle(Resources.getFrameTitle());
            f.setResizable(false);
            f.setAlwaysOnTop(true);
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
