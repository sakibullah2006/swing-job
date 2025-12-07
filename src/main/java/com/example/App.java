package com.example;

import com.example.gui.MainFrame;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the Job Management Portal application.
 */
public class App {
    
    public static void main(String[] args) {
        // Launch the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
