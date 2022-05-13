package at.fhooe.swe4.administration.scenes;

import at.fhooe.swe4.administration.data.DemandItem;
import at.fhooe.swe4.administration.enums.Category;
import at.fhooe.swe4.administration.enums.Condition;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.Random;

public class ManageDemandDialog {
  private Window owner;
  private Stage dialogStage;
  private ObservableList<DemandItem> demand;
  private TableView<DemandItem> demandTable;
  private Integer curAmountInt = 1;

  private TextField descriptionInput;
  private ChoiceBox conditionInput;
  private HBox amountControl;
  private ChoiceBox categoryInput;

  protected ManageDemandDialog(Window owner, ObservableList<DemandItem> demand, TableView<DemandItem> demandTable) {
    dialogStage = new Stage();
    this.demand = demand;
    this.owner = owner;
    this.demandTable = demandTable;
  }

  private ChoiceBox createConditionDropDown() {
    ChoiceBox conditionDropDown = new ChoiceBox();
    conditionDropDown.getItems().addAll(Condition.values());
    return conditionDropDown;
  }

  private ChoiceBox createCategoryDropDown() {
    ChoiceBox categoryDropDown = new ChoiceBox();
    categoryDropDown.getItems().addAll(Category.values());
    return categoryDropDown;
  }

  private HBox createAmountSelection(Integer startAmount) {
    String startAmountString = startAmount.toString();
    Label curAmount = new Label(startAmountString);

    Button increaseBtn = new Button("mehr");
    increaseBtn.setOnAction((e) -> {
      curAmountInt += 1;
      String temp = curAmountInt.toString();
      curAmount.setText(temp);
    });

    Button decreaseBtn = new Button("weniger");
    decreaseBtn.setOnAction(e -> {
      if(curAmountInt > 1) {
        curAmountInt--;
        String temp = curAmountInt.toString();
        curAmount.setText(temp);
      }
    });

    HBox amountPane = new HBox(curAmount, increaseBtn, decreaseBtn);
    amountPane.setId("demand-amount-selection-pane");

    return amountPane;
  }

  private Button createAddButton() {
    Button addButton = new Button("Bedarf hinzufügen");
    addButton.setId("button-add-demand");
    return addButton;
  }

  private Button createCancelButton() {
    Button cancelButton = new Button("Abbrechen");
    cancelButton.setId("button-cancel");
    cancelButton.setOnAction(e -> {
      dialogStage.close();
    });
    return cancelButton;
  }

  private Button createSaveButton() {
    Button saveButton = new Button("Speichern");
    saveButton.setId(("button-save"));
    saveButton.setOnAction(e->{
      //TODO
    });
    return saveButton;
  }

  private GridPane createInputGrid(Integer startAmount_amountControl) {
    GridPane inputGrid = new GridPane();
    inputGrid.setId("add-demand-input-grid");

    inputGrid.add(new Label("Beschreibung: "),0,0);
    descriptionInput = new TextField();
    inputGrid.add(descriptionInput,1,0);

    inputGrid.add(new Label("Zustand: "),0,1);
    conditionInput = createConditionDropDown();
    inputGrid.add(conditionInput,1,1);

    inputGrid.add(new Label("Menge: "),0,2);
    amountControl = createAmountSelection(startAmount_amountControl);
    inputGrid.add(amountControl, 1,2);

    inputGrid.add(new Label("Kategorie: "),0,3);
    categoryInput = createCategoryDropDown();
    inputGrid.add(categoryInput,1,3);

    return inputGrid;
  }

  private void addDemandDialog() {
    Button addButton = createAddButton();

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(addButton);

    GridPane inputGrid = createInputGrid(1);
    inputGrid.add(buttonBar,0,4,3,1);

    addButton.setOnAction(e-> {
      String desc = descriptionInput.getText();
      Condition cond = (Condition)conditionInput.getValue();
      Category categ = (Category)categoryInput.getValue();

      if(desc.length() > 0 && cond != null && categ != null) {
        Random rand = new Random();
        Integer id = rand.nextInt(10000);
        demand.add(new DemandItem(id,desc, cond, curAmountInt, null, categ));
      }

    });

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/addDemand-dialog.css").toExternalForm());
    sceneSetup(dialogScene, "Bedarfsmeldung hinzufügen");
  }

  private void editDemandDialog(DemandItem selectedItem){
    Button saveButton = createSaveButton();

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(saveButton);

    GridPane inputGrid = createInputGrid(selectedItem.getAmount());

    inputGrid.add(buttonBar,0,4,3,1);

    descriptionInput.setText(selectedItem.getDescription());
    conditionInput.setValue(selectedItem.getCondition());
    curAmountInt = selectedItem.getAmount() ;
    categoryInput.setValue(selectedItem.getCategory());

    saveButton.setOnAction(e -> {
      selectedItem.setDescription(descriptionInput.getText());
      selectedItem.setCondition((Condition)conditionInput.getValue());
      selectedItem.setAmount(curAmountInt);
      selectedItem.setCategory((Category) categoryInput.getValue());
      demandTable.refresh();
    });

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/addDemand-dialog.css").toExternalForm());
    sceneSetup(dialogScene, "Bedarfsmeldung ändern");
  }

  private void sceneSetup(Scene scene, String stageTitle) {
    dialogStage.setScene(scene);
    dialogStage.setTitle(stageTitle);
    dialogStage.setMinWidth(300);
    dialogStage.setMinHeight(300);
    dialogStage.initModality(Modality.WINDOW_MODAL);
    dialogStage.initStyle(StageStyle.UTILITY);
    dialogStage.initOwner(owner);
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
