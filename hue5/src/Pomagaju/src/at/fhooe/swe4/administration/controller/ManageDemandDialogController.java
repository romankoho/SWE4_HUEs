package at.fhooe.swe4.administration.controller;
//file ManageDemandDialogController.java

import at.fhooe.swe4.administration.views.ManageDemandDialog;
import at.fhooe.swe4.model.Article;
import at.fhooe.swe4.model.DemandItem;
import at.fhooe.swe4.model.ReceivingOffice;
import at.fhooe.swe4.model.dbMock;
import javafx.event.ActionEvent;

import java.util.Random;

public class ManageDemandDialogController {
  private ManageDemandDialog view;

  public ManageDemandDialogController(ManageDemandDialog view) {
    this.view = view;
    setView();
  }

  public void setView() {
    view.getAddDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleAddDemand(e));
    view.getEditDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleEditDemand(e));
  }

  private void handleEditDemand(ActionEvent e) {
    DemandItem selectedItem = view.getDemandTable().getSelectionModel().getSelectedItem();

    String amount = view.getInputAmount().getText();
    Integer intAmount = Integer.parseInt(amount);

    selectedItem.setRelatedArticle((Article) view.getArticleDropDown().getValue());
    selectedItem.setAmount(intAmount);
    selectedItem.setRelatedOffice((ReceivingOffice)view.getOfficeDropDown().getValue());
    view.getDemandTable().refresh();
  }

  private void handleAddDemand(ActionEvent e) {
    Article article = (Article)view.getArticleDropDown().getValue();
    ReceivingOffice office = (ReceivingOffice)view.getOfficeDropDown().getValue();

    String amount = view.getInputAmount().getText();

    try {
      Integer intAmount = Integer.parseInt(amount);
      if(article != null && intAmount > 0) {
        Random rand = new Random();
        Integer id = rand.nextInt(10000);
        dbMock.getInstance().addDemand(new DemandItem(id, article, office, intAmount));
        view.getInputAmount().setStyle(null);

        view.getInputAmount().clear();
        view.getArticleDropDown().setValue(null);
      }

    } catch(NumberFormatException error) {
      view.getInputAmount().setStyle("-fx-border-color: red; -fx-border-width: 1;");
    }
  }
}
