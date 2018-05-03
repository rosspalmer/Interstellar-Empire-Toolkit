package ross.palmer.interstellar.gui.explorer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ross.palmer.interstellar.Main;
import ross.palmer.interstellar.simulator.galaxy.StarData;


public class StarTableView extends TableView {

    private ObservableList<StarData> data =
            FXCollections.observableArrayList();

    public StarTableView() {

        Main.getGalaxy().getStellarSystems()
                .forEach(stellarSystem -> data.add(stellarSystem.getStarData()));

        TableColumn<StarData, Long> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<StarData, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn<StarData, Double> x = new TableColumn<>("X");
        x.setMinWidth(10);
        x.setCellValueFactory(
                new PropertyValueFactory<>("x"));

        TableColumn<StarData, Double> y = new TableColumn<>("Y");
        y.setMinWidth(10);
        y.setCellValueFactory(
                new PropertyValueFactory<>("y"));

        TableColumn<StarData, Double> z = new TableColumn<>("Z");
        z.setMinWidth(10);
        z.setCellValueFactory(
                new PropertyValueFactory<>("z"));

        TableColumn<StarData, String> spectFull = new TableColumn<>("spectFull");
        spectFull.setMinWidth(100);
        spectFull.setCellValueFactory(
                new PropertyValueFactory<>("spectFull"));

        TableColumn<StarData, StarData.StarClass> starClass = new TableColumn<>("Star Class");
        starClass.setMinWidth(100);
        starClass.setCellValueFactory(
                new PropertyValueFactory<>("starClass"));

        TableColumn<StarData, StarData.StarClass> starSequence = new TableColumn<>("Star Sequence");
        starSequence.setMinWidth(100);
        starSequence.setCellValueFactory(
                new PropertyValueFactory<>("starSequence"));

        setItems(data);
        getColumns().addAll(idCol, nameCol, x, y, z, spectFull, starClass, starSequence);

    }

}
