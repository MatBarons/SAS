package catering.ui.general;

import catering.businesslogic.CatERing;
import catering.businesslogic.event.Event;
import catering.businesslogic.event.Service;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class EventsInfoDialog {

    private Stage myStage;

    @FXML
    TreeView<Event> eventTree;

    public void initialize() {
        ObservableList<Event> all = CatERing.getInstance().getEventManager().getEventInfo();
        eventTree.setShowRoot(false);
        TreeItem<Event> root = new TreeItem<>(null);

        for (Event e: all) {
            TreeItem<Event> node = new TreeItem<>(e);
            root.getChildren().add(node);
            // ObservableList<Service> serv = e.getServices();
            // for (Service s: serv) {
            //     node.getChildren().add(new TreeItem<Event>(s));
            // }
        }

        eventTree.setRoot(root);
    }

    @FXML
    public void okButtonPressed() {
        myStage.close();
    }

    public void setOwnStage(Stage stage) {
        myStage = stage;
    }
}
