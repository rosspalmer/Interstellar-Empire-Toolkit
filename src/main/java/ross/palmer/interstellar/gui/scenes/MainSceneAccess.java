package ross.palmer.interstellar.gui.scenes;

public class MainSceneAccess {

    private static MainPaneScene mainPaneScene;

    public static void setMainPaneScene(MainPaneScene mainPaneScene) {
        MainSceneAccess.mainPaneScene = mainPaneScene;
    }

    public static MainPaneScene getMainPaneScene() {
        return mainPaneScene;
    }

    public static GalaxyPane getGalaxyPane() {
        return mainPaneScene.getGalaxyPane();
    }

}
