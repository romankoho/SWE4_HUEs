package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.administration.controller.ArticleController;
import at.fhooe.swe4.administration.models.Article;
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

public class ManageArticlesDialog {
  private Window owner;
  private Stage dialogStage;
  private TableView<Article> articleTable;

  private TextField nameInput;
  private TextArea descriptionInput;
  private ChoiceBox conditionInput;
  private ChoiceBox categoryInput;

  protected ManageArticlesDialog(Window owner, TableView<Article> articleTable) {
    dialogStage = new Stage();
    this.owner = owner;
    this.articleTable = articleTable;
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

  private GridPane createInputGrid() {
    GridPane inputGrid = new GridPane();
    inputGrid.setId("add-category-input-grid");

    inputGrid.add(new Label("Name: "),0,0);
    nameInput = new TextField();
    inputGrid.add(nameInput,1,0);

    inputGrid.add(new Label("Beschreibung: "),0,1);
    descriptionInput = new TextArea();
    inputGrid.add(descriptionInput,1,1);

    inputGrid.add(new Label("Zustand: "),0,2);
    conditionInput = createConditionDropDown();
    inputGrid.add(conditionInput,1,2);

    inputGrid.add(new Label("Kategorie: "),0,3);
    categoryInput = createCategoryDropDown();
    inputGrid.add(categoryInput,1,3);

    return inputGrid;
  }

  private void addArticleDialog() {
    Button addButton = new Button("Hilfsgut hinzufügen");
    addButton.setId("button-add-article");

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(addButton);

    GridPane inputGrid = createInputGrid();
    inputGrid.add(buttonBar,0,4,3,1);

    addButton.setOnAction(e-> {
      String name = nameInput.getText();
      String desc = descriptionInput.getText();
      Condition cond = (Condition)conditionInput.getValue();
      Category categ = (Category)categoryInput.getValue();

      if(name.length() > 0 && desc.length() > 0 && cond != null && categ != null) {
        Random rand = new Random();
        Integer id = rand.nextInt(10000);
        ArticleController.getInstance().addArticle(new Article(id, name, desc, cond, categ));

        nameInput.clear();
        descriptionInput.clear();
        conditionInput.setValue(null);
        categoryInput.setValue(null);
      }
    });

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/addDemand-dialog.css").toExternalForm());
    sceneSetup(dialogScene, "Hilfsgut hinzufügen");
  }

  private void editDemandDialog(Article selectedItem){
    Button saveButton = new Button("Speichern");
    saveButton.setId(("button-save"));

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(saveButton);

    GridPane inputGrid = createInputGrid();

    inputGrid.add(buttonBar,0,4,3,1);

    nameInput.setText(selectedItem.getName());
    descriptionInput.setText(selectedItem.getDescription());
    conditionInput.setValue(selectedItem.getCondition());
    categoryInput.setValue(selectedItem.getCategory());

    saveButton.setOnAction(e -> {
      ArticleController.getInstance().updateArticle(
              selectedItem, descriptionInput.getText(),
              descriptionInput.getText(),(Condition)conditionInput.getValue(),
              (Category) categoryInput.getValue()); articleTable.refresh();
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
    this.addArticleDialog();
    dialogStage.show();
  }

  public void showEditDialog(Article selectedItem) {
    this.editDemandDialog(selectedItem);
    dialogStage.show();
  }

}
