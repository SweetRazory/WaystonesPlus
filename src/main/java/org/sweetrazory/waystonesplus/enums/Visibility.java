package org.sweetrazory.waystonesplus.enums;

import org.jetbrains.annotations.NotNull;

public enum Visibility {
    GLOBAL,
    PUBLIC,
    PRIVATE;

    public static Visibility fromString(@NotNull String visibility) {
        try {
            return Visibility.valueOf(visibility.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("Could not find visibility named %s", visibility));
        }
    }
}
