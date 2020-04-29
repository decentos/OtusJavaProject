package me.decentos.messagesystem;

public enum MessageType {
    NEW_USER_DATA("NewUserData"),
    USER_DATA("UserData"),
    USER_DATA_COLLECTION("UserDataCollection");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}