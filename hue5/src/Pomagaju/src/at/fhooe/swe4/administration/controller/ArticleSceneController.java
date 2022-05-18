package at.fhooe.swe4.administration.controller;

import at.fhooe.swe4.administration.views.ArticleScene;
import at.fhooe.swe4.administration.views.ManageArticlesDialog;
import at.fhooe.swe4.model.Article;
import at.fhooe.swe4.model.dbMock;
import javafx.event.ActionEvent;

public class ArticleSceneController {
  private ArticleScene view;

  public ArticleSceneController(ArticleScene view) {
    this.view = view;
    setView();
  }

  private void setView() {
    view.getAddArticleBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleAddArticleEvent(e));
    view.getDeleteArticleBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleDeleteArticleEvent(e));
    view.getEditArticleBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleEditArticleEvent(e));
  }

  private void handleAddArticleEvent(ActionEvent e) {
    ManageArticlesDialog dialog = new ManageArticlesDialog(view.getWindow(), view.getArticleTable());
    dialog.showAddDialog();
  }

  private void handleEditArticleEvent(ActionEvent e) {
    Article selectedArticle = view.getArticleTable().getSelectionModel().getSelectedItem();

    if (selectedArticle != null) {
      ManageArticlesDialog dialog = new ManageArticlesDialog(view.getWindow(), view.getArticleTable());
      dialog.showEditDialog(selectedArticle);
    }
  }

  private void handleDeleteArticleEvent(ActionEvent e) {
    Article selectedArticle = view.getArticleTable().getSelectionModel().getSelectedItem();
    if (selectedArticle != null) {
      dbMock.getInstance().deleteArticle(selectedArticle);
    }
  }

}
