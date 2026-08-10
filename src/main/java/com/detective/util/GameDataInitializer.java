package com.detective.util;

import com.detective.factory.EvidenceFactory;
import com.detective.factory.RoomFactory;
import com.detective.factory.SuspectFactory;
import com.detective.manager.GameManager;
import com.detective.model.Evidence;
import com.detective.model.Room;

public class GameDataInitializer {
    public static void initialize() {
        GameManager gm = GameManager.getInstance();
        gm.getRooms().clear();
        gm.getSuspects().clear();

        Room library = RoomFactory.createRoom("Library", "Dusty shelves and old books.");
        Room kitchen = RoomFactory.createRoom("Kitchen", "Smells like burnt toast.");
        Room study = RoomFactory.createRoom("Study", "A locked drawer sits on the desk.");

        Evidence knife = EvidenceFactory.createEvidence("Bloody Knife", "Found hidden behind books.");
        Evidence letter = EvidenceFactory.createEvidence("Threatening Letter", "Addressed to the victim.");
        Evidence key = EvidenceFactory.createEvidence("Mysterious Key", "Fits an unknown lock.");

        library.addEvidence(knife);
        kitchen.addEvidence(letter);
        study.addEvidence(key);

        gm.getRooms().add(library);
        gm.getRooms().add(kitchen);
        gm.getRooms().add(study);

        gm.getSuspects().add(SuspectFactory.createSuspect("Mr. Black", "Says he was at home.", false));
        gm.getSuspects().add(SuspectFactory.createSuspect("Ms. White", "Says she was at work.", true));
        gm.getSuspects().add(SuspectFactory.createSuspect("Dr. Green", "Says he was traveling.", false));
    }
}