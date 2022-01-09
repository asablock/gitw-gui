package com.github.asablock.screens;

import com.github.asablock.Constants;
import com.github.asablock.I18n;
import com.github.asablock.UserConfig;
import com.github.asablock.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class IntroScreen extends Screen {
    @Override
    protected void init(JFrame frame) {
        frame.setTitle(I18n.translate("text.title"));
        frame.setPreferredSize(new Dimension(350, 200));
        frame.setLocationRelativeTo(null);
    }

    @Override
    protected void drawGui(JFrame frame, JPanel panel) {
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JMenuBar menuBar = new JMenuBar();

        JMenu helpMenu = new JMenu(I18n.translate("menubar.help"));
        JMenuItem installGitMenu = new JMenuItem(I18n.translate("menubar.help.installgit"));
        installGitMenu.addActionListener(e -> {
            Util.openBrowser("https://www.gitforwindows.org/");
            JOptionPane optionPane = new JOptionPane(I18n.translate("label.intro.installhelp"),
                    JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionPane.createDialog(I18n.translate("text.tips"));
            dialog.setAlwaysOnTop(true);
            dialog.setModal(true);
            dialog.setIconImage(Util.readImg(getClass().getResource("/com/github/asablock/icon.png")));
            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });
        helpMenu.add(installGitMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        JLabel titleLabel = new JLabel(I18n.translate("label.intro.title"), CENTER);
        titleLabel.setFont(new Font(Constants.DEFAULT_FONT_NAME, Font.BOLD, 13));
        titleLabel.setPreferredSize(new Dimension(100, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        JButton selectFileButton = new JButton(I18n.translate("button.filechooser"));
        selectFileButton.setPreferredSize(new Dimension(80, 30));
        Util.FileHolder holder = Util.toFileSelectorButton(selectFileButton, frame, false, new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || "git.exe".equals(f.getName());
            }

            @Override
            public String getDescription() {
                return I18n.translate("text.filechooser.gitexe");
            }
        }, false);
        panel.add(selectFileButton, BorderLayout.CENTER);

        JButton okButton = new JButton(I18n.translate("button.ok"));
        okButton.setPreferredSize(new Dimension(0, 30));
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (holder.getFile() != null) {
                    UserConfig.setGitFile(holder.getFile());
                    new MainScreen().show(IntroScreen.this);
                } else {
                    JOptionPane.showMessageDialog(frame, I18n.translate("label.intro.filenotselected"),
                            I18n.translate("text.warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panel.add(okButton, BorderLayout.SOUTH);
    }
}
