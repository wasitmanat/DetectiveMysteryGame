package com.detective.util;

import com.detective.factory.EvidenceFactory;
import com.detective.factory.RoomFactory;
import com.detective.factory.SuspectFactory;
import com.detective.manager.GameManager;
import com.detective.model.Room;

import java.util.Random;

public class GameDataInitializer {
    private static final Random random = new Random();

    public static void initializeLevel(int level) {
        switch (level) {
            case 1: buildMansionCase(); break;
            case 2: buildOfficeCase(); break;
            case 3: buildTheaterCase(); break;
            default: buildMansionCase(); break;
        }
    }

    private static void buildMansionCase() {
        GameManager gm = GameManager.getInstance();
        gm.getRooms().clear();
        gm.getSuspects().clear();
        gm.addLog("Case File: The Aldridge Mansion Murder");

        Room library = RoomFactory.createRoom("Library", "Dusty shelves and old books.");
        Room kitchen = RoomFactory.createRoom("Kitchen", "Smells like burnt toast.");
        Room study = RoomFactory.createRoom("Study", "A locked drawer sits on the desk.");

        int guiltyIndex = random.nextInt(3);
        String[] names = {"Mr. Black", "Ms. White", "Dr. Green"};
        String culprit = names[guiltyIndex];

        library.addEvidence(EvidenceFactory.createEvidence("Bloody Knife",
                "Found hidden behind books. A scratched initial resembles " + culprit + "."));
        kitchen.addEvidence(EvidenceFactory.createEvidence("Threatening Letter",
                "Addressed to the victim. Sharp, rushed handwriting."));
        kitchen.addEvidence(EvidenceFactory.createEvidence("Torn Glove",
                "A single glove, size matches an adult hand."));
        study.addEvidence(EvidenceFactory.createEvidence("Mysterious Key",
                "Fits an unknown lock. A smudged receipt nearby reads \"" + culprit + "\"."));
        study.addEvidence(EvidenceFactory.createEvidence("Broken Watch",
                "Stopped at 11:47 PM - the estimated time of death."));

        gm.getRooms().add(library);
        gm.getRooms().add(kitchen);
        gm.getRooms().add(study);

        gm.getSuspects().add(SuspectFactory.createSuspect("Mr. Black", "Says he was at home.", guiltyIndex == 0));
        gm.getSuspects().add(SuspectFactory.createSuspect("Ms. White", "Says she was at work.", guiltyIndex == 1));
        gm.getSuspects().add(SuspectFactory.createSuspect("Dr. Green", "Says he was traveling.", guiltyIndex == 2));
    }

    private static void buildOfficeCase() {
        GameManager gm = GameManager.getInstance();
        gm.getRooms().clear();
        gm.getSuspects().clear();
        gm.addLog("Case File: The Corporate Blackout");

        Room boardroom = RoomFactory.createRoom("Boardroom", "The lights were off when the body was found.");
        Room serverRoom = RoomFactory.createRoom("Server Room", "Cold air hums between the racks.");
        Room lobby = RoomFactory.createRoom("Lobby", "Security cameras line the ceiling.");

        int guiltyIndex = random.nextInt(3);
        String[] names = {"Mr. Turner", "Ms. Reyes", "Mr. Osei"};
        String culprit = names[guiltyIndex];

        boardroom.addEvidence(EvidenceFactory.createEvidence("Spilled Coffee",
                "Still warm. A name tag nearby reads " + culprit + "."));
        serverRoom.addEvidence(EvidenceFactory.createEvidence("Access Log Printout",
                "Shows a badge swipe at 9:58 PM, right before the blackout."));
        serverRoom.addEvidence(EvidenceFactory.createEvidence("USB Drive",
                "Encrypted. Label partially readable: \"..." + culprit.substring(culprit.length() - 4) + "\"."));
        lobby.addEvidence(EvidenceFactory.createEvidence("Broken Badge Reader",
                "Someone forced it open to bypass the log."));
        lobby.addEvidence(EvidenceFactory.createEvidence("Security Footage Note",
                "Camera 3 was mysteriously offline for 10 minutes."));

        gm.getRooms().add(boardroom);
        gm.getRooms().add(serverRoom);
        gm.getRooms().add(lobby);

        gm.getSuspects().add(SuspectFactory.createSuspect("Mr. Turner", "Says he left before 9 PM.", guiltyIndex == 0));
        gm.getSuspects().add(SuspectFactory.createSuspect("Ms. Reyes", "Says she was on a call all night.", guiltyIndex == 1));
        gm.getSuspects().add(SuspectFactory.createSuspect("Mr. Osei", "Says he was fixing bugs in the server room.", guiltyIndex == 2));
    }

    private static void buildTheaterCase() {
        GameManager gm = GameManager.getInstance();
        gm.getRooms().clear();
        gm.getSuspects().clear();
        gm.addLog("Case File: Curtain Call Murder");

        Room stage = RoomFactory.createRoom("Stage", "The final scene was never finished.");
        Room dressingRoom = RoomFactory.createRoom("Dressing Room", "Mirrors surrounded by wilting flowers.");
        Room backstage = RoomFactory.createRoom("Backstage", "Ropes, pulleys, and shadows.");

        int guiltyIndex = random.nextInt(3);
        String[] names = {"Madame Rosa", "Victor Lang", "Elise Moreau"};
        String culprit = names[guiltyIndex];

        stage.addEvidence(EvidenceFactory.createEvidence("Torn Script Page",
                "A scene rewritten in a hurry, signed faintly by " + culprit + "."));
        dressingRoom.addEvidence(EvidenceFactory.createEvidence("Perfume Bottle",
                "A distinct scent - matches something " + culprit + " often wears."));
        dressingRoom.addEvidence(EvidenceFactory.createEvidence("Love Letter",
                "Unsigned, but the handwriting looks familiar."));
        backstage.addEvidence(EvidenceFactory.createEvidence("Frayed Rope",
                "Cut deliberately, not worn through."));
        backstage.addEvidence(EvidenceFactory.createEvidence("Muddy Footprint",
                "Leads offstage, toward the dressing rooms."));

        gm.getRooms().add(stage);
        gm.getRooms().add(dressingRoom);
        gm.getRooms().add(backstage);

        gm.getSuspects().add(SuspectFactory.createSuspect("Madame Rosa", "Says she was rehearsing alone.", guiltyIndex == 0));
        gm.getSuspects().add(SuspectFactory.createSuspect("Victor Lang", "Says he was adjusting the lights.", guiltyIndex == 1));
        gm.getSuspects().add(SuspectFactory.createSuspect("Elise Moreau", "Says she was signing autographs.", guiltyIndex == 2));
    }
}