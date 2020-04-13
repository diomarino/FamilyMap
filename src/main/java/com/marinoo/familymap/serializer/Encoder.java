package com.marinoo.familymap.serializer;

import com.google.gson.Gson;

public class Encoder {

    public static String serialize(Object objectToSerialize) {
        return (new Gson()).toJson(objectToSerialize);
    }
}
