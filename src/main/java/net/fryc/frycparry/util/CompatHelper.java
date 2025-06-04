package net.fryc.frycparry.util;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class CompatHelper {

    public static @Nullable Class<?> getClassForName(String name){
        try{
            Class<?> clazz = Class.forName(name);
            return clazz;
        } catch (Exception e) {
            return null;
        }
    }

    public static @Nullable Method getMethodForName(Class<?> clazz, String name, Class<?>... parameters){
        try{
            Method method = clazz.getMethod(name, parameters);
            return method;
        } catch (Exception e) {
            return null;
        }
    }
}
