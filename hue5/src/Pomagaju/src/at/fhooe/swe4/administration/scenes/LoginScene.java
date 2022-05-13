package at.fhooe.swe4.administration.scenes;

import at.fhooe.swe4.administration.Utilities;
import at.fhooe.swe4.administration.data.DemandItem;
import at.fhooe.swe4.administration.data.ReceivingOffice;
import at.fhooe.swe4.administration.enums.FederalState;
import at.fhooe.swe4.administration.enums.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScene {

  private static Stage window;
  private Scene loginScene;

  public LoginScene(Stage window) {
    this.window = window;

    Pane loginPane = new VBox(LoginScene.createLoginPane());
    loginPane.setId("login-pane");

    loginScene = new Scene(loginPane, 600,600);
    loginScene.getStylesheets().add(getClass().getResource("/loginScene.css").toString());
  }

  public Scene getLoginScene() {
    return loginScene;
  }

  private static TableView<ReceivingOffice> officeTable;
  private static final ObservableList<ReceivingOffice> offices =
          FXCollections.observableArrayList();

  private static TableView<ReceivingOffice> createOfficeTable() {
    TableView<ReceivingOffice> officeTable = new TableView<>();
    officeTable.setId("office-table");
    officeTable.setItems(offices);

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
    offices.add(new ReceivingOffice(512, "Hilfe für die Ukraine", FederalState.CARINTHIA,
            "Spittal/Drau", "Lindenstraße 1", Status.ACTIVE));

    offices.add(new ReceivingOffice(124, "Omas gegen Rechts", FederalState.VIEANNA,
            "Wien", "Porzellangasse", Status.ACTIVE));

    VBox startPane = new VBox(loginInfo, officeTable, startButton);
    startPane.setId("login-screen-start-pane");

    return startPane;
  }

  private static void handleLoginButtonEvent(ActionEvent e) {

    ReceivingOffice selectedOffice = officeTable.getSelectionModel().getSelectedItem();
    if(selectedOffice != null) {
      double x = window.getWidth();
      double y = window.getHeight();

      window.setWidth(x);
      window.setHeight(y);

      MainScene mainScene = new MainScene(window, selectedOffice);
      window.setScene(mainScene.getMainScene());
    }
  }
}
