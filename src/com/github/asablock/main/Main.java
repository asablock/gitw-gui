package com.github.asablock.main;

import com.github.asablock.Constants;
import com.github.asablock.UserConfig;
import com.github.asablock.Util;
import com.github.asablock.screens.IntroScreen;
import com.github.asablock.screens.MainScreen;

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
            setUIFont(new FontUIResource(Constants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            Util.getLogger().log(Util.ERROR, "Cannot set look and feel", e);
        }
        if (isFirstStart()) {
            new IntroScreen().show(null);
        } else {
            UserConfig.load();
            new MainScreen().show(null);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            UserConfig.save();
            Util.getLogger().info("Stopping!");
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
        File config = new File(System.getProperty("user.dir"), "config.properties");
        return !config.exists();
    }
}
