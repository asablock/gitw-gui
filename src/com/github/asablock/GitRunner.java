package com.github.asablock;

import com.github.asablock.main.Main;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;
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
            return Objects.requireNonNull(Runtime.getRuntime().exec(script));
        } catch (IOException e) {
            Util.getLogger().log(Util.ERROR, "Cannot execute git process", e);
            JOptionPane.showMessageDialog(parentComp.get(),
                    I18n.translate("error.git.ioexception", e.getMessage()));
            if (JOptionPane.showConfirmDialog(parentComp.get(), I18n.translate("label.reload"),
                    I18n.translate("text.tips"), JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                Main.main(new String[0]);
            } else {
                System.exit(-1);
            }
            throw new AssertionError();
        }
    }

    public static void handleProcessToWriter(Process process, Writer writer, Charset charset) {
        try (
                InputStreamReader isr = new InputStreamReader(process.getInputStream());
                BufferedReader br = new BufferedReader(isr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace(writer instanceof PrintWriter w ? w : new PrintWriter(writer));
        }
    }

    public static void handleProcessToWriter(Process process, Writer writer) {
        try (
                InputStreamReader isr = new InputStreamReader(process.getInputStream());
                BufferedReader br = new BufferedReader(isr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace(writer instanceof PrintWriter w ? w :
                    new PrintWriter(writer));
        }
    }

    public static void handleProcessToOus(Process process, OutputStream os) {
        InputStream in = process.getInputStream();
        int c;
        try {
            while ((c = in.read()) != -1) {
                os.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace(os instanceof PrintStream s ? s :
                    new PrintStream(os));
        }
    }

    public static void initGitRepository(Supplier<? extends JFrame> parentComp, String loc) {
        handleProcessToOus(runGitCommand(parentComp, "init", loc), System.out);
    }
}
