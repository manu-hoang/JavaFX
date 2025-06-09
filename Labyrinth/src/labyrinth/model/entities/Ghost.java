package labyrinth.model.entities;

import javafx.util.Pair;
import labyrinth.model.LabyrinthModel;
import labyrinth.model.interfaces.SettingsHandler;

import java.util.*;

public class Ghost extends EntityModel {
    public Ghost(int x, int y, int width, int height) {
        super(x, y, width, height);

        this.setSprint(true);
        this.setSpeed(SettingsHandler.getGhostSpeed());

        this.setHitbox(width/4, height/4, width/2 + 2, height/2 + 2);
        this.goEast = true;
    }

    @Override
    public void actionWhenCollided(Player player) {
        player.resetPosition();
        player.decreaseLives();
        this.reset();
    }

    // Check for collision on next step
    public boolean collided() {
        LabyrinthModel game = LabyrinthModel.getInstance();

        boolean collided = false;

        Ghost copy = new Ghost(this.x, this.y, this.width, this.height);
        copy.setHitbox(-this.getHitboxWidth()/2 + 4, -this.getHitboxHeight()/2 + 4, this.getHitboxWidth()*2 + 8, this.getHitboxHeight() * 2 + 8);
        copy.setSpeed(1);

        // move in direction
        copy.move();

        for (EntityModel[] row : game.getMaze()) {
            for (EntityModel wall : row) {
                if(!(wall instanceof Wall)){continue;}
                if(game.collision(wall, copy)) {
                    collided = true;
                }
            }
        }

        return collided;
    }

    public boolean inCorridor() {
        LabyrinthModel game = LabyrinthModel.getInstance();

        // All directions are "open", if collision in set direction set to false
        boolean north = true, south = true, east = true, west = true;

        enum Direction {
            UP,
            DOWN,
            LEFT,
            RIGHT
        }

        Direction[] directions = Direction.values();

        Ghost copy = new Ghost(this.x, this.y, this.width, this.height);

        for (Direction direction : directions) {
            // reset values for every iteration
            copy.x = this.x;
            copy.y = this.y;

            copy.goNorth = false;
            copy.goSouth = false;
            copy.goEast = false;
            copy.goWest = false;

            switch (direction) {
                case UP:
                    copy.goNorth = true;
                    break;

                case DOWN:
                    copy.goSouth = true;
                    break;

                case LEFT:
                    copy.goEast = true;
                    break;

                case RIGHT:
                    copy.goWest = true;
                    break;

                default:
                    break;
            }

            // move in direction
            copy.move();

            if(copy.collided()){
                switch (direction) {
                    case UP:
                        north = false;
                        break;

                    case DOWN:
                        south = false;
                        break;

                    case LEFT:
                        east = false;
                        break;

                    case RIGHT:
                        west = false;
                        break;

                    default:
                        break;
                }
            }
        }

        return (north || south) && (east || west);
    }

    public boolean deadEnd() {

        // All directions are "open", if collision in set direction set to false
        boolean north = true, south = true, east = true, west = true;

        enum Direction {
            UP,
            DOWN,
            LEFT,
            RIGHT
        }

        Direction[] directions = Direction.values();

        Ghost copy = new Ghost(this.x, this.y, this.width, this.height);

        for (Direction direction : directions) {
            // reset values for every iteration
            copy.x = this.x;
            copy.y = this.y;

            copy.goNorth = false;
            copy.goSouth = false;
            copy.goEast = false;
            copy.goWest = false;

            switch (direction) {
                case UP:
                    copy.goNorth = true;
                    break;

                case DOWN:
                    copy.goSouth = true;
                    break;

                case LEFT:
                    copy.goEast = true;
                    break;

                case RIGHT:
                    copy.goWest = true;
                    break;

                default:
                    break;
            }

            // move in direction
            copy.move();

            if(copy.collided()){
                switch (direction) {
                    case UP:
                        north = false;
                        break;

                    case DOWN:
                        south = false;
                        break;

                    case LEFT:
                        east = false;
                        break;

                    case RIGHT:
                        west = false;
                        break;

                    default:
                        break;
                }
            }
        }

        int counter = 0;

        if (north) {counter++;}
        if (south) {counter++;}
        if (east) {counter++;}
        if (west) {counter++;}

        return counter == 1;
    }

    public void reverseDirection() {
        if (this.goNorth) {
            this.goNorth = false;
            this.goSouth = true;
        }

        if (this.goSouth) {
            this.goSouth = false;
            this.goNorth = true;
        }

        if (this.goWest) {
            this.goWest = false;
            this.goEast = true;
        }

        if (this.goEast) {
            this.goEast = false;
            this.goWest = true;
        }
    }

    // Calculates direction based on Manhattan distance
    public void calculateDirection() {
        this.goNorth = false;
        this.goSouth = false;
        this.goEast = false;
        this.goWest = false;

        LabyrinthModel game = LabyrinthModel.getInstance();
        Player player = game.getPlayer();

        Pair<Integer, Integer> up = new Pair<>(0, -1);
        Pair<Integer, Integer> down = new Pair<>(0, 1);
        Pair<Integer, Integer> left = new Pair<>(-1, 0);
        Pair<Integer, Integer> right = new Pair<>(1, 0);

        List<Pair<Integer, Integer>> Directions = Arrays.asList(up, down, left, right);

        Ghost copy = new Ghost(this.x, this.y, this.getWidth(), this.getHeight());

        int min_distance = Integer.MAX_VALUE;
        Pair<Integer, Integer> min_direction = null;
        for (Pair<Integer, Integer> direction : Directions) {
            // reset copy position
            copy.x = this.x;
            copy.y = this.y;

            // move in direction
            copy.x += direction.getKey() * LabyrinthModel.getInstance().tileSize;
            copy.y += direction.getValue() * LabyrinthModel.getInstance().tileSize;

            for (EntityModel[] row : game.getMaze()) {
                for (EntityModel entity : row) {
                    if(entity instanceof Wall && game.collision(entity,copy)){
                        break;
                    }
                }
            }

            // calculate distance
            int distance = copy.manhattanDistance(player);
            if (distance < min_distance) {
                min_distance = distance;
                min_direction = direction;
            }
        }

        if (min_direction == up) {this.goNorth = true;}
        else if (min_direction == down) {this.goSouth = true;}
        else if (min_direction == left) {this.goWest = true;}
        else if (min_direction == right) {this.goEast = true;}
    }

