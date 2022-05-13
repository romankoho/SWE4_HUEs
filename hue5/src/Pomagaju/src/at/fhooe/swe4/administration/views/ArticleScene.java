package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.administration.Utilities;
import at.fhooe.swe4.administration.controller.ArticleController;
import at.fhooe.swe4.administration.models.Article;
import at.fhooe.swe4.administration.enums.Category;
import at.fhooe.swe4.administration.enums.Condition;
import javafx.event.ActionEvent;
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

  private static Stage window;
  private final Scene manageArticleScene;
  private static TableView<Article> articleTable;

  public Scene getManageArticleScene() {return this.manageArticleScene;}

  public ArticleScene(Stage window) {
    this.window = window;

    Pane mainPane = new VBox(ArticleScene.createScene());
    mainPane.setId("manageArticles-pane");

    manageArticleScene = new Scene(mainPane, 600,600);
    manageArticleScene.getStylesheets().add(LoginScene.class.getResource("/mainScene.css").toString());
  }

  private static TableView<Article> createArticleTable() {
    TableView<Article> articleTable = new TableView<>();
    articleTable.setId("article-table");
    articleTable.setItems(ArticleController.getInstance().getArrayList());

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

  protected static Pane createScene() {
    Label articlesHL = new Label("Übersicht über Hilfsgüter");
    articlesHL.setId("headline");
    articleTable = createArticleTable();

    Button addArticle = Utilities.createTextButton("add-article", "+");
    addArticle.setId("standard-button");
    Button deleteArticle = Utilities.createTextButton("delete-article", "löschen");
    deleteArticle.setId("standard-button");
    Button editArticle = Utilities.createTextButton("edit-article", "ändern");
    editArticle.setId("standard-button");

    addArticle.addEventHandler(ActionEvent.ACTION, (e) -> handleAddArticleEvent(e));
    editArticle.addEventHandler(ActionEvent.ACTION, (e) -> handleEditArticleEvent(e));
    deleteArticle.addEventHandler(ActionEvent.ACTION, (e) -> handleDeleteArticleEvent(e));

    HBox articleButtonPane = new HBox(addArticle, deleteArticle, editArticle);
    articleButtonPane.setId("article-buttons-pane");

    VBox mainPane = new VBox(Utilities.createMenuBar(window), articlesHL, articleTable, articleButtonPane);
    return mainPane;
  }

  private static void handleAddArticleEvent(ActionEvent e) {
    ManageArticlesDialog dialog = new ManageArticlesDialog(window, articleTable);
    dialog.showAddDialog();
  }

  private static void handleEditArticleEvent(ActionEvent e) {
    Article selectedArticle = articleTable.getSelectionModel().getSelectedItem();
    if (selectedArticle != null) {
      ManageArticlesDialog dialog = new ManageArticlesDialog(window, articleTable);
      dialog.showEditDialog(selectedArticle);
    }
  }

  private static void handleDeleteArticleEvent(ActionEvent e) {
    Article article = articleTable.getSelectionModel().getSelectedItem();
    if (article != null) {
      ArticleController.getInstance().deleteArticle(article);
    }
  }
}
