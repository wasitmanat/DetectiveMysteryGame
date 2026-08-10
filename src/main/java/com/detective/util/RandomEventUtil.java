package com.detective.util;

import java.util.List;
import java.util.Random;

public class RandomEventUtil {
    private static final Random random = new Random();

    private static final List<String> EVENTS = List.of(
            "You hear footsteps in the hallway... someone was just here.",
            "A cold draft passes through the room. Something feels off.",
            "You notice a suspicious shadow outside the window."
    );

    public static String maybeTriggerEvent() {
        if (random.nextInt(100) < 30) {
            int index = random.nextInt(EVENTS.size());
            return EVENTS.get(index);
        }
        return null;
    }
}