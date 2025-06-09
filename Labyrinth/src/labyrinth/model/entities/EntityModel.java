package labyrinth.model.entities;

public class EntityModel {

    // POSITION
    protected int x;
    protected int y;
    protected final int width;
    protected final int height;

    // HITBOX
    protected int deltaHitboxX;     // Hitbox X = entity x + deltaHitboxX
    protected int deltaHitboxY;     // Hitbox Y = entity y + deltaHitboxY
    protected int hitboxWidth;
    protected int hitboxHeight;

    protected final int startingX;
    protected final int startingY;

    Boolean goNorth, goSouth, goEast, goWest;
    private double speed;  // amount of pixels per move()
    Boolean sprint;     // doubles speed

    protected boolean visited;
    public boolean focused;

    // Constructor
    public EntityModel(int x, int y, int width, int height) {

        // ENTITY POSITION
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // HITBOX
        deltaHitboxX = 0;
        deltaHitboxY = 0;
        hitboxWidth = 0;
        hitboxHeight = 0;

        // STARTING POSITION
        this.startingX = x;
        this.startingY = y;

        // PIXELS PER MOVE
        this.speed = 0;
        this.sprint = false;

        // DIRECTIONS
        this.goNorth = false;
        this.goSouth = false;
        this.goEast = false;
        this.goWest = false;

        visited = false;
    }

    /* moves entity in set direction based on speed */
    public void move(){
        double distance = this.speed;

        if (sprint) { distance *= 2;}

        if (goNorth) {this.y -= distance;}
        if (goSouth) {this.y += distance;}
        if (goEast) {x += distance;}
        if (goWest) {x -= distance;}
    }

    // Resets entity position
    public void resetPosition() {
        this.x = this.startingX;
        this.y = this.startingY;
    }

    // Abstract collision method to be overwritten
    public void actionWhenCollided (Player player){};

    // Abstract method to be overwritten
    public void reset (){};

    // Teleports Entity to x,y
    public void teleport(int x, int y){
        this.x = x;
        this.y = y;
    }

    // GETTERS
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHitboxX() {
        return this.x + this.deltaHitboxX;
    }

    public int getHitboxY() {
        return this.y + this.deltaHitboxY;
    }

    public int getHitboxWidth() {
        return hitboxWidth;
    }

    public int getHitboxHeight() {
        return hitboxHeight;
    }

    public Boolean getGoNorth() {
        return goNorth;
    }

    public Boolean getGoSouth() {
        return goSouth;
    }

    public Boolean getGoEast() {
        return goEast;
    }

    public Boolean getGoWest() {
        return goWest;
    }

    public Boolean sprinting() {
        return sprint;
    }

    // SETTERS

    public void setGoNorth(Boolean goNorth) {
        this.goNorth = goNorth;
    }

    public void setGoSouth(Boolean goSouth) {
        this.goSouth = goSouth;
    }

    public void setGoEast(Boolean goEast) {
        this.goEast = goEast;
    }

    public void setGoWest(Boolean goWest) {
        this.goWest = goWest;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setSprint(Boolean sprint) {
        this.sprint = sprint;
    }

    public void setHitbox(int delta_x, int delta_y, int width, int height) {
        this.deltaHitboxX = delta_x;
        this.deltaHitboxY = delta_y;

        this.hitboxWidth = width;
        this.hitboxHeight = height;
    }
}