package com.github.asablock.screens;

import com.github.asablock.Util;

import javax.swing.*;
import java.awt.*;

public class CreateRepositoryScreen extends Screen {
    @Override
    protected void init(JFrame frame) {
        frame.setPreferredSize(new Dimension(50, 50));
    }

    @Override
    protected void drawGui(JFrame frame, JPanel panel) {

    }

    @Override
    protected void onClose(JFrame frame) {
        Util.checkUnsaved(frame);
    }
}
