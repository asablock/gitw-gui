package com.github.asablock;

import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWriter extends Writer {
    private final Logger logger;
    private Level level;

    public LogWriter(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    public LogWriter(Logger logger) {
        this.logger = logger;
        this.level = Level.INFO;
    }

    public LogWriter(Level level) {
        this.logger = Util.getLogger();
        this.level = level;
    }

    public LogWriter() {
        this(Util.getLogger());
    }

    @Override
    public void write(char[] cbuf, int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        char[] content = new char[len - off];
        System.arraycopy(cbuf, off, content, 0, len);
        logger.log(level, String.valueOf(content));
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    public Logger getLogger() {
        return logger;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
