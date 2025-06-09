package labyrinth.model.entities;

import labyrinth.model.StopwatchModel;

public class TimePotion extends EntityModel {
    private boolean consumed;

    public TimePotion(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setHitbox(width/4, height/4, width/2, height/2);

        consumed = false;
    }

    @Override
    public void actionWhenCollided(Player player) {
        if(consumed){return;}

        this.consumed = true;

        try {StopwatchModel.getInstance().rewind(5);}
        catch (Exception e) {
            System.out.println("rewind time failed");
        }
    }

    @Override
    public void reset() {
        resetPosition();
        consumed = false;
    }

    public boolean isConsumed() {
        return consumed;
    }
}
