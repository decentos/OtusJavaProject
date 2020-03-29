package me.decentos.cache;

public enum CacheAction {
    ADD_TO_CACHE("add"),
    REMOVE_FROM_CACHE("remove");

    private final String value;

    CacheAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
