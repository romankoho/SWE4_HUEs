package at.fhooe.swe4.administration;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Administration extends Application {

  private Stage window;
  private Scene loginScene, mainScene;

/*  private Button createIconButton(String id) {
    Button button = new Button();
    button.setId(id);
    return button;
  }*/

  private Button createTextButton(String id, String caption) {
    Button button = new Button(caption);
    button.setId(id);
    button.setPadding(new Insets(5));
    button.setPrefSize(50,40);
    button.setMinSize(50,40);
    return button;
  }

  private Pane createLoginPane(){
    Text loginInfo = new Text("Bitte geben Sie die Annahmestelle ein, welcher Sie zugeordnet sind.");
    loginInfo.setId("login-pane-info-text");
    loginInfo.setWrappingWidth(300);

    Label inputLabel = new Label("Meine Annahmestelle: ");
    TextField inputField = new TextField();
    Button startButton = createTextButton("button-login", "Login");
    startButton.addEventHandler(ActionEvent.ACTION, (e) -> handleLoginButtonEvent(e));

    HBox inputPane = new HBox(inputLabel, inputField);
    inputPane.setId("start-screen-input-pane");

    VBox startPane = new VBox();
    startPane.setId("start-screen-start-pane");

    startPane.getChildren().addAll(loginInfo, inputPane, startButton);

    return startPane;
  }

  private void handleLoginButtonEvent(ActionEvent e) {
    double x = window.getWidth();
    double y = window.getHeight();

    window.setWidth(x);
    window.setHeight(y);
    window.setScene(mainScene);
  }


  private TableView<DemandItem> demandTable;
  private ObservableList<DemandItem> demand =
          FXCollections.observableArrayList();

  private TableView<DemandItem> createDemandTable() {
    TableView<DemandItem> demandTable = new TableView<>();
    demandTable.setId("demand-table");
    demandTable.setItems(demand);

    TableColumn<DemandItem, Integer> column1 = new TableColumn<>("ID");
    TableColumn<DemandItem, String> column2 = new TableColumn<>("Beschreibung");
    TableColumn<DemandItem, String> column3 = new TableColumn<>("Zustand");
    TableColumn<DemandItem, Integer> column4 = new TableColumn<>("Menge");

    column1.setCellValueFactory(new PropertyValueFactory<DemandItem, Integer>("id"));
    column2.setCellValueFactory(new PropertyValueFactory<DemandItem, String>("description"));
    column3.setCellValueFactory(new PropertyValueFactory<DemandItem, String>("condition"));
    column4.setCellValueFactory(new PropertyValueFactory<DemandItem, Integer>("amount"));

    column1.setMinWidth(20);
    column2.setMinWidth(160);
    column3.setMinWidth(40);
    column4.setMinWidth(20);

    demandTable.getColumns().add(column1);
    demandTable.getColumns().add(column2);
    demandTable.getColumns().add(column3);
    demandTable.getColumns().add(column4);

    return demandTable;
  }

  private Pane createMainPane() {
    Label headline = new Label("Bedarfsübersicht");
    headline.setId("headline");
    demandTable = createDemandTable();
    demand.add(new DemandItem(1,"Das ist eine Beschreibung", Condition.SLIGHTLYUSED.getCondition(),10, null, null));

    Button addDemand = createTextButton("add-demand", "+");
    addDemand.setId("standard-button");
    Button deleteDemand = createTextButton("delete-demand", "löschen");
    deleteDemand.setId("standard-button");
    Button editDemand = createTextButton("edit-demand", "ändern");
    editDemand.setId("standard-button");

    addDemand.addEventHandler(ActionEvent.ACTION, (e) -> handleAddDemandEvent(e));
    deleteDemand.addEventHandler(ActionEvent.ACTION, (e) -> handleDeleteDemandEvent(e));

    HBox demandButtonsPane = new HBox(addDemand, deleteDemand, editDemand);
    demandButtonsPane.setId("demand-buttons-pane");

    VBox mainPane = new VBox(createMenuBar(window), headline, demandTable, demandButtonsPane);      //da gehören die anderen Bestandteile noch rein
    return mainPane;
  }

  private void handleDeleteDemandEvent(ActionEvent e) {
    DemandItem demandI = demandTable.getSelectionModel().getSelectedItem();
    if (demandI != null) {
      demand.remove(demandI);
    }
  }



  private void handleAddDemandEvent(ActionEvent e) {
    AddDemandDialog dialog = new AddDemandDialog(window, demand);
    dialog.show();
  }

  private MenuBar createMenuBar(Stage stage) {
    MenuItem manageReceivingOffices = new MenuItem("Annahmestellen verwalten");
    Menu adminMenu = new Menu("Verwaltung");
    adminMenu.getItems().add((manageReceivingOffices));

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().add(adminMenu);
    return menuBar;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;

    Pane loginPane = new VBox(createLoginPane());
    loginPane.setId("login-pane");

    Pane mainPane = new VBox(createMainPane());
    mainPane.setId("main-pane");

    loginScene = new Scene(loginPane, 600,600);
    loginScene.getStylesheets().add(getClass().getResource("/administration.css").toString());

    mainScene = new Scene(mainPane, 600,600);
    mainScene.getStylesheets().add(getClass().getResource("/administration.css").toString());

    window.setScene(loginScene);
    window.setMinWidth(400);
    window.setMinHeight(400);

    window.setTitle("Pomagaju Annahmestellen Verwaltungsservice");
    window.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
