package ru.yakimov;

import org.apache.avro.Schema;
import ru.yakimov.SparkAPI.WorkWhithFiles.SchemaGiving;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ReflexCreateAvroSchema {
    public void createSchema(File classFile, String pathTo)
            throws MalformedURLException, ClassNotFoundException, NoSuchMethodException
            , InvocationTargetException, IllegalAccessException, InstantiationException {
        Class cl = getClass(classFile);
        if(SchemaGiving.class.isAssignableFrom(cl)) {
            Constructor constructor = cl.getConstructor();
            Object obj = constructor.newInstance();

            Method createAvroSchema = cl.getDeclaredMethod("createAvroSchema");
            createAvroSchema.setAccessible(true);
            writeSchemaAvro(pathTo, (Schema) createAvroSchema.invoke(obj));
        }else{
            System.out.println("This class not implements SchemaGiving ");
        }
    }



    private void writeSchemaAvro(String path, Schema schema){
        try(FileWriter fileWriter = new FileWriter(path)){
            fileWriter.write(schema.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Class<?> getClass(File classFile) throws ClassNotFoundException, MalformedURLException {
        return URLClassLoader.newInstance(new URL[]{classFile.getParentFile().toURL()})
                .loadClass(getNameWithOutFormat(classFile.getName()));
    }

    private String getNameWithOutFormat(String fileNameWithFormat){
        char[] charArr = fileNameWithFormat.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char symbol: charArr) {
            if(symbol=='.'){
                break;
            }
            sb.append(symbol);
        }
        return sb.toString();
    }

}
