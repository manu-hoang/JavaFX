package trivial_pursuit.model;

import java.util.ArrayList;
import java.util.List;

import static trivial_pursuit.model.Category.*;
import static trivial_pursuit.model.Spoke.connect;

public class BoardModel {

    // private attributen
    List<NodeModel> nodes;
    List<Spoke> spokes;
    List<Spoke> circle;

    public BoardModel() {
        nodes = new ArrayList<>();
        spokes = new ArrayList<>();
        circle = new ArrayList<>();

        // Starting node
        NodeModel start = new NodeModel(ALL);

        // Create spokes
        for(int i = 0; i < 4; i++){
            Spoke spoke = new Spoke(6, ARTS, ALL);
            spokes.add(spoke);
        }

        // For each spoke create an arc
        for(int i = 0; i < spokes.size(); i++){
            Spoke arc = new Spoke(6, ALL, ALL);
            circle.add(arc);

            connect(spokes.get(i), arc);
        }

        // TODO: connect spokes to left arc in circle
        for(int i = 0; i < spokes.size(); i++){
        }



    }

    public List<Spoke> getSpokes() {
        return spokes;
    }
}