    private int manhattanDistance (EntityModel player) {
        // Calculate center points
        int ghost_x = this.x + this.getWidth()/2;
        int ghost_y = this.y + this.getHeight()/2;

        int player_x = player.getX() + player.getWidth()/2;
        int player_y = player.getY() + player.getHeight()/2;

        // Calculate Manhattan Distance
        int deltaX = Math.abs(ghost_x - player_x);
        int deltaY = Math.abs(ghost_y - player_y);

        return deltaX + deltaY;
    }

    // Returns a random UNVISITED neighbouring cell
    // Used for DFS
    public EntityModel getNeighbour(int canvas_x, int canvas_y) {

        int x = Math.round((float) canvas_x / LabyrinthModel.getInstance().tileSize);
        int y = Math.round((float) canvas_y / LabyrinthModel.getInstance().tileSize);

        LabyrinthModel maze = LabyrinthModel.getInstance();
        List<EntityModel> neighbours = new ArrayList<>();

        Map<String, int[]> directions = new HashMap<>();

        directions.put("up", new int[]{0, -1});
        directions.put("down", new int[]{0, 1});
        directions.put("left", new int[]{-1, 0});
        directions.put("right", new int[]{1, 0});

        for (Map.Entry<String, int[]> direction : directions.entrySet()) {

            int neighbour_x = x + directions.get(direction.getKey())[0];
            int neighbour_y = y + directions.get(direction.getKey())[1];

            if(0 < neighbour_x && neighbour_x < LabyrinthModel.getInstance().mapColumns && 0 < neighbour_y && neighbour_y < LabyrinthModel.getInstance().mapRows) {
                EntityModel neighbour = maze.getEntity(neighbour_x, neighbour_y);
                if(neighbour == null){continue;}
                if(neighbour instanceof Wall){continue;}

                if(!neighbour.visited) {
                    neighbours.add(neighbour);
                }
            }
        }

        if(neighbours.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        return neighbours.get(rand.nextInt(neighbours.size()));
    }

    // Calculates direction based on DFS
    public void calculateDirectionDFS() {
        this.goNorth = false;
        this.goSouth = false;
        this.goEast = false;
        this.goWest = false;

        LabyrinthModel maze = LabyrinthModel.getInstance();

        // Reset all entities
        EntityModel[][] map = maze.getMaze();
        for(EntityModel[] row : map) {
            for(EntityModel entity : row) {
                entity.visited = false;
            }
        }

        Player player = LabyrinthModel.getInstance().getPlayer();

        int player_x = Math.round((float) player.x / LabyrinthModel.getInstance().tileSize);
        int player_y = Math.round((float) player.y / LabyrinthModel.getInstance().tileSize);

        Stack<EntityModel> visited_cells = new Stack<>();

        int copy_x = Math.round((float) this.x / LabyrinthModel.getInstance().tileSize);
        int copy_y = Math.round((float) this.y / LabyrinthModel.getInstance().tileSize);
        EntityModel copy = maze.getEntity(copy_x, copy_y);
        copy.visited = true;

        visited_cells.push(copy);

        while (!visited_cells.isEmpty()) {

            EntityModel current_cell = visited_cells.peek();

            EntityModel random_cell = getNeighbour(current_cell.x, current_cell.y);

            // did not find player
            if (random_cell == null && visited_cells.size() == 1) {
                break;
            }

            if (random_cell == null) {
                visited_cells.pop();
                continue;
            }

            int matrix_x = Math.round((float) current_cell.x / LabyrinthModel.getInstance().tileSize);
            int matrix_y = Math.round((float) current_cell.y / LabyrinthModel.getInstance().tileSize);

            if(matrix_x == player_x && matrix_y == player_y) {

                // pop everything and store direction
                while(visited_cells.size() > 2) {
                    visited_cells.pop();
                }

                EntityModel direction = visited_cells.peek();

                int delta_x = Math.round((float) (direction.x - this.x) / 32);
                int delta_y = Math.round((float) (direction.y - this.y) / 32);

                if(delta_x > 0) {this.goEast = true;}
                else if(delta_x < 0) {this.goWest = true;}
                else if(delta_y < 0) {this.goNorth = true;}
                else if(delta_y > 0) {this.goSouth = true;}

                visited_cells.clear();
                break;
            }

            EntityModel next_cell = maze.getEntity(Math.round((float) random_cell.x /32) , Math.round((float) random_cell.y /32));
            next_cell.visited = true;

            visited_cells.push(next_cell);
        }
    }


    @Override
    public void reset() {
        resetPosition();
        calculateDirectionDFS();
    }

    /* exact opposite of move() function */
    public void undoMove(){
        while(this.collided()) {
            if (goNorth) {this.y += 1;}
            if (goSouth) {this.y -= 1;}
            if (goEast) {x -= 1;}
            if (goWest) {x += 1;}
        }
    }
}
