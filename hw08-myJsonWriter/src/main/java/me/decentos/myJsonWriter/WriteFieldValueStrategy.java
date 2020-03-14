package me.decentos.myJsonWriter;

import java.util.function.Function;
import java.util.function.Predicate;

public enum WriteFieldValueStrategy {
    SIMPLE(o -> o == null || o instanceof Number || o instanceof Boolean, String::valueOf),
    TEXT(o -> o instanceof String || o instanceof Character, o -> "\"" + o + "\""),
    DEFAULT {};

    WriteFieldValueStrategy() {}

    WriteFieldValueStrategy(Predicate<Object> predicate, Function<Object, String> func) {
        this(predicate);
        this.func = func;
    }

    WriteFieldValueStrategy(Predicate<Object> predicate) {
        this.predicate = predicate;
    }

    boolean test(Object o) {
        return predicate.test(o);
    }

    String write(Object o) {
        return func.apply(o);
    }

    protected Predicate<Object> predicate = o -> false;
    protected Function<Object, String> func = String::valueOf;
}
