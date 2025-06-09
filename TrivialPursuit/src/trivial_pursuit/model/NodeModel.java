package trivial_pursuit.model;

import java.util.*;

public class NodeModel {

    // private attributen
    private Category category;
    String question;
    String answer;

    // Double linked list
    private List<NodeModel> next;
    private List<NodeModel> prev;

    public NodeModel(Category category) {
        this.category = category;
        next = new ArrayList<>();
        prev = new ArrayList<>();
    }

    public void connect(NodeModel node) {
        next.add(node);
        node.prev.add(this);
    }

    public Category getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}