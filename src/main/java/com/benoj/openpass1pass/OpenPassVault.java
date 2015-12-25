package com.benoj.openpass1pass;

import com.benoj.openpass1pass.content.ContentFileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class OpenPassVault {
    private final ContentFileReader contentFileReader;

    public OpenPassVault(String path) throws FileNotFoundException {
        contentFileReader = new ContentFileReader(new File(path + "/data/default/contents.js"));
    }

    public List<OpenPassword> getPasswords() throws IOException {
       return contentFileReader.getPasswords();
    }
}

