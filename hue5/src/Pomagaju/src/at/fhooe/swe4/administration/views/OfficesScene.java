package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.OfficesSceneController;
import at.fhooe.swe4.model.ReceivingOffice;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OfficesScene {

  private Stage window;
  private final Scene manageOfficesScene;
  private Pane mainPane;

  //controller for scene
  private OfficesSceneController controller;

  private TableView<ReceivingOffice> officesTable;
  private Button addOfficeBtn;
  private Button editOfficeBtn;
  private Button deleteOfficeBtn;

  public Stage getWindow() {return window;}
  public Button getAddOfficeBtn() {return addOfficeBtn;}
  public Button getEditOfficeBtn() {return editOfficeBtn;}
  public Button getDeleteOfficeBtn() {return deleteOfficeBtn;}
  public TableView<ReceivingOffice> getOfficesTable() {return officesTable;}

  public Scene getOfficesScene() {return this.manageOfficesScene;}

  public OfficesScene(Stage window) {
    this.window = window;

    mainPane = new VBox();
    mainPane.getChildren().add(Utilities.createMenuBar(window));
    mainPane.getChildren().add(createMainPane());
    mainPane.setId("offices-pane");

    manageOfficesScene = new Scene(mainPane, 600,600);
    manageOfficesScene.getStylesheets().add(getClass().getResource("/administration.css").toString());
    controller = new OfficesSceneController(this);
  }

  protected Pane createMainPane() {
    Label articlesHL = new Label("Übersicht über Annahmestellen");
    articlesHL.setId("headline");
    officesTable = Utilities.createOfficesTable();

    addOfficeBtn = Utilities.createTextButton("add-office", "+");
    addOfficeBtn.setId("standard-button");
    editOfficeBtn = Utilities.createTextButton("edit-office", "ändern");
    editOfficeBtn.setId("standard-button");
    deleteOfficeBtn = Utilities.createTextButton("delete-office", "löschen");
    deleteOfficeBtn.setId("standard-button");

    HBox officesButtonPane = new HBox(addOfficeBtn, deleteOfficeBtn, editOfficeBtn);
    officesButtonPane.setId("offices-buttons-pane");

    VBox articlesPane = new VBox(articlesHL, officesTable, officesButtonPane);
    articlesPane.setId("offices-pane-content");

    return articlesPane;
  }
}
