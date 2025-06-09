package labyrinth.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * Depth First Search maze generator (random)
 * Extra functionality that was definitely not needed
 * Same code logic gets used for Ghost AI
 * ( just refactored by quite a lot)
* */
public class DFSMazeGenerator {

    int snapshotCount;

    Cell currentCell;

    public DFSMazeGenerator() {
        snapshotCount = 0;
        currentCell = null;
    }

    public class Cell {
        int x, y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Returns a random UNVISITED neighbouring cell
        public Cell getNeighbour(Cell[][] maze) {
            List<Cell> neighbours = new ArrayList<>();

            Map<String, int[]> directions = new HashMap<>();

            directions.put("up", new int[]{0, -1});
            directions.put("down", new int[]{0, 1});
            directions.put("left", new int[]{-1, 0});
            directions.put("right", new int[]{1, 0});

            for (Map.Entry<String, int[]> direction : directions.entrySet()) {

                int neighbourX = this.x + directions.get(direction.getKey())[0] * 2;
                int neighbourY = this.y + directions.get(direction.getKey())[1] * 2;

                if(0 < neighbourX && neighbourX < maze[0].length && 0 < neighbourY && neighbourY < maze.length) {
                    Cell neighbour = maze[neighbourY][neighbourX];
                    if(!(neighbour instanceof Wall || neighbour instanceof Path)) {neighbours.add(maze[neighbourY][neighbourX]);}
                }
            }

            if(neighbours.isEmpty()) {return null;}

            Random rand = new Random();
            return neighbours.get(rand.nextInt(neighbours.size()));
        }
    }

    class Wall extends Cell {
        Wall(int x, int y) {
            super(x, y);
        }
    }

    class Path extends Cell {
        Path(int x, int y) {
            super(x, y);
        }
    }

    class Player extends Cell {
        Player(int x, int y) {
            super(x, y);
        }
    }

    class Ghost extends Cell {
        Ghost(int x, int y) {
            super(x, y);
        }
    }

    class Exit extends Cell {
        Exit(int x, int y) {
            super(x, y);
        }
    }

    // Generates empty maze filled walls
    // All "Nodes" are set to unvisited by default
    Cell[][] generateEmptyMaze(int width, int height) {
        Cell[][] maze = new Cell[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if(x % 2 == 0) {maze[y][x] = new Wall(x,y);}
                if(y % 2 == 0) {maze[y][x] = new Wall(x,y);}

                // Fill maze with empty cells
                if(maze[y][x] == null) { maze[y][x] = new Cell(x,y);}
            }
        }

