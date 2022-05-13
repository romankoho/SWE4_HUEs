package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.administration.Utilities;
import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.controller.OfficesController;
import at.fhooe.swe4.administration.models.ReceivingOffice;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    loginScene.getStylesheets().add(getClass().getResource("/loginScene.css").toString());
  }

  public static Scene getLoginScene() {
    return loginScene;
  }

  private static TableView<ReceivingOffice> officeTable;

  private static TableView<ReceivingOffice> createOfficeTable() {
    TableView<ReceivingOffice> officeTable = new TableView<>();
    officeTable.setId("office-table");
    officeTable.setItems(OfficesController.getInstance().getArrayList());

    TableColumn<ReceivingOffice, Integer> idCol = new TableColumn<>("ID");
    TableColumn<ReceivingOffice, String> nameCol = new TableColumn<>("Name");
    TableColumn<ReceivingOffice, String> stateCol = new TableColumn<>("Bundesland");
    TableColumn<ReceivingOffice, String> districtCol = new TableColumn<>("Bezirk");
    TableColumn<ReceivingOffice, String> addCol = new TableColumn<>("Adresse");

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    stateCol.setCellValueFactory(new PropertyValueFactory<>("federalState"));
    districtCol.setCellValueFactory(new PropertyValueFactory<>("district"));
    addCol.setCellValueFactory(new PropertyValueFactory<>("address"));

    idCol.setMinWidth(40);
    nameCol.setMinWidth(160);
    stateCol.setMinWidth(40);
    districtCol.setMinWidth(20);
    addCol.setMinWidth(60);

    officeTable.getColumns().add(idCol);
    officeTable.getColumns().add(nameCol);
    officeTable.getColumns().add(stateCol);
    officeTable.getColumns().add(districtCol);
    officeTable.getColumns().add(addCol);

    return officeTable;
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

      MainScene mainScene = new MainScene(window);
      window.setScene(mainScene.getMainScene());
    }
  }
}
