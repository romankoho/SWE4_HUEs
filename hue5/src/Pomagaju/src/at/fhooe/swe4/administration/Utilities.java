package at.fhooe.swe4.administration;

import at.fhooe.swe4.administration.views.ArticleScene;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

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
    MenuItem manageReceivingOffices = new MenuItem("Annahmestellen verwalten");
    MenuItem manageArticles = new MenuItem("Hilfsgüter verwalten");
    Menu adminMenu = new Menu("Verwaltung");
    adminMenu.getItems().addAll(manageReceivingOffices, manageArticles);

    manageArticles.addEventHandler(ActionEvent.ACTION, (e) -> handleManageArticlesEvent(e, window));

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().add(adminMenu);
    return menuBar;
  }

  private static void handleManageArticlesEvent(ActionEvent e, Stage window) {
    double x = window.getWidth();
    double y = window.getHeight();

    window.setWidth(x);
    window.setHeight(y);

    ArticleScene articleScene = new ArticleScene(window);
    window.setScene(articleScene.getManageArticleScene());
  }

}
