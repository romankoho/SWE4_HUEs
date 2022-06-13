package at.fhooe.swe4.administration.controller;
//file ManageArticlesDialogController.java

import at.fhooe.swe4.administration.views.ManageArticlesDialog;
import at.fhooe.swe4.model.Article;
import at.fhooe.swe4.model.Model;
import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.Condition;
import javafx.event.ActionEvent;

import java.rmi.RemoteException;
import java.util.Random;

public class ManageArticlesDialogController {
  private ManageArticlesDialog view;
  private Model model;

  public ManageArticlesDialogController(ManageArticlesDialog view, Model model) {
    this.view = view;
    this.model = model;
    setView();
  }

  private void setView() {
    view.getAddArticleBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleAddArticle(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
    view.getEditArticlesDialog().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleEditArticle(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  private void handleEditArticle(ActionEvent e) throws RemoteException {
    Article selectedItem = view.getArticlesTable().getSelectionModel().getSelectedItem();
    model.updateArticle(
            selectedItem, view.getNameInput().getText(),
            view.getDescriptionInput().getText(),(Condition) view.getConditionInput().getValue(),
            (Category) view.getCategoryInput().getValue());

    view.getArticlesTable().refresh();
  }

  private void handleAddArticle(ActionEvent e) throws RemoteException {
    String name = view.getNameInput().getText();
    String desc = view.getDescriptionInput().getText();
    Condition cond = (Condition) view.getConditionInput().getValue();
    Category categ = (Category) view.getCategoryInput().getValue();

    if(name.length() > 0 && desc.length() > 0 && cond != null && categ != null) {
      Random rand = new Random();
      Integer id = rand.nextInt(10000);
      model.addArticle(new Article(id, name, desc, cond, categ));

      view.getNameInput().clear();
      view.getDescriptionInput().clear();
      view.getConditionInput().setValue(null);
      view.getCategoryInput().setValue(null);
    }
  }
}
