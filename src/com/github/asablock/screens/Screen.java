package com.github.asablock.screens;

import com.github.asablock.Util;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

public abstract class Screen implements WindowConstants, SwingConstants {
    protected Screen previous;
    protected static final Logger LOGGER = Util.getLogger();
    private final JFrame frame;

    protected abstract void init(JFrame frame);

    protected abstract void drawGui(JFrame frame, JPanel panel);

    protected void onClose(JFrame frame) {
        frame.dispose();
        System.exit(0);
    }

    public Screen() {
        this.frame = new JFrame();
    }

    public final void show(Screen previous) {
        this.previous = previous;
        if (previous != null) {
            frame.setLocation(previous.frame.getLocation());
            frame.setTitle(previous.frame.getTitle());
            previous.frame.dispose();
        }
        this.init(frame);
        LOGGER.info(getClass().getSimpleName() + " initialized");
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        this.drawGui(frame, panel);
        frame.pack();
        frame.setVisible(true);
        Util.icon(frame);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                onClose(frame);
            }
        });
        LOGGER.info(getClass().getSimpleName() + " loaded");
    }

    protected boolean isFirstScreen() {
        return previous == null;
    }
}
