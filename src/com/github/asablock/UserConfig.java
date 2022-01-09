package com.github.asablock;

import java.io.*;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;

public class UserConfig {
    private static final Properties properties;
    private static final File CONFIG_FILE;

    public static File getGitFile() {
        return new File(properties.getProperty("gitFile"));
    }

    public static void setGitFile(File file) {
        properties.setProperty("gitFile", Objects.requireNonNull(file, "file").getAbsolutePath());
    }

    public static File getConfigFile() {
        return CONFIG_FILE;
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            properties.store(writer, "Config file");
        } catch (IOException e) {
            Util.getLogger().log(Level.SEVERE, "Cannot save config", e);
        }
    }

    public static void load() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            properties.load(reader);
        } catch (IOException e) {
            Util.getLogger().log(Level.SEVERE, "Cannot load config", e);
        }
    }

    static {
        properties = new Properties();
        properties.setProperty("gitFile", new File("git.exe").getAbsolutePath());
        CONFIG_FILE = new File(System.getProperty("user.dir"), "config.properties");
    }
}
