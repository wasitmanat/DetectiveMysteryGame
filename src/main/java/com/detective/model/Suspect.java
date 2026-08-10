package com.detective.model;

public class Suspect {
    private String name;
    private String alibi;
    private boolean guilty;

    public Suspect(String name, String alibi, boolean guilty) {
        this.name = name;
        this.alibi = alibi;
        this.guilty = guilty;
    }

    public String getName() {
        return name;
    }

    public String getAlibi() {
        return alibi;
    }

    public boolean isGuilty() {
        return guilty;
    }
}