package labyrinth.model.entities;

import labyrinth.model.LabyrinthModel;

public class Wall extends EntityModel {
    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);

        this.setHitbox(width/4 - 1, height/4 - 1, width/2 + 4, height/2 + 4);
    }

    @Override
    public void actionWhenCollided(Player player) {
        player.resetPosition();
        player.decreaseLives();

        for (Ghost ghost : LabyrinthModel.getInstance().getGhosts()) {
            ghost.reset();
        }
    }

    @Override
    public void reset() {
        // do nothing
    }
}
