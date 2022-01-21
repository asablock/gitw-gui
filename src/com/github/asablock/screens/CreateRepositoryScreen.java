package com.github.asablock.screens;

import com.github.asablock.GitRunner;
import com.github.asablock.I18n;
import com.github.asablock.LogWriter;
import com.github.asablock.Util;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serial;

public class CreateRepositoryScreen extends Screen {
    @Override
    protected void init(JFrame frame, JPanel panel) {
        frame.setPreferredSize(new Dimension(400, 200));
    }

    @Override
    protected void drawGui(JFrame frame, JPanel panel) {
        panel.setBorder(new TitledBorder(I18n.translate("label.create.title")) {
            @Serial
            private static final long serialVersionUID = 5532076838946262392L;

            @Override
            public Insets getBorderInsets(Component c, Insets insets) {
                super.getBorderInsets(c, insets);
                insets.top += 5;
                insets.bottom += 5;
                insets.left += 5;
                insets.right += 5;
                return insets;
            }
        });
        panel.setLayout(new GridLayout(2, 2, 5, 5));

        JButton selectPathButton = new JButton();
        Util.FileHolder holder = Util.toFileChooserButton(selectPathButton, frame, true,
                true, null);
        Util.addFormItem(panel, "label.create.path", selectPathButton);

        JButton cancelButton = new JButton(I18n.translate("button.cancel"));
        cancelButton.addMouseListener(Util.newMouseListener(event ->
                new MainScreen().show(CreateRepositoryScreen.this)));
        panel.add(cancelButton);

        JButton nextStepButton = new JButton(I18n.translate("button.nextstep"));
        nextStepButton.addMouseListener(Util.newMouseListener(event -> {
            initGitRepo(holder.getFile(), frame);
            new ActionScreen().show(CreateRepositoryScreen.this);
        }));
        panel.add(nextStepButton);
    }

    private static void initGitRepo(File dir, JFrame frame) {
        if (!new File(dir, ".git").exists() || JOptionPane.showConfirmDialog(frame,
                I18n.translate("label.create.alreadyexist"),
                I18n.translate("text.warning"), JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
            GitRunner.handleProcessToWriter(GitRunner.runGitCommand(() -> frame, "init", dir.getAbsolutePath()),
                    new LogWriter());
        }
    }

    @Override
    protected void onClose(JFrame frame, WindowEvent event) {
        Util.checkUnsaved(frame);
    }
}
