package com.github.asablock;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Util {
    private Util() {}

    private static final ImageIcon DEFAULT_ICON;
    private static final Logger LOGGER;

    public static final Level ERROR;

    public static void icon(JFrame frame) {
        if (DEFAULT_ICON != null) {
            frame.setIconImage(DEFAULT_ICON.getImage());
        } else {
            LOGGER.warning("Cannot set frame icon because default icon doesn't exist");
        }
    }

    public static Icon getDefaultIcon() {
        return DEFAULT_ICON;
    }

    public static void checkUnsaved(JFrame frame) {
        if (JOptionPane.showConfirmDialog(frame, I18n.translate("label.unsaved"),
                I18n.translate("text.warning"), JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
            LOGGER.warning("Unsaved changes");
            frame.dispose();
            System.exit(0);
        }
    }

    public static JMenuBar getDefaultMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        JMenu helpMenu = new JMenu(I18n.translate("menubar.help"));

        JMenuItem gitMenuItem = createMenuItem("menubar.help.git",
                e -> openBrowser("https://www.gitforwindows.org/"));

        menuBar.add(helpMenu);

        return menuBar;
    }

    public static JMenuItem createMenuItem(String translateKey, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(I18n.translate(translateKey));
        menuItem.addActionListener(listener);
        return menuItem;
    }

    public static void openBrowser(String url) {
        try {
            openBrowser(new URI(url));
        } catch (URISyntaxException e) {
            LOGGER.log(ERROR, "Bad URL syntax", e);
        }
    }

    public static void openBrowser(URI url) {
        try {
            Desktop.getDesktop().browse(url);
        } catch (IOException e) {
            LOGGER.log(ERROR, "Cannot start browser", e);
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    static {
        // Initialize logger
        LOGGER = Logger.getLogger("gitw-gui");
        ERROR = new CustomLevel("ERROR", 950);

        // Load frame icon
        URL imgUrl = Util.class.getResource("/com/github/asablock/icon.png");
        if (imgUrl != null) {
            DEFAULT_ICON = new ImageIcon(imgUrl);
        } else {
            LOGGER.warning("Cannot get frame icon! Don't modify JAR file.");
            DEFAULT_ICON = null;
        }
    }

    public static FileHolder toFileSelectorButton(JButton button, JFrame parentFrame, boolean onlyDirectory, FileFilter fileFilter, boolean enableAllFile) {
        FileHolder fileHolder = new FileHolder();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(onlyDirectory ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_AND_DIRECTORIES);
                if (!enableAllFile) {
                    jfc.removeChoosableFileFilter(jfc.getChoosableFileFilters()[0]);
                }
                jfc.setFileFilter(fileFilter);
                jfc.showDialog(parentFrame, I18n.translate("text.filechooser.open"));
                File file = jfc.getSelectedFile();
                if (file != null && (onlyDirectory ? file.isDirectory() : file.isFile())) {
                    if (onlyDirectory ? file.isDirectory() : file.isFile()) {
                        fileHolder.file = file;
                        Util.getLogger().info("Selected file: " + file.getPath());
                    } else {
                        Util.getLogger().warning("Not a valid file");
                    }
                } else {
                    Util.getLogger().warning("Not a valid file");
                }
            }
        });
        return fileHolder;
    }

    public static BufferedImage readImg(URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final class FileHolder {
        private File file;

        private FileHolder() {}

        public File getFile() {
            return this.file;
        }
    }

    private static class CustomLevel extends Level {
        @Serial
        private static final long serialVersionUID = -6078235798335419839L;

        public CustomLevel(String name, int value) {
            super(name, value);
        }

        public CustomLevel(String name, int value, String resourceBundleName) {
            super(name, value, resourceBundleName);
        }
    }
}
