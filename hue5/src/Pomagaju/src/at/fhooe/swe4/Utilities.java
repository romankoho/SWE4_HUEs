package at.fhooe.swe4;

import at.fhooe.swe4.administration.views.ArticleScene;
import at.fhooe.swe4.administration.views.DemandScene;
import at.fhooe.swe4.administration.views.OfficesScene;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class Utilities {

  public static Button createTextButton(String id, String caption) {
    Button button = new Button(caption);
    button.setId(id);
    button.setPadding(new Insets(5));
    button.setPrefSize(50,40);
    button.setMinSize(50,40);
    return button;
  }

  public static MenuBar createMenuBar(Stage window) {
    MenuItem manageDemand = new MenuItem("Bedarf verwalten");
    MenuItem manageReceivingOffices = new MenuItem("Annahmestellen verwalten");
    MenuItem manageArticles = new MenuItem("Hilfsgüter verwalten");
    Menu adminMenu = new Menu("Verwaltung");
    adminMenu.getItems().addAll(manageDemand, manageReceivingOffices, manageArticles);

    manageArticles.addEventHandler(ActionEvent.ACTION, (e) -> handleManageArticlesEvent(e, window));
    manageDemand.addEventHandler(ActionEvent.ACTION, (e) -> handleManageDemandEvent(e, window));
    manageReceivingOffices.addEventHandler(ActionEvent.ACTION, (e) -> handleManageOfficesEvent(e, window));

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().add(adminMenu);
    return menuBar;
  }

  private static void handleManageOfficesEvent(ActionEvent e, Stage window) {
    double x = window.getWidth();
    double y = window.getHeight();
    window.setWidth(x);
    window.setHeight(y);
    OfficesScene officesScene = new OfficesScene(window);
    window.setScene(officesScene.getOfficesScene());
  }

  private static void handleManageDemandEvent(ActionEvent e, Stage window) {
    double x = window.getWidth();
    double y = window.getHeight();
    window.setWidth(x);
    window.setHeight(y);
    DemandScene demandScene = new DemandScene(window);
    window.setScene(demandScene.getMainScene());
  }

  private static void handleManageArticlesEvent(ActionEvent e, Stage window) {
    double x = window.getWidth();
    double y = window.getHeight();

    window.setWidth(x);
    window.setHeight(y);

    ArticleScene articleScene = new ArticleScene(window);
    window.setScene(articleScene.getManageArticleScene());
  }

  public static void sceneSetup(Window owner, Stage stage, Scene scene, String stageTitle) {
    stage.setScene(scene);
    stage.setTitle(stageTitle);
    stage.setMinWidth(300);
    stage.setMinHeight(300);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initStyle(StageStyle.UTILITY);
    stage.initOwner(owner);
  }

}
