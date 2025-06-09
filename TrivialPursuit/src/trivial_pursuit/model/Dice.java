package trivial_pursuit.model;

import java.util.Random;

public interface Dice {

    static int roll(){
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

}
