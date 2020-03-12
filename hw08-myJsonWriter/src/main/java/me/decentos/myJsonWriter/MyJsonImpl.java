package me.decentos.myJsonWriter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyJsonImpl implements MyJson {

    @Override
    public String toJson(Object object) {
        if (object == null) {
            return null;
        }
        Class<?> clazz = object.getClass();

        if (clazz.isArray()) {
            return getJsonFromArray(object);
        }
        else if (object instanceof Collection) {
            return getJsonFromCollection((Collection<?>) object);
        }
        else {
            return getJsonFromObject(object, clazz);
        }
    }

    private String getJsonFromObject(Object object, Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>();
        sb.append('{');
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            final String fieldName = String.format("\"%s\":", field.getName());

            result.add(fieldName + write(value));
        }
        sb.append(String.join(",", result));
        sb.append('}');
        return sb.toString();
    }

    private String write(Object value) {
        for (WriteFieldValueStrategy writeStrategy : WriteFieldValueStrategy.values()) {
            if (writeStrategy.test(value)) {
                return writeStrategy.write(value);
            }
        }

        if (value.getClass().isArray()) {
            return getJsonFromArray(value);
        }

        if (value instanceof Collection) {
            return getJsonFromCollection((Collection<?>) value);
        }

        return toJson(value);
    }

    private String getJsonFromCollection(Collection<?> value) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Object aVal : value) {
            if (sb.length() != 1) {
                sb.append(',');
            }
            sb.append(write(aVal));
        }
        sb.append(']');
        return sb.toString();
    }

    private String getJsonFromArray(Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < Array.getLength(value); i++) {
            Object aVal = Array.get(value, i);

            if (sb.length() != 1) {
                sb.append(',');
            }

            sb.append(write(aVal));
        }
        sb.append(']');
        return sb.toString();
    }
}
