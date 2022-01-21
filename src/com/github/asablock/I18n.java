package com.github.asablock;

import javax.swing.JComboBox;
import java.util.*;

public class I18n {
    private static ResourceBundle bundle;
    private static final Locale[] SUPPORTED_LOCALES = {
            new Locale("zh", "CN"),
            new Locale("en", "US")
    };

    public static String translate(@ResourceBundleKey String key) {
        return bundle.getString(key);
    }

    public static String translate(@ResourceBundleKey String key, Object... args) {
        return bundle.getString(key).formatted(args);
    }

    public static void setCurrentLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("gitw", locale);
    }

    public static Locale getCurrentLocale() {
        return bundle.getLocale();
    }

    public static ResourceBundle getResourceBundle() {
        return bundle;
    }

    public static JComboBox<Locale> toLocaleComboBox() {
        return new JComboBox<>(SUPPORTED_LOCALES);
    }

    public static Locale[] getSupportedLocales() {
        return SUPPORTED_LOCALES.clone();
    }

    static {
        setCurrentLocale(Locale.getDefault());
        //setCurrentLocale(new Locale("en", "US"));
    }
}
