package com.benoj.openpass1pass;

import java.util.HashMap;

public enum OpenPasswordType {
    ONE_PASSWORD_PASSWORD("passwords.Password");

    private String name;
    static HashMap<String, OpenPasswordType> lookup = new HashMap<>();

    static {
        for(OpenPasswordType type: OpenPasswordType.values()){
            lookup.put(type.name,type);
        }
    }

    OpenPasswordType(String name) {
        this.name = name;
    }

    public static OpenPasswordType from(String fromString){
        return lookup.get(fromString);
    }

}
