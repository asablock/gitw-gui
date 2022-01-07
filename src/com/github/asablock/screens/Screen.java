package com.github.asablock.screens;

import com.github.asablock.Util;

import javax.swing.*;
import java.util.logging.Logger;

public abstract class Screen implements WindowConstants, SwingConstants {
    protected Screen previous;
    protected final Logger logger;
    private final JFrame frame;

    protected abstract void init(JFrame frame);

    protected abstract void drawGui(JFrame frame, JPanel panel);

    public Screen() {
        this.frame = new JFrame();
        this.logger = Util.getLogger();
    }

    public final void show(Screen previous) {
        this.previous = previous;
        if (previous != null) {
            frame.setLocation(previous.frame.getLocation());
            frame.setTitle(previous.frame.getTitle());
        }
        this.init(frame);
        logger.info(getClass().getSimpleName() + " initialized");
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        this.drawGui(frame, panel);
        frame.pack();
        frame.setVisible(true);
        Util.icon(frame);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        logger.info(getClass().getSimpleName() + " loaded");
    }
}
