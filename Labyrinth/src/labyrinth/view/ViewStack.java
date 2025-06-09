package labyrinth.view;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/** Public singleton StackPane class
 *  StackPane peek gets displayed on the users window
 * @author Manu Hoang
 */

public class ViewStack {
    StackPane stack;

    // Singleton instance
    private static ViewStack singleInstance = null;

    private ViewStack() {
        stack = new StackPane();
    }

    // Static method to create instance of Singleton class
    public static synchronized ViewStack getInstance() {
        if (singleInstance == null){
            singleInstance = new ViewStack();
        }
        return singleInstance;
    }

    /**
    * Pushes a node onto the Stack and displays it
    * @param node   node to be displayed
    * */
    public void push(Node node) {
        stack.getChildren().add(node);
        node.setVisible(true);
    }

    /**
     * Removes the top node
    * */
    public void pop() {
        stack.getChildren().removeLast();
    }

    /**
     * Returns the top node (does not remove it)
     * @return  the displayed node
    * */
    public Node peek() {
        return stack.getChildren().getLast();
    }

    /**
    * Returns to the "Home" screen by removing every node except the first
    * */
    public void home () {
        Node home = stack.getChildren().getFirst();

        while (!stack.getChildren().isEmpty()){
            stack.getChildren().removeLast();
        }

        stack.getChildren().add(home);
        stack.getChildren().getLast().requestFocus();
    }

    /**
    * Getter for StackPane
    * */
    public StackPane getStackPane() {
        return stack;
    }
}
