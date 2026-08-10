package com.detective.factory;

import com.detective.model.Suspect;

public class SuspectFactory {
    public static Suspect createSuspect(String name, String alibi, boolean guilty) {
        return new Suspect(name, alibi, guilty);
    }
}