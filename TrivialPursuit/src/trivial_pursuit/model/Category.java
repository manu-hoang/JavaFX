package trivial_pursuit.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
The questions are divided into separate categories:
    ● Geography (blue)
    ● Amusement (pink)
    ● History (yellow)
    ● Arts & Literature (brown)
    ● Sciences & nature (green)
    ● Sports & recreational activities (orange)
*/
public enum Category {
    GEOGRAPHY,
    AMUSEMENT,
    HISTORY,
    ARTS,
    SCIENCE,
    SPORTS,
    ROLL,
    ALL; // start
    
    public static final List<Category> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    // returns a random category
    // optional: exclude category parameters
    public static Category randomCategory(Category... exclude)  {
        if(exclude.length == SIZE) return null;

        Category random;

        do {random = VALUES.get(RANDOM.nextInt(SIZE));}
        while (Arrays.asList(exclude).contains(random) || random == ALL);

        return random;
    }

}
