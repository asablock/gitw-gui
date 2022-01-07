package com.github.asablock;

import javax.swing.JComboBox;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private static ResourceBundle bundle;
    private static final Locale[] SUPPORTED_LOCALES = {
            new Locale("zh", "CN"),
            new Locale("en", "US")
    };

    public static String translate(String key) {
        return bundle.getString(key);
    }

    public static String translate(String key, Object... args) {
        return bundle.getString(key).formatted(args);
    }

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("gitw", locale);
    }

    public static Locale getLocale() {
        return bundle.getLocale();
    }

    public static ResourceBundle getResourceBundle() {
        return bundle;
    }

    public static Locale[] getSupportedLocales() {
        return SUPPORTED_LOCALES.clone();
    }

    public static JComboBox<Locale> toLocaleComboBox() {
        return new JComboBox<>(SUPPORTED_LOCALES);
    }

    static {
        setLocale(Locale.getDefault());
    }
}
