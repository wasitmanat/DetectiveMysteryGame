package com.detective.util;

import com.detective.factory.EvidenceFactory;
import com.detective.factory.RoomFactory;
import com.detective.factory.SuspectFactory;
import com.detective.manager.GameManager;
import com.detective.model.Evidence;
import com.detective.model.Room;
import com.detective.model.Suspect;

import java.util.Random;

public class GameDataInitializer {
    private static final Random random = new Random();

    public static void initialize() {
        GameManager gm = GameManager.getInstance();
        gm.getRooms().clear();
        gm.getSuspects().clear();

        Room library = RoomFactory.createRoom("Library", "Dusty shelves and old books.");
        Room kitchen = RoomFactory.createRoom("Kitchen", "Smells like burnt toast.");
        Room study = RoomFactory.createRoom("Study", "A locked drawer sits on the desk.");

        // Randomly decide who is guilty this game
        int guiltyIndex = random.nextInt(3);
        String[] names = {"Mr. Black", "Ms. White", "Dr. Green"};
        String culprit = names[guiltyIndex];

        Evidence knife = EvidenceFactory.createEvidence(
                "Bloody Knife",
                "Found hidden behind books. A faint initial is scratched on the handle... it looks like it could belong to " + culprit + "."
        );
        Evidence letter = EvidenceFactory.createEvidence(
                "Threatening Letter",
                "Addressed to the victim. The handwriting style is unusual — sharp and rushed."
        );
        Evidence key = EvidenceFactory.createEvidence(
                "Mysterious Key",
                "Fits an unknown lock. There's a receipt nearby with a smudged name that resembles \"" + culprit + "\"."
        );

        library.addEvidence(knife);
        kitchen.addEvidence(letter);
        study.addEvidence(key);

        gm.getRooms().add(library);
        gm.getRooms().add(kitchen);
        gm.getRooms().add(study);

        Suspect black = SuspectFactory.createSuspect("Mr. Black", "Says he was at home.", guiltyIndex == 0);
        Suspect white = SuspectFactory.createSuspect("Ms. White", "Says she was at work.", guiltyIndex == 1);
        Suspect green = SuspectFactory.createSuspect("Dr. Green", "Says he was traveling.", guiltyIndex == 2);

        gm.getSuspects().add(black);
        gm.getSuspects().add(white);
        gm.getSuspects().add(green);
    }
}