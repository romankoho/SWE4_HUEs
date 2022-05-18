package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.ArticleController;
import at.fhooe.swe4.administration.controller.DemandController;
import at.fhooe.swe4.administration.controller.OfficesController;
import at.fhooe.swe4.administration.models.Article;
import at.fhooe.swe4.administration.models.DemandItem;
import at.fhooe.swe4.administration.models.ReceivingOffice;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Random;

public class ManageDemandDialog {
  private Window owner;
  private Stage dialogStage;
  private Integer curAmountInt = 1;

  private ChoiceBox articleDropDown;
  private ChoiceBox officeDropDown;
  private HBox amountControl;
  private TableView<DemandItem> demandTable;

  public ManageDemandDialog(Window owner, TableView<DemandItem> demandTable) {
    dialogStage = new Stage();
    this.owner = owner;
    this.demandTable = demandTable;
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

    private GridPane createInputGrid(Integer startAmount_amountControl) {
    GridPane inputGrid = new GridPane();
    inputGrid.setId("add-demand-input-grid");

    inputGrid.add(new Label("Benötigtes Spendengut: "),0,0);
    articleDropDown = new ChoiceBox();
    articleDropDown.getItems().addAll(ArticleController.getInstance().getArticles());
    inputGrid.add(articleDropDown,1,0);

    inputGrid.add(new Label("Menge: "),0,1);
    amountControl = createAmountSelection(startAmount_amountControl);
    inputGrid.add(amountControl, 1,1);

    inputGrid.add(new Label("zuständige Annahmestelle: "),0,2);
    officeDropDown = new ChoiceBox();
    officeDropDown.getItems().addAll(OfficesController.getInstance().getOffices());
    officeDropDown.setValue(OfficesController.getInstance().getActiveOffice());
    inputGrid.add(officeDropDown,1,2);

    return inputGrid;
  }

  private void addDemandDialog() {
    Button addButton = new Button("Bedarf hinzufügen");
    addButton.setId("button-add-demand");

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(addButton);

    GridPane inputGrid = createInputGrid(1);
    inputGrid.add(buttonBar,0,4,3,1);

    addButton.setOnAction(e-> {
      Article article = (Article)articleDropDown.getValue();
      ReceivingOffice office = (ReceivingOffice)officeDropDown.getValue();

      if(article != null && curAmountInt > 0) {
        Random rand = new Random();
        Integer id = rand.nextInt(10000);
        DemandController.getInstance().addDemand(new DemandItem(id, article, office, curAmountInt));
      }
    });

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Bedarfsmeldung hinzufügen");
  }

  private void editDemandDialog(DemandItem selectedItem){
    Button saveButton = new Button("Speichern");
    saveButton.setId(("button-save"));

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(saveButton);

    GridPane inputGrid = createInputGrid(selectedItem.getAmount());

    inputGrid.add(buttonBar,0,4,3,1);

    articleDropDown.setValue(selectedItem.getRelatedArticle());
    curAmountInt = selectedItem.getAmount() ;
    officeDropDown.setValue(selectedItem.getRelatedOffice());

    saveButton.setOnAction(e -> {
      selectedItem.setRelatedArticle((Article) articleDropDown.getValue());
      selectedItem.setAmount(curAmountInt);
      selectedItem.setRelatedOffice((ReceivingOffice)officeDropDown.getValue());
      DemandController.getInstance().getFilteredDemand().setPredicate(
              demandItem -> demandItem.getRelatedOffice().equals(OfficesController.getInstance().getActiveOffice())
      );
      demandTable.refresh();
    });

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Bedarfsmeldung ändern");
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
