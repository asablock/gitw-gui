package com.github.asablock.screens;

import com.github.asablock.I18n;
import com.github.asablock.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainScreen extends Screen {
    @Override
    protected void init(JFrame frame, JPanel panel) {
        frame.setPreferredSize(new Dimension(450, 300));
        frame.setJMenuBar(Util.getDefaultMenuBar(frame));
        if (isFirstScreen()) {
            frame.setTitle(I18n.translate("text.title"));
            frame.setLocationRelativeTo(null);
        }
    }

    @Override
    protected void drawGui(JFrame frame, JPanel panel) {
        panel.setBorder(BorderFactory.createTitledBorder(I18n.translate("label.main.title")));
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));

        JButton createButton = new JButton(I18n.translate("button.main.create"));
        createButton.addMouseListener(Util.newMouseListener(event -> new CreateRepositoryScreen().show(this)));
        panel.add(createButton);

        JButton openButton = new JButton(I18n.translate("button.main.open"));
        openButton.addMouseListener(Util.newMouseListener(event -> {
            // TODO
        }));
        panel.add(openButton);
    }
}
