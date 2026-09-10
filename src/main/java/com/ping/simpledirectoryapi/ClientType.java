package com.ping.simpledirectoryapi;

public enum ClientType {

    ADMIN,

    LIMITED;

    public static ClientType fromHeaderValue(String value) {
        if (value == null) {
            return null;
        }
        try {
            return valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
