package at.fhooe.swe4.administration.views;
//file ManageArticlesDialog.java

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.ManageArticlesDialogController;
import at.fhooe.swe4.model.Article;
import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.Condition;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ManageArticlesDialog {
  private Window owner;
  private Stage dialogStage;

  private ManageArticlesDialogController controller;

  private TableView<Article> articlesTable;
  private TextField nameInput;
  private TextArea descriptionInput;
  private ChoiceBox conditionInput;
  private ChoiceBox categoryInput;

  Button addArticleBtn = new Button("Hilfsgut hinzufügen");
  Button editArticlesDialog = new Button("Speichern");

  public TextField getNameInput() {return nameInput;}
  public TextArea getDescriptionInput() {return descriptionInput;}
  public ChoiceBox getConditionInput() {return conditionInput;}
  public ChoiceBox getCategoryInput() {return categoryInput;}

  public Button getAddArticleBtn() {return addArticleBtn;}
  public Button getEditArticlesDialog() {return editArticlesDialog;}

  public TableView<Article> getArticlesTable() {return articlesTable;}

  public ManageArticlesDialog(Window owner, TableView<Article> articlesTable) {
    dialogStage = new Stage();
    this.owner = owner;
    this.articlesTable = articlesTable;
  }

  private GridPane createInputGrid() {
    GridPane inputGrid = new GridPane();
    inputGrid.setId("article-input-grid");

    inputGrid.add(new Label("Name: "),0,0);
    nameInput = new TextField();
    inputGrid.add(nameInput,1,0);

    inputGrid.add(new Label("Beschreibung: "),0,1);
    descriptionInput = new TextArea();
    inputGrid.add(descriptionInput,1,1);

    inputGrid.add(new Label("Zustand: "),0,2);
    conditionInput = new ChoiceBox();
    conditionInput.getItems().addAll(Condition.values());
    inputGrid.add(conditionInput,1,2);

    inputGrid.add(new Label("Kategorie: "),0,3);
    categoryInput = new ChoiceBox();
    categoryInput.getItems().addAll(Category.values());
    inputGrid.add(categoryInput,1,3);

    return inputGrid;
  }

  private void addArticleDialog() {
    addArticleBtn.setId("button-add-article");

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(addArticleBtn);

    GridPane inputGrid = createInputGrid();
    inputGrid.add(buttonBar,0,4,3,1);

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Hilfsgut hinzufügen");
    controller = new ManageArticlesDialogController(this);
  }

  private void editArticlesDialog(Article selectedItem){
    editArticlesDialog.setId(("button-save"));

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().add(editArticlesDialog);

    GridPane inputGrid = createInputGrid();
    inputGrid.add(buttonBar,0,4,3,1);

    nameInput.setText(selectedItem.getName());
    descriptionInput.setText(selectedItem.getDescription());
    conditionInput.setValue(selectedItem.getCondition());
    categoryInput.setValue(selectedItem.getCategory());

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/administration.css").toExternalForm());
    Utilities.sceneSetup(owner, dialogStage, dialogScene, "Hilfsgut verwalten");
    controller = new ManageArticlesDialogController(this);
  }

  public void showAddDialog() {
    this.addArticleDialog();
    dialogStage.show();
  }

  public void showEditDialog(Article selectedItem) {
    this.editArticlesDialog(selectedItem);
    dialogStage.show();
  }

}
