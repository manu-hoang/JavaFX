package labyrinth.model.entities;

import labyrinth.model.LabyrinthModel;
import labyrinth.model.interfaces.SettingsHandler;

import java.util.List;

public class Player extends EntityModel {

    private int lives;
    private boolean alive;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setHitbox(width/4, height/4, width/2 + 2, height/2 + 2);

        alive = true;
        lives = 3;

        this.setSpeed(SettingsHandler.getPlayerSpeed());
    }

    @Override
    public void actionWhenCollided(Player player) {
        // do nothing
        // player cant collide with itself
    }

    @Override
    public void reset() {
        resetLives();
        resetPosition();
        resetDirection();
    }

    // Check if Player is standing on portal
    // This prevents Player from infinitely teleporting back and forth
    public void updateOnPortal() {
        List<Portal> portals = LabyrinthModel.getInstance().getPortals();

        for (Portal portal : portals) {
            if(LabyrinthModel.getInstance().collision(this, portal)){
                portal.setPlayerOnPortal(true);
            } else {
                portal.setPlayerOnPortal(false);
            }
        }
    }

    public void resetLives() {
        lives = 3;
        alive = true;
    }

    public void resetDirection() {
        this.goNorth = false;
        this.goEast = false;
        this.goSouth = false;
        this.goWest = false;

    }

    public void decreaseLives() {
        this.lives--;

        if (this.lives == 0) {alive = false;}
    }

    public int getLives() {
        return lives;
    }

}
