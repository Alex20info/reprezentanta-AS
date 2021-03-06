package com.example.geo2021.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Json {

    public static <T> List getList(InputStream stream, String propertyName, Class<T> tClass){
        try{
            return getList(new JsonParser().parse(new JsonReader(new InputStreamReader(stream))), propertyName, tClass);
        } finally {
            if(stream !=null){
                try{
                    stream.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static <T> List<T> getList(JsonElement element, String propertyName, Class<T> tClass){
        if(!element.isJsonNull()){
            if(propertyName!=null){
                return new Gson().fromJson(element.getAsJsonObject().get(propertyName),new ArrayListType(tClass) );
            }
            return new Gson().fromJson(element, new ArrayListType(tClass));
        }
        return null;
    }

    public static <T> T getItem(InputStream stream, String propertyName, Class<T> tClass){
        try{
            return getItem(new JsonParser().parse(new JsonReader(new InputStreamReader(stream))), propertyName, tClass);
        } finally {
            if(stream !=null){
                try{
                    stream.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static <T> T getItem(JsonElement element, String propertyName, Class<T> tClass){
        if(!element.isJsonNull()){
            if(propertyName!=null){
                return new Gson().fromJson(element.getAsJsonObject().get(propertyName), tClass);
            }
            return new Gson().fromJson(element.getAsJsonObject(), tClass);
        }
        return null;
    }

    static class ArrayListType implements ParameterizedType{

        Type type;
        public ArrayListType(Type type) {this.type=type; }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
