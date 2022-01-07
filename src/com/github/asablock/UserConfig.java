package com.github.asablock;

import java.io.File;
import java.util.Objects;

public class UserConfig {
    private static File gitFile;

    public static File getGitFile() {
        return gitFile;
    }

    public static void setGitFile(File file) {
        gitFile = Objects.requireNonNull(file, "file");
    }

    static {
        gitFile = new File(System.getProperty("user.dir"), "git.exe");
    }
}
