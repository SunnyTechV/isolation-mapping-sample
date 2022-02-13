package com.techvg.covid.care.isolation.domain.enumeration;

/**
 * The IsolationStatus enumeration.
 */
public enum IsolationStatus {
    HOMEISOLATION("Home Isolation"),
    HOSPITALISED("Hospitalised");

    private final String value;

    IsolationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