        return maze;
    }

    // Generates a random maze of width and height using DFS
    public void generateRandomMaze (int width, int height) {
        Cell[][] maze = generateEmptyMaze(width, height);

        // Clear any previous snapshots (used for displaying)
        clearDirectory(new File("resources/csv/random_maze_snapshots"));

        Stack<Cell> visitedCells = new Stack<>();

        // Random maze always starts at top left corner
        Path startingCell = new Path(1,1);
        maze[1][1] = startingCell;
        visitedCells.push(startingCell);

        while (!visitedCells.isEmpty()) {
            currentCell = visitedCells.peek();
            outputMazeSnapshot(maze);

            Cell randomNeighbour = currentCell.getNeighbour(maze);

            // Dead end / could not find any non-visited neighbours
            if (randomNeighbour == null) {
                visitedCells.pop();
                continue;
            }

            // Push new cell onto the stack and repeat
            Path newCell = new Path(randomNeighbour.x, randomNeighbour.y);
            maze[newCell.y][newCell.x] = newCell;

            // Remove wall inbetween nodes
            // Removal is done by replacing with a Path node
            int deltaX = (newCell.x - currentCell.x)/2;
            int deltaY = (newCell.y - currentCell.y)/2;

            int cellX = currentCell.x + deltaX;
            int cellY = currentCell.y + deltaY;

            Path inbetween = new Path(cellX, cellY);
            maze[cellY][cellX] = inbetween;

            visitedCells.push(newCell);
        }

        // Player top left
        maze[1][1] = new Player(1,1);

        generateRandomExit(maze);

        // output final maze with Player and Exit
        outputMazeSnapshot(maze);
    }

    // TODO: FIX, CURRENTLY BUGGED AND IS NOT MOVING
    private void generateRandomGhost(Cell[][] maze) {
        List<Cell> corridors = new ArrayList<>();

        Map<String, int[]> directions = new HashMap<>();

        directions.put("up", new int[]{0, -1});
        directions.put("down", new int[]{0, 1});
        directions.put("left", new int[]{-1, 0});
        directions.put("right", new int[]{1, 0});

        for(Cell[] row : maze) {
            for(Cell cell : row) {
                if(!(cell instanceof Path)) {continue;}

                // amount of open directions of cell
                int counter = 0;
                for (Map.Entry<String, int[]> direction : directions.entrySet()) {

                    int neighbourX = cell.x + directions.get(direction.getKey())[0];
                    int neighbourY = cell.y + directions.get(direction.getKey())[1];

                    Cell neighbour = maze[neighbourY][neighbourX];
                    if(neighbour instanceof Wall) {counter++;}

                }

                if(counter == 2){
                    corridors.add(cell);
                }

            }
        }

        Random rand = new Random();
        Cell end = corridors.get(rand.nextInt(corridors.size()));

        maze[end.y][end.x] = new Ghost(end.x, end.y);
    }

    // Generates a random exit in a "dead end"
    // Dead ends are described by 3 adjacent walls
    private void generateRandomExit(Cell[][] maze) {
        List<Cell> deadEnds = new ArrayList<>();

        Map<String, int[]> directions = new HashMap<>();

        directions.put("up", new int[]{0, -1});
        directions.put("down", new int[]{0, 1});
        directions.put("left", new int[]{-1, 0});
        directions.put("right", new int[]{1, 0});

        for(Cell[] row : maze) {
            for(Cell cell : row) {
                if(!(cell instanceof Path)) {continue;}

                // amount of open directions of cell
                int counter = 0;
                for (Map.Entry<String, int[]> direction : directions.entrySet()) {

                    int neighbourX = cell.x + directions.get(direction.getKey())[0];
                    int neighbourY = cell.y + directions.get(direction.getKey())[1];

                    Cell neighbour = maze[neighbourY][neighbourX];
                    if(neighbour instanceof Wall) {counter++;}

                }

                if(counter == 3){
                    deadEnds.add(cell);
                }

            }
        }

        Random rand = new Random();
        Cell end = deadEnds.get(rand.nextInt(deadEnds.size()));

        maze[end.y][end.x] = new Exit(end.x, end.y);
    }

    // Outputs maze in current state
    // Generates a bunch of files in resources/csv/random_maze_snapshots
    // Snapshots are displayed one by one in StackPane
    private void outputMazeSnapshot(Cell[][] maze) {
        try {
            File myObj = new File("resources/csv/random_maze_snapshots/snapshot" + snapshotCount + ".csv");

            FileWriter writer = new FileWriter(myObj);

            for(int y = 0 ; y < maze.length ; y++) {
                for ( int x = 0; x < maze[0].length; x++) {
                    Cell cell = maze[y][x];
                    if(cell instanceof Wall) {writer.write('W');}
                    else if(cell instanceof Player) {writer.write('P');}
                    else if(cell instanceof Ghost) {writer.write('G');}
                    else if(cell instanceof Exit) {writer.write('E');}
                    else if(x == currentCell.x && y == currentCell.y) {writer.write('C');}

                    if(x != maze.length - 1) {writer.write(',');}
                }

                writer.write('\n');
            }

            System.out.println("snapshot" + snapshotCount + ".csv has been generated");

            writer.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        snapshotCount++;
    }

    // Clears directory
    public static void clearDirectory(File dir) {
        for (File file: dir.listFiles()) {
            if (file.isDirectory())
                clearDirectory(file);
            file.delete();
            System.out.println("deleting: " + file);
        }
    }
}
