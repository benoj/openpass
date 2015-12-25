package com.benoj.openpass1pass.content;

import com.benoj.openpass1pass.OpenPassword;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ContentFileReader {
    private final Gson gson;
    private File contentFilePath;

    public ContentFileReader(File path) throws FileNotFoundException {
        if(!path.exists()) {
            throw new FileNotFoundException();
        }
        contentFilePath = path;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(OpenPassword.class, new PasswordDeserializer(contentFilePath.getParent()))
                .create();
    }

    public List<OpenPassword> getPasswords() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(contentFilePath))){
            OpenPassword[] passwords = gson.fromJson(bufferedReader, OpenPassword[].class);
            return Arrays.asList(passwords);
        }
    }

}
