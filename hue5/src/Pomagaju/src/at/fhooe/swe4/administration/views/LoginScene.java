package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.controller.OfficesController;
import at.fhooe.swe4.administration.models.ReceivingOffice;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScene {

  private static Stage window;
  private static Scene loginScene;

  public LoginScene(Stage window) {
    this.window = window;

    Pane loginPane = new VBox(LoginScene.createLoginPane());
    loginPane.setId("login-pane");

    loginScene = new Scene(loginPane, 600,600);
    loginScene.getStylesheets().add(getClass().getResource("/administration.css").toString());
  }

  public static Scene getLoginScene() {
    return loginScene;
  }

  private static TableView<ReceivingOffice> officeTable;

  private static TableView<ReceivingOffice> createOfficeTable() {
    return OfficesScene.createOfficesTable();
  }

  protected static Pane createLoginPane(){
    Text loginInfo = new Text("Bitte wählen Sie Ihre Annahmestelle aus.");
    loginInfo.setId("login-pane-info-text");
    loginInfo.setWrappingWidth(300);

    Button startButton = Utilities.createTextButton("button-login", "Login");
    startButton.addEventHandler(ActionEvent.ACTION, (e) -> handleLoginButtonEvent(e));
    officeTable = createOfficeTable();

    VBox startPane = new VBox(loginInfo, officeTable, startButton);
    startPane.setId("login-screen-start-pane");

    return startPane;
  }

  private static void handleLoginButtonEvent(ActionEvent e) {

    ReceivingOffice selectedOffice = officeTable.getSelectionModel().getSelectedItem();
    if(selectedOffice != null) {
      OfficesController.getInstance().setActiveOffice(selectedOffice);

      DemandController.getInstance().getFilteredDemand().setPredicate(
              demandItem -> demandItem.getRelatedOffice().equals(OfficesController.getInstance().getActiveOffice())
      );

      double x = window.getWidth();
      double y = window.getHeight();
      window.setWidth(x);
      window.setHeight(y);

      DemandScene demandScene = new DemandScene(window);
      window.setScene(demandScene.getMainScene());
    }
  }
}
