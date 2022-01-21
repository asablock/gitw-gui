package com.github.asablock.screens;

import com.github.asablock.I18n;
import com.github.asablock.Util;

import javax.swing.*;
import java.awt.*;

public class ActionScreen extends Screen {
    @Override
    protected void init(JFrame frame, JPanel panel) {
        frame.setPreferredSize(new Dimension(450, 300));
    }

    @Override
    protected void drawGui(JFrame frame, JPanel panel) {
        panel.setBorder(BorderFactory.createTitledBorder(I18n.translate("label.main.title")));
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));

        
    }
}
