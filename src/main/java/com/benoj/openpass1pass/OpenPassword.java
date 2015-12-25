package com.benoj.openpass1pass;

import java.io.File;

public class OpenPassword {

    private final String name;
    private final OpenPasswordType type;
    private final File file;

    public OpenPassword(String name, OpenPasswordType type, File file) {
        this.name = name;
        this.type = type;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public OpenPasswordType getType() {
        return type;
    }

    public File getFile() {
        return file;
    }
}
