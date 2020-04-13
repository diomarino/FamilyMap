package com.marinoo.familymap.serializer;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Decoder {

    public static <T> T deserialize(Reader reader, Class<T> returnType) {
        return (new Gson()).fromJson(reader, returnType);
    }

    public static  <T> T deserializeJsonFile(String json, Class<T> returnType) throws FileNotFoundException {
        return (new Gson()).fromJson(new FileReader(json), returnType);
    }

}
