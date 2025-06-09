package trivial_pursuit.model;

import java.util.ArrayList;
import java.util.List;

public class Spoke {
    private List<NodeModel> nodes;

    public Spoke(int size, Category start, Category end) {
        nodes = new ArrayList<NodeModel>();

        nodes.add(new NodeModel(start));

        for(int i = 0; i < size-2; i++) {
            Category random = Category.randomCategory();
            NodeModel middle = new NodeModel(random);
            nodes.add(middle);
            nodes.get(i).connect(middle);
        }

        NodeModel last = new NodeModel(end);
        nodes.getLast().connect(last);
        nodes.add(last);

        this.nodes.getFirst().setQuestion("this is the start of the spoke");
        this.nodes.getLast().setQuestion("this is the end of the spoke");
    }

    public static void connect(Spoke end, Spoke start) {
        end.nodes.getLast().connect(start.nodes.getFirst());
    }

}