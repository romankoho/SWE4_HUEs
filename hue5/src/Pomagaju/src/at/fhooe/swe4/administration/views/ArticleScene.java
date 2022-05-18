package at.fhooe.swe4.administration.views;

import at.fhooe.swe4.Utilities;
import at.fhooe.swe4.administration.controller.ArticleController;
import at.fhooe.swe4.administration.models.Article;
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

  private  Stage window;
  private final Scene manageArticleScene;
  private  Pane mainPane;

  protected TableView<Article> articleTable;

  public Scene getManageArticleScene() {return this.manageArticleScene;}

  public ArticleScene(Stage window) {
    this.window = window;

    mainPane = new VBox();
    mainPane.getChildren().add(Utilities.createMenuBar(window));
    mainPane.getChildren().add(createMainPane());
    mainPane.setId("articles-pane");

    manageArticleScene = new Scene(mainPane, 600,600);
    manageArticleScene.getStylesheets().add(getClass().getResource("/administration.css").toString());
  }

  private TableView<Article> createArticleTable() {
    TableView<Article> articleTable = new TableView<>();
    articleTable.setId("article-table");
    articleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    articleTable.setItems(ArticleController.getInstance().getArticles());

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

    VBox articlesPane = new VBox(articlesHL, articleTable, articleButtonPane);
    articlesPane.setId("articles-pane-content");

    return articlesPane;
  }

  private void handleAddArticleEvent(ActionEvent e) {
    ManageArticlesDialog dialog = new ManageArticlesDialog(window, articleTable);
    dialog.showAddDialog();
  }

  private void handleEditArticleEvent(ActionEvent e) {
    Article selectedArticle = articleTable.getSelectionModel().getSelectedItem();
    if (selectedArticle != null) {
      ManageArticlesDialog dialog = new ManageArticlesDialog(window, articleTable);
      dialog.showEditDialog(selectedArticle);
    }
  }

  private void handleDeleteArticleEvent(ActionEvent e) {
    Article article = articleTable.getSelectionModel().getSelectedItem();
    if (article != null) {
      ArticleController.getInstance().deleteArticle(article);
    }
  }
}
