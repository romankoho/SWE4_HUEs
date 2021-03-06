package at.fhooe.swe4;
//File: Utilities.java

import at.fhooe.swe4.model.Model;
import at.fhooe.swe4.model.ReceivingOffice;
import at.fhooe.swe4.administration.views.ArticleScene;
import at.fhooe.swe4.administration.views.DemandScene;
import at.fhooe.swe4.administration.views.OfficesScene;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.rmi.RemoteException;

public class Utilities {

  public static Button createTextButton(String id, String caption) {
    Button button = new Button(caption);
    button.setId(id);
    button.setPadding(new Insets(5));
    button.setPrefSize(50,40);
    button.setMinSize(50,40);
    return button;
  }

  public static MenuBar createMenuBar(Stage window, Model model) {
    MenuItem manageDemand = new MenuItem("Bedarf verwalten");
    MenuItem manageReceivingOffices = new MenuItem("Annahmestellen verwalten");
    MenuItem manageArticles = new MenuItem("Hilfsgüter verwalten");
    MenuItem updateData = new MenuItem("Daten updaten");
    Menu adminMenu = new Menu("Verwaltung");
    adminMenu.getItems().addAll(manageDemand, manageReceivingOffices, manageArticles, updateData);

    manageArticles.addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleManageArticlesEvent(e, window, model);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
    manageDemand.addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleManageDemandEvent(e, window, model);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
    manageReceivingOffices.addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        handleManageOfficesEvent(e, window, model);
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });
    updateData.addEventHandler(ActionEvent.ACTION, (e) -> {
      try {
        model.updateModel();
      } catch (RemoteException ex) {
        throw new RuntimeException(ex);
      }
    });

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().add(adminMenu);
    return menuBar;
  }

  private static void handleManageOfficesEvent(ActionEvent e, Stage window, Model model) throws RemoteException {
    double x = window.getWidth();
    double y = window.getHeight();
    window.setWidth(x);
    window.setHeight(y);
    OfficesScene officesScene = new OfficesScene(window, model);
    window.setScene(officesScene.getOfficesScene());
  }

  private static void handleManageDemandEvent(ActionEvent e, Stage window, Model model) throws RemoteException {
    double x = window.getWidth();
    double y = window.getHeight();
    window.setWidth(x);
    window.setHeight(y);
    DemandScene demandScene = new DemandScene(window, model);
    window.setScene(demandScene.getMainScene());
  }

  private static void handleManageArticlesEvent(ActionEvent e, Stage window, Model model) throws RemoteException {
    double x = window.getWidth();
    double y = window.getHeight();

    window.setWidth(x);
    window.setHeight(y);

    ArticleScene articleScene = new ArticleScene(window, model);
    window.setScene(articleScene.getManageArticleScene());
  }

  public static void sceneSetup(Window owner, Stage stage, Scene scene, String stageTitle) {
    stage.setScene(scene);
    stage.setTitle(stageTitle);
    stage.setMinWidth(300);
    stage.setMinHeight(300);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initStyle(StageStyle.UTILITY);
    stage.initOwner(owner);
  }

  public static TableView<ReceivingOffice> createOfficesTable() {
    TableView<ReceivingOffice> officesTable = new TableView<>();
    officesTable.setId("offices-table");
    officesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<ReceivingOffice, Integer> idCol = new TableColumn<>("ID");
    TableColumn<ReceivingOffice, String> nameCol = new TableColumn<>("Name");
    TableColumn<ReceivingOffice, String> fedStateCol = new TableColumn<>("Bundesland");
    TableColumn<ReceivingOffice, String> distCol = new TableColumn<>("Bezirk");
    TableColumn<ReceivingOffice, String> addCol = new TableColumn<>("Adresse");
    TableColumn<ReceivingOffice, String> statCol = new TableColumn<>("Status");

    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    fedStateCol.setCellValueFactory(new PropertyValueFactory<>("federalState"));
    distCol.setCellValueFactory(new PropertyValueFactory<>("district"));
    addCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    statCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    idCol.setMinWidth(40);
    nameCol.setMinWidth(150);
    fedStateCol.setMinWidth(80);
    distCol.setMinWidth(80);
    addCol.setMinWidth(150);
    statCol.setMinWidth(80);

    officesTable.getColumns().add(idCol);
    officesTable.getColumns().add(nameCol);
    officesTable.getColumns().add(addCol);
    officesTable.getColumns().add(fedStateCol);
    officesTable.getColumns().add(distCol);
    officesTable.getColumns().add(statCol);

    return officesTable;
  }

}
