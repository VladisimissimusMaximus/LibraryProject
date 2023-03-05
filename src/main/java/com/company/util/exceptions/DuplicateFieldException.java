package com.company.util.exceptions;

import java.util.Arrays;

public class DuplicateFieldException extends DAOException {
    private final Key key;

    public DuplicateFieldException() {
        this.key = null;
    }

    public DuplicateFieldException(Throwable cause) {
        super("Duplication error!!!", cause);
        this.key = Key.parseKey(cause.getMessage());
    }

    public Key getKey() {
        return key;
    }

    public enum Key {
        OPERATION_UNIQUENESS_CONSTRAINT("book_operations.book_operations_uniqueness_constraint"),
        BOOK_UNIQUENESS_CONSTRAIN("book_uniqueness_constraint"),
        USER_UNIQUENESS_CONSTRAINT("users.email");

        private final String name;

        Key(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Key parseKey(String message) {
            String quote = "'";
            String keyString = message.substring(0, message.lastIndexOf(quote));
            keyString = keyString.substring(keyString.lastIndexOf(quote) + 1);
            final String finalKeyString = keyString;

            return Arrays.stream(Key.values())
                    .filter(key -> key.getName().equalsIgnoreCase(finalKeyString))
                    .findAny()
                    .orElse(null);
        }
    }
}
