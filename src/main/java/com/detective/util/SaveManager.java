package com.detective.util;

import java.io.*;

public class SaveManager {
    private static final String SAVE_FOLDER = "saves";

    public static void saveProgress(String playerName, int level, int score) {
        try {
            File dir = new File(SAVE_FOLDER);
            if (!dir.exists()) dir.mkdir();
            File file = new File(dir, playerName.toLowerCase() + ".save");
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(playerName);
                writer.println(level);
                writer.println(score);
            }
        } catch (IOException e) {
            System.out.println("Could not save progress: " + e.getMessage());
        }
    }

    public static int[] loadProgress(String playerName) {
        File file = new File(SAVE_FOLDER, playerName.toLowerCase() + ".save");
        if (!file.exists()) return null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            int level = Integer.parseInt(reader.readLine().trim());
            int score = Integer.parseInt(reader.readLine().trim());
            return new int[]{level, score};
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean hasSave(String playerName) {
        return new File(SAVE_FOLDER, playerName.toLowerCase() + ".save").exists();
    }

    public static void deleteSave(String playerName) {
        File file = new File(SAVE_FOLDER, playerName.toLowerCase() + ".save");
        if (file.exists()) file.delete();
    }
}