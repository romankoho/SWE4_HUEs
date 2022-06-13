package at.fhooe.swe4.administration.controller;
//file ManageDemandDialogController.java

import at.fhooe.swe4.administration.views.ManageDemandDialog;
import at.fhooe.swe4.model.*;
import javafx.event.ActionEvent;

import java.rmi.RemoteException;
import java.util.Random;

public class ManageDemandDialogController {
  private ManageDemandDialog view;
  private Model model;

  public ManageDemandDialogController(ManageDemandDialog view, Model model) throws RemoteException {
    this.view = view;
    this.model = model;
    setView();
  }

  public void setView() throws RemoteException {
    view.getAddDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleAddDemand(e);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
    view.getEditDemandBtn().addEventHandler(ActionEvent.ACTION, (e) -> handleEditDemand(e));
    view.getOfficeDropDown().setValue(model.getActiveOffice());
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

  private void handleAddDemand(ActionEvent e) throws RemoteException {
    Article article = (Article)view.getArticleDropDown().getValue();
    ReceivingOffice office = (ReceivingOffice)view.getOfficeDropDown().getValue();

    String amount = view.getInputAmount().getText();

    try {
      Integer intAmount = Integer.parseInt(amount);
      if(article != null && intAmount > 0) {
        Random rand = new Random();
        Integer id = rand.nextInt(10000);
        model.addDemand(new DemandItem(id, article, office, intAmount));
        view.getInputAmount().setStyle(null);

        view.getInputAmount().clear();
        view.getArticleDropDown().setValue(null);
      }

    } catch(NumberFormatException error) {
      view.getInputAmount().setStyle("-fx-border-color: red; -fx-border-width: 1;");
    }
  }
}
