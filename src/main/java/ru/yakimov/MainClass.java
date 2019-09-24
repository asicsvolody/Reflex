package ru.yakimov;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public class MainClass {
    public static void main(String[] args) {

        try {
            new ReflexCreateAvroSchema().createSchema(
                    new File("/java/projects/Reflex/src/main/resources/UserWithData.class"),
                    "/java/projects/Reflex/src/main/resources/UserWithData.avsc");
        } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
