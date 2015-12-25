package com.benoj.openpass1pass.content;

import com.benoj.openpass1pass.OpenPassword;
import com.benoj.openpass1pass.OpenPasswordType;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.File;
import java.lang.reflect.Type;

class PasswordDeserializer implements JsonDeserializer<OpenPassword> {
    private String basePath;

    public PasswordDeserializer(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public OpenPassword deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray node = (JsonArray) json;

        String uuid = node.get(0).getAsString();
        OpenPasswordType type = OpenPasswordType.from(node.get(1).getAsString());
        String name = node.get(2).getAsString();

        return new OpenPassword(name, type, new File(basePath + "/" + uuid + ".1password"));
    }
}
