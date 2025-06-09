package labyrinth.model.interfaces;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public interface CSVInputHandler {

    static char[][] readMazeFromCSV(String file) {
        Path myFile = Paths.get("resources/csv/" + file);

        int maxRowLength = 0;
        int maxColLength = 0;

        try (Scanner fileScanner = new Scanner(myFile)) {
            while (fileScanner.hasNext()) {
                String[] line = fileScanner.nextLine().split(",");
                if (line.length > maxRowLength) {
                    maxRowLength = line.length;
                }
                maxColLength++;
            }
        } catch (IOException ioe) {
            System.out.println("Could not read " + file + " size");
        }

        char[][] maze = new char[maxColLength][maxRowLength];
        try (Scanner fileScanner = new Scanner(myFile)) {
            int y = 0;
            while (fileScanner.hasNext()) {
                String[] line = fileScanner.nextLine().split(",");

                for (int x = 0; x < line.length; x++) {
                    if (!line[x].isEmpty()) {
                        maze[y][x] = line[x].charAt(0);
                    }
                }

                y++;
            }
        } catch (IOException ioe) {
            System.out.println("Could not scan " + myFile + " (file does exist)");
        }

        return maze;
    }

    static boolean fileExists (String file) {
        String path = "resources/csv/" + file;
        Path myFile = Paths.get(path);
        return Files.exists(myFile);
    }

}