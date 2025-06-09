package trivial_pursuit.view;


import trivial_pursuit.model.BoardModel;

public class ApplicatieNaamPresenter {
    private BoardModel model;
    private ApplicatieNaamView view;

    public ApplicatieNaamPresenter(BoardModel model, ApplicatieNaamView view) {
        this.model = model;
        this.view = view;
        this.addEventHandlers();
        this.updateView();
    }

    private void addEventHandlers() {
        // Koppelt event handlers (anon. inner klassen)
        // aan de controls uit de view.
        // Event handlers: roepen methodes aan uit het
        // model en zorgen voor een update van de view.
    }

    private void updateView() {
        // Vult de view met data uit model
        view.createSpokes(4);
    }
}