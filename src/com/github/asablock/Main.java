package com.github.asablock;

import com.github.asablock.screens.TitleScreen;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize UI
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            setUIFont(new FontUIResource("SimHei", Font.PLAIN, 12));
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            Util.getLogger().log(Util.ERROR, "Cannot set look and feel", e);
        }
        if (isFirstStart()) {
            new IntroScreen().show(null);
        } else {
            new TitleScreen().show(null);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        }, "Shutdown"));
    }

    private static void setUIFont(FontUIResource font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }

    private static boolean isFirstStart() {
        File config = new File(System.getProperty("user.dir"), "conf.properties");
        return !config.exists();
    }
}
