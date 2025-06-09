package labyrinth.model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
* Stopwatch singleton (can get accessed from any class)
* */
public class StopwatchModel {

    // Singleton instance
    private static StopwatchModel single_instance = null;

    private final Timeline stopwatchTimeline;
    private static final int START_TICK_DURATION_MILLIS = 1000;
    private int tickDurationMillis;

    private int seconds;

    int fpsCounter;

    // Stopwatch Initializer
    private StopwatchModel() {
        this.tickDurationMillis = START_TICK_DURATION_MILLIS;

        this.stopwatchTimeline = new Timeline(new KeyFrame(Duration.millis(tickDurationMillis), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seconds++;

                System.out.println("Frames Per Second: " + fpsCounter);
                fpsCounter = 0;
            }
        }));
        stopwatchTimeline.setCycleCount(Animation.INDEFINITE);
    }

    // Static method to create instance of Singleton class
    public static synchronized StopwatchModel getInstance() {
        if (single_instance == null){
            single_instance = new StopwatchModel();
        }
        return single_instance;
    }

    // Start stopwatch
    public void start() {
        stopwatchTimeline.play();
    }

    // Reset stopwatch
    public void reset() {
        this.seconds = 0;
    }

    // Stop stopwatch
    public void stop() {
        stopwatchTimeline.stop();
    }

    // @param is in seconds
    public void rewind(int amount) {
        this.seconds -= amount;

        if(this.seconds < 0){
            this.seconds = 0;
        }
    }

    // FPS counter
    public void fpsTick () {
        fpsCounter++;
    }

    public int getSeconds() {
        return seconds;
    }
}
