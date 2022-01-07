package com.github.asablock;

import com.github.asablock.screens.Screen;
import com.github.asablock.screens.TitleScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Objects;

public class IntroScreen extends Screen {
    @Override
    protected void init(JFrame frame) {
        frame.setTitle(I18n.translate("text.title"));
        frame.setPreferredSize(new Dimension(350, 200));
    }

    @Override
    protected void drawGui(JFrame frame, JPanel panel) {
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(IntroScreen.class
                .getResource("/com/github/asablock/icon.png")));
        Image image = icon.getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT);
        JLabel iconLabel = new JLabel(new ImageIcon(image));
        panel.add(iconLabel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        panel.add(rightPanel, BorderLayout.EAST);
        rightPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(I18n.translate("label.intro.title"), CENTER);
        titleLabel.setFont(new Font("SimHei", Font.BOLD, 17));
        rightPanel.add(titleLabel, BorderLayout.NORTH);

        JButton selectFileButton = new JButton(I18n.translate("button.filechooser"));
        Util.FileHolder holder = Util.toFileSelectorButton(selectFileButton, frame, false);
        rightPanel.add(selectFileButton, BorderLayout.CENTER);

        JButton okButton = new JButton(I18n.translate("button.ok"));
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UserConfig.setGitFile(holder.getFile());
                new TitleScreen().show(IntroScreen.this);
                frame.dispose();
            }
        });
        rightPanel.add(okButton, BorderLayout.SOUTH);
    }
}
