package at.fhooe.swe4.administration.views;
//file ManageDemandDialog.java

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.ManageDemandDialogController;
import at.fhooe.swe4.model.DemandItem;
import at.fhooe.swe4.model.dbMock;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ManageDemandDialog {
  private Window owner;
  private Stage dialogStage;

  private ManageDemandDialogController dialogController;

  private ChoiceBox articleDropDown;
  private ChoiceBox officeDropDown;
  private TextField inputAmount;
  private TableView<DemandItem> demandTable;

  private Button addDemandBtn = new Button("Bedarf hinzufügen");;
  private Button editDemandBtn = new Button("Speichern");

  public Button getAddDemandBtn() {return addDemandBtn;}
  public ChoiceBox getArticleDropDown() {return articleDropDown;}
  public TextField getInputAmount() {return inputAmount;}
  public ChoiceBox getOfficeDropDown() {return officeDropDown;}
  public Button getEditDemandBtn() {return editDemandBtn;}

  public TableView<DemandItem> getDemandTable() {return demandTable;}

  public ManageDemandDialog(Window owner, TableView<DemandItem> demandTable) {
    dialogStage = new Stage();
    this.owner = owner;
    this.demandTable = demandTable;
  }

    private GridPane createInputGrid() {
    GridPane inputGrid = new GridPane();
    inputGrid.setId("add-demand-input-grid");

    inputGrid.add(new Label("Benötigtes Spendengut: "),0,0);
    articleDropDown = new ChoiceBox();
    articleDropDown.getItems().addAll(dbMock.getInstance().getArticles());
    inputGrid.add(articleDropDown,1,0);

    inputGrid.add(new Label("Menge: "),0,1);
    inputAmount = new TextField();
    inputAmount.setId("manage-demand-input-amount");
    inputGrid.add(inputAmount, 1,1);

    inputGrid.add(new Label("zuständige Annahmestelle: "),0,2);
    officeDropDown = new ChoiceBox();
    officeDropDown.getItems().addAll(dbMock.getInstance().getOffices());
    officeDropDown.setValue(dbMock.getInstance().getActiveOffice());
    inputGrid.add(officeDropDown,1,2);

    return inputGrid;
  }

  private void addDemandDialog() {
    addDemandBtn.setId("button-add-demand");

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(addDemandBtn);

    GridPane inputGrid = createInputGrid();
    inputGrid.add(buttonBar,0,4,3,1);

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Bedarfsmeldung hinzufügen");
    dialogController = new ManageDemandDialogController(this);
  }

  private void editDemandDialog(DemandItem selectedItem){
    editDemandBtn.setId(("button-save"));

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(editDemandBtn);

    GridPane inputGrid = createInputGrid();
    inputGrid.add(buttonBar,0,4,3,1);

    articleDropDown.setValue(selectedItem.getRelatedArticle());
    inputAmount.setText(selectedItem.getAmount().toString());
    officeDropDown.setValue(selectedItem.getRelatedOffice());

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Bedarfsmeldung ändern");
    dialogController = new ManageDemandDialogController(this);
  }

  public void showAddDialog() {
    this.addDemandDialog();
    dialogStage.show();
  }

  public void showEditDialog(DemandItem selectedItem) {
    this.editDemandDialog(selectedItem);
    dialogStage.show();
  }
}
