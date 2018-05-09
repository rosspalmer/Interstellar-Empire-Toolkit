package ross.palmer.interstellar.gui.explorer;

import javafx.scene.layout.GridPane;

public class SystemExplorer extends GridPane {

    private StarMap starMap;
    private StarTableView starTableView;

    public SystemExplorer() {

        System.out.println("-- Generating System Explorer --");
        starMap = new StarMap();
//        starMap.resize(1500, 400);
        starTableView = new StarTableView();
        starTableView.resize(1500, 700);

        add(starMap, 0, 0);
        add(starTableView, 0, 1);

        System.out.println("-- System Explorer Ready --");

    }

}
