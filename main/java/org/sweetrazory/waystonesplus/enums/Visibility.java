package org.sweetrazory.waystonesplus.enums;

public enum Visibility {
    GLOBAL("GLOBAL"),
    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE");

    private final String value;

    Visibility(String value) {
        this.value = value;
    }

    public static Visibility fromString(String visibility) {
        try {
            return Visibility.valueOf(visibility.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public String getValue() {
        return value;
    }

}
