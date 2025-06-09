package labyrinth.model.interfaces;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ScoreOutputHandler {

    // In case of tie, first entry > last entry
    // first in, first on table
    // TODO: reset highscores files if corrupted / invalid
    static void writeScore(String player, int score) {

        List<String> highScoresList = getHighScores();

        if(highScoresList == null) { highScoresList = new ArrayList<>();}

        int index = 0; // placement on scoreboard
        for (int i = 0; i < highScoresList.size(); i++) {
            String[] line = highScoresList.get(i).split(",");
            if(score >= Integer.parseInt(line[1])) {index = i + 1;}
        }

        highScoresList.add(index, player + "," + score);

        // Ensure only top 5 scores get saved
        int endIndex = Math.min(highScoresList.size(), 5);
        List<String> top5 = highScoresList.subList(0, endIndex);

        String unencrypted = String.join("\n", top5);
        String encrypted = encrypt(unencrypted);

        Path myFile = Paths.get("resources/highscores.txt");
        try {Files.write(myFile, encrypted.getBytes());}
        catch (IOException e) {System.out.println("writing high scores unsuccessful");}
    }

    static List<String> getHighScores(){
        Path myFile = Paths.get("resources/highscores.txt");

        String encrypted;
        try {encrypted = Files.readString(myFile, Charset.defaultCharset());}
        catch ( IOException e ) {
            System.out.println("could not read highscores.txt");
            return null;
        }

        String decrypted = decrypt(encrypted.toCharArray());

        if(decrypted.isEmpty()) {return null;}
        return new ArrayList<>(Arrays.asList(decrypted.split("\n")));
    }

    static private String encrypt(String string) {
        char[] chars = string.toCharArray();

        String encrypted = "";
        for (char c : chars) {
            c += 10;
            encrypted += c;
        }

        return encrypted;
    }

    static private String decrypt(char[] chars) {
        String decrypted = "";
        for (char c : chars) {
            c -= 10;
            decrypted += c;
        }

        return decrypted;
    }

}