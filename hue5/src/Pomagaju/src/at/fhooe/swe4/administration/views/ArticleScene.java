package at.fhooe.swe4.administration.views;
//file ArticleScene.java

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.ArticleSceneController;
import at.fhooe.swe4.model.Article;
import at.fhooe.swe4.model.dbMock;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ArticleScene {

  //global scene management
  private  Stage window;
  private final Scene manageArticleScene;
  private  Pane articlesPane;

  //controller for this scene
  ArticleSceneController controller;

  //View nodes
  private Button addArticleBtn;
  private Button deleteArticleBtn;
  private Button editArticleBtn;

  public Button getAddArticleBtn() {return addArticleBtn;}
  public Button getDeleteArticleBtn() {return deleteArticleBtn;}
  private TableView<Article> articleTable;

  //getter
  public Button getEditArticleBtn() {return editArticleBtn;}
  public Stage getWindow() {return window;}

  public TableView<Article> getArticleTable() {return articleTable;}

  public Scene getManageArticleScene() {return this.manageArticleScene;}

  public ArticleScene(Stage window) {
    this.window = window;

    articlesPane = new VBox();
    articlesPane.getChildren().add(Utilities.createMenuBar(window));
    articlesPane.getChildren().add(createMainPane());
    articlesPane.setId("articles-pane");

    manageArticleScene = new Scene(articlesPane, 600,600);
    manageArticleScene.getStylesheets().add(getClass().getResource("/administration.css").toString());

    controller = new ArticleSceneController(this);
  }

  private TableView<Article> createArticleTable() {
    TableView<Article> articleTable = new TableView<>();
    articleTable.setId("article-table");
    articleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    articleTable.setItems(dbMock.getInstance().getArticles());

    TableColumn<Article, Integer> idCol = new TableColumn<>("ID");
    TableColumn<Article, String> nameCol = new TableColumn<>("Name");
    TableColumn<Article, String> descCol = new TableColumn<>("Beschreibung");
    TableColumn<Article, String> condCol = new TableColumn<>("Zustand");
    TableColumn<Article, String> catCol = new TableColumn<>("Kategorie");

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    condCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
    catCol.setCellValueFactory(new PropertyValueFactory<>("category"));

    idCol.setMinWidth(40);
    nameCol.setMinWidth(80);
    descCol.setMinWidth(120);
    condCol.setMinWidth(80);
    catCol.setMinWidth(80);

    articleTable.getColumns().add(idCol);
    articleTable.getColumns().add(nameCol);
    articleTable.getColumns().add(descCol);
    articleTable.getColumns().add(condCol);
    articleTable.getColumns().add(catCol);

    return articleTable;
  }

  protected Pane createMainPane() {
    Label articlesHL = new Label("Übersicht über Hilfsgüter");
    articlesHL.setId("headline");
    articleTable = createArticleTable();

    addArticleBtn = Utilities.createTextButton("add-article", "+");
    addArticleBtn.setId("standard-button");
    deleteArticleBtn = Utilities.createTextButton("delete-article", "löschen");
    deleteArticleBtn.setId("standard-button");
    editArticleBtn = Utilities.createTextButton("edit-article", "ändern");
    editArticleBtn.setId("standard-button");

    HBox articleButtonPane = new HBox(addArticleBtn, deleteArticleBtn, editArticleBtn);
    articleButtonPane.setId("article-buttons-pane");

    VBox articlesPane = new VBox(articlesHL, articleTable, articleButtonPane);
    articlesPane.setId("articles-pane-content");

    return articlesPane;
  }
}
