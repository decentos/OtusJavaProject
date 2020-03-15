package me.decentos.jdbc;

import me.decentos.orm.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SqlQueryHelper {
    private final static Logger logger = LoggerFactory.getLogger(SqlQueryHelper.class);

    public static String createInsertStatementForClass(Class<?> clazz) {
        List<String> fieldsToInsert = getNonIdFieldNames(clazz);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into ");
        stringBuilder.append(clazz.getSimpleName().toLowerCase());
        stringBuilder.append("(");
        stringBuilder.append(String.join(",", fieldsToInsert));
        stringBuilder.append(")");
        stringBuilder.append(" values(");
        stringBuilder.append(String.join(",", Collections.nCopies(fieldsToInsert.size(), "?")));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String createUpdateStatementForClass(Class<?> clazz) {
        Field idField = null;
        try {
            idField = getIdField(clazz);
        } catch (NoSuchFieldException e) {
            logger.error("Id field not found for class {}", clazz);
            throw new RuntimeException(e);
        }
        List<String> fieldsToUpdate = getNonIdFieldNames(clazz);
        for (int i = 0; i < fieldsToUpdate.size(); i++) {
            String newVal = String.format("%s = ?", fieldsToUpdate.get(i));
            fieldsToUpdate.set(i, newVal);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("update ");
        stringBuilder.append(clazz.getSimpleName().toLowerCase());
        stringBuilder.append(" set ");
        stringBuilder.append(String.join(",", fieldsToUpdate));
        stringBuilder.append(" where ");
        stringBuilder.append(idField.getName());
        stringBuilder.append(" = ?");
        return stringBuilder.toString();
    }

    public static String createSelectStatementForClass(Class<?> clazz) {
        Field idField = null;
        try {
            idField = getIdField(clazz);
        } catch (NoSuchFieldException e) {
            logger.error("Id field not found for class {}", clazz);
            throw new RuntimeException(e);
        }
        List<String> fieldsToSelect = getNonIdFieldNames(clazz);
        fieldsToSelect.add(0, idField.getName());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ");
        stringBuilder.append(String.join(", ", fieldsToSelect));
        stringBuilder.append(" from ");
        stringBuilder.append(clazz.getSimpleName().toLowerCase());
        stringBuilder.append(" where ");
        stringBuilder.append(idField.getName());
        stringBuilder.append(" = ?");
        return stringBuilder.toString();
    }

    public static Field getIdField(Class<?> clazz) throws NoSuchFieldException {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow(NoSuchFieldException::new);
    }

    public static List<Field> getNonIdFields(Class<?> clazz) {
        Field idField = null;
        try {
            idField = getIdField(clazz);
        } catch (NoSuchFieldException e) {
            logger.error("Id field not found for class {}", clazz);
            throw new RuntimeException(e);
        }
        Field finalIdField = idField;
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !field.equals(finalIdField))
                .collect(Collectors.toList());
    }

    public static List<String> getNonIdFieldNames(Class<?> clazz) {
        return getNonIdFields(clazz).stream()
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    public static Object getIdFieldValue(Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field field = getIdField(obj.getClass());
        if (!Modifier.isPublic(field.getModifiers())) field.setAccessible(true);
        return field.get(obj);
    }
}
