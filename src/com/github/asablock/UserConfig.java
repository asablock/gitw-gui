package com.github.asablock;

import java.io.*;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;

public class UserConfig {
    private static final Properties PROPERTIES;
    private static final File CONFIG_FILE;

    // Keys
    private static final String GIT_FILE = "file.gitfile";

    public static File getGitFile() {
        return new File(PROPERTIES.getProperty(GIT_FILE));
    }

    public static void setGitFile(File file) {
        PROPERTIES.setProperty(GIT_FILE, Objects.requireNonNull(file, "file").getAbsolutePath());
    }

    public static File getConfigFile() {
        return CONFIG_FILE;
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            PROPERTIES.store(writer, "Config file");
        } catch (IOException e) {
            Util.getLogger().log(Level.SEVERE, "Cannot save config", e);
        }
    }

    public static void load() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            PROPERTIES.load(reader);
        } catch (IOException e) {
            Util.getLogger().log(Level.SEVERE, "Cannot load config", e);
        }
    }

    static {
        PROPERTIES = new Properties();
        PROPERTIES.setProperty("gitFile", new File("git.exe").getAbsolutePath());
        CONFIG_FILE = new File(System.getProperty("user.dir"), "config.properties");
    }
}
