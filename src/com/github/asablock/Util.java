package com.github.asablock;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serial;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Util {
    private Util() {}

    private static final ImageIcon defaultIcon;
    private static final Logger logger;

    public static final Level ERROR;

    public static void icon(JFrame frame) {
        if (defaultIcon != null) {
            frame.setIconImage(defaultIcon.getImage());
        } else {
            logger.warning("Cannot set frame icon because default icon doesn't exist");
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    static {
        // Initialize logger
        logger = Logger.getLogger("gitw-gui");
        ERROR = new CustomLevel("ERROR", 950);

        // Load frame icon
        URL imgUrl = Util.class.getResource("/com/github/asablock/icon.png");
        if (imgUrl != null) {
            defaultIcon = new ImageIcon(imgUrl);
        } else {
            logger.warning("Cannot get frame icon! Don't modify JAR file.");
            defaultIcon = null;
        }
    }

    public static FileHolder toFileSelectorButton(JButton button, JFrame parentFrame, boolean onlyDirectory) {
        FileHolder fileHolder = new FileHolder();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(onlyDirectory ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_AND_DIRECTORIES);
                jfc.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return "git.exe".equals(f.getName());
                    }

                    @Override
                    public String getDescription() {
                        return I18n.translate("text.filechooser.gitexe");
                    }
                });
                jfc.showDialog(parentFrame, I18n.translate("text.filechooser.open"));
                File file = jfc.getSelectedFile();
                if (file != null && (onlyDirectory ? file.isDirectory() : file.isFile())) {
                    if (onlyDirectory ? file.isDirectory() : file.isFile()) {
                        fileHolder.file = file;
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
