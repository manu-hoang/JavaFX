package labyrinth.model.entities;

import labyrinth.model.LabyrinthModel;

public class Exit extends EntityModel {

    public Exit(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setHitbox(width/4, height/4, width/2, height/2);
    }

    @Override
    public void actionWhenCollided(Player player) {
        LabyrinthModel.getInstance().setLevelFinished(true);
    }

    @Override
    public void reset() {
        resetPosition();
    }

}
