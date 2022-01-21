package com.github.asablock.screens;

import com.github.asablock.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

public abstract class Screen implements WindowConstants, SwingConstants {
    protected static final Logger LOGGER = Util.getLogger();

    private Screen previous;
    private JFrame frame;
    private JPanel panel;
    private static Screen currentScreen;
    protected abstract void init(JFrame frame, JPanel panel);

    protected abstract void drawGui(JFrame frame, JPanel panel);

    public final void show(Screen previous) {
        this.previous = previous;
        this.frame = previous != null ? previous.frame : new JFrame();
        if (previous != null) {
            this.panel = previous.panel;
            this.panel.removeAll();
            frame.setJMenuBar(previous.frame.getJMenuBar());
        } else {
            this.frame.setLocationRelativeTo(null);
            this.panel = new JPanel();
            frame.getContentPane().add(panel);
        }
        currentScreen = this;
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose(frame, e);
            }
        });
        this.init(frame, panel);
        Util.icon(frame);
        this.drawGui(frame, panel);
        frame.pack();
        frame.setVisible(true);
        LOGGER.info(getClass().getSimpleName() + " loaded");
    }

    protected void onClose(JFrame frame, WindowEvent event) {
        frame.dispose();
        System.exit(0);
    }

    public final boolean isFirstScreen() {
        return previous == null;
    }

    protected final Screen getPrevious() {
        return previous;
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static Component getCurrentFrame() {
        return currentScreen.frame;
    }
}
