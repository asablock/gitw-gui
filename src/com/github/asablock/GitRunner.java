package com.github.asablock;

import javax.swing.*;
import java.io.IOException;
import java.util.function.Supplier;

public class GitRunner {
    public static Process runGitCommand(Supplier<? extends JFrame> parentComp, String... args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s).append(" ");
        }
        String script = "\"" + UserConfig.getGitFile().getPath() + "\" " + sb;
        Util.getLogger().info("Generated bat script: " + script);
        try {
            return Runtime.getRuntime().exec(script);
        } catch (IOException e) {
            Util.getLogger().log(Util.ERROR, "Cannot execute git process", e);
            JOptionPane.showMessageDialog(parentComp.get(),
                    I18n.translate("error.git.ioexception", e.getMessage()));
            return null;
        }
    }
}
