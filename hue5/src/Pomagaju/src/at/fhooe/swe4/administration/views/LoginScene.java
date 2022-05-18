package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.controller.OfficesController;
import at.fhooe.swe4.administration.models.ReceivingOffice;
import at.fhooe.swe4.donationsApp.controller.DonationsController;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.rmi.CORBA.Util;

public class LoginScene {

  private Stage window;
  private Scene loginScene;

  public LoginScene(Stage window) {
    this.window = window;

    Pane loginPane = new VBox(createLoginPane());
    loginPane.setId("login-pane");

    loginScene = new Scene(loginPane, 600,600);
    loginScene.getStylesheets().add(getClass().getResource("/administration.css").toString());
  }

  public Scene getLoginScene() {
    return loginScene;
  }

  private TableView<ReceivingOffice> officeTable;


  private Pane createLoginPane(){
    Text loginInfo = new Text("Bitte wählen Sie Ihre Annahmestelle aus.");
    loginInfo.setId("login-pane-info-text");
    loginInfo.setWrappingWidth(300);

    Button startButton = Utilities.createTextButton("button-login", "Login");
    startButton.addEventHandler(ActionEvent.ACTION, (e) -> handleLoginButtonEvent(e));
    officeTable = Utilities.createOfficesTable();

    VBox startPane = new VBox(loginInfo, officeTable, startButton);
    startPane.setId("login-screen-start-pane");

    return startPane;
  }

  private void handleLoginButtonEvent(ActionEvent e) {

    ReceivingOffice selectedOffice = officeTable.getSelectionModel().getSelectedItem();
    if(selectedOffice != null) {
      OfficesController.getInstance().setActiveOffice(selectedOffice);

      DemandController.getInstance().getFilteredDemand().setPredicate(
              demandItem -> demandItem.getRelatedOffice().equals(OfficesController.getInstance().getActiveOffice())
      );

      DonationsController.getInstance().getFilteredDonations().setPredicate(
              donation -> donation.getRelatedDemand().getRelatedOffice().equals(OfficesController.getInstance().getActiveOffice())
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
