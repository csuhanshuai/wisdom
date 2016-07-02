package com.cnst.wisdom.utills;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author taoyuan
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class GeoUtil {
    private final Gson gson;

    public GeoUtil() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public <T> String serializer(T result) {
        return gson.toJson(result);
    }

    public <T extends Object> T deserializer(String result, Class<T> c) {
        return gson.fromJson(result, c);
    }

    public <T extends Object> T deserializer(String result, Type t) {
        return gson.fromJson(result, t);
    }
}
