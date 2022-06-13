package at.fhooe.swe4.administration.controller;
//file ArticleSceneController.java

import at.fhooe.swe4.administration.views.ArticleScene;
import at.fhooe.swe4.administration.views.ManageArticlesDialog;
import at.fhooe.swe4.model.Article;
import at.fhooe.swe4.model.Model;
import javafx.event.ActionEvent;

import java.rmi.RemoteException;

public class ArticleSceneController {
  private ArticleScene view;
  private Model model;

  public ArticleSceneController(ArticleScene view, Model model) throws RemoteException {
    this.view = view;
    this.model = model;
    setView();
  }

  private void setView() throws RemoteException {
    view.getArticleTable().setItems(model.getArticles());
    view.getAddArticleBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleAddArticleEvent(e));
    view.getDeleteArticleBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleDeleteArticleEvent(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
    view.getEditArticleBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleEditArticleEvent(e));
  }

  private void handleAddArticleEvent(ActionEvent e) {
    ManageArticlesDialog dialog = new ManageArticlesDialog(view.getWindow(), view.getArticleTable(), model);
    dialog.showAddDialog();
  }

  private void handleEditArticleEvent(ActionEvent e) {
    Article selectedArticle = view.getArticleTable().getSelectionModel().getSelectedItem();

    if (selectedArticle != null) {
      ManageArticlesDialog dialog = new ManageArticlesDialog(view.getWindow(), view.getArticleTable(), model);
      dialog.showEditDialog(selectedArticle);
    }
  }

  private void handleDeleteArticleEvent(ActionEvent e) throws RemoteException {
    Article selectedArticle = view.getArticleTable().getSelectionModel().getSelectedItem();
    if (selectedArticle != null) {
      model.deleteArticle(selectedArticle);
    }
  }

}
