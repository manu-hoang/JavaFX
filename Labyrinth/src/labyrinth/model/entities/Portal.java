package labyrinth.model.entities;

import labyrinth.model.LabyrinthModel;

public class Portal extends EntityModel {

    boolean playerOnPortal;

    public Portal(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setHitbox(width/4, height/4, width/2, height/2);
    }

    @Override
    public void actionWhenCollided(Player player) {
        if(playerOnPortal) {return;}

        Portal random_portal;
        do {random_portal = LabyrinthModel.getInstance().randomPortal();}
        while (random_portal == this);

        player.teleport(random_portal.getX(), random_portal.getY());
        random_portal.setPlayerOnPortal(true);
    }

    @Override
    public void reset() {
        resetPosition();
        playerOnPortal = false;
    }

    public void setPlayerOnPortal(boolean playerOnPortal) {
        this.playerOnPortal = playerOnPortal;
    }
}
