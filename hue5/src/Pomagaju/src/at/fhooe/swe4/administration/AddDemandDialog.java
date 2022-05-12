package at.fhooe.swe4.administration;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.concurrent.atomic.AtomicReference;

public class AddDemandDialog {
  private Stage dialogStage;
  private ObservableList<DemandItem> demand;

  public AddDemandDialog(Window owner, ObservableList<DemandItem> demand) {
    dialogStage = new Stage();
    this.demand = demand;

    Button addButton = new Button("Bedarf hinzufügen");
    addButton.setId("button-add-demand");

    Button cancelButton = new Button("Cancel");
    cancelButton.setId("cancel-button");
    cancelButton.setOnAction(e -> {
      dialogStage.close();
    });

    HBox buttonBar = new HBox(20);
    buttonBar.setId("button-bar");
    buttonBar.getChildren().addAll(addButton, cancelButton);

    GridPane inputGrid = new GridPane();
    inputGrid.setId("add-demand-input-grid");

    inputGrid.add(new Label("Beschreibung: "),0,0);
    TextField descriptionInput = new TextField();
    inputGrid.add(descriptionInput,1,0);

    inputGrid.add(new Label("Zustand: "),0,1);
    TextField conditionInput = new TextField();
    inputGrid.add(conditionInput,1,1);

    inputGrid.add(new Label("Menge: "),0,2);
    Label curAmount = new Label("0");

    AtomicReference<Integer> curAmountInt = new AtomicReference<>(0);

    Button increaseBtn = new Button("mehr");
    cancelButton.setId("increaseBtn-button");
    increaseBtn.setOnAction(e -> {
      curAmountInt.getAndSet(curAmountInt.get() + 1);
      String curAmountString = curAmountInt.toString();
      curAmount.setText(curAmountString);
    });

    Button decreaseBtn = new Button("weniger");
    cancelButton.setId("increaseBtn-button");
    decreaseBtn.setOnAction(e -> {
      Integer val = (Integer)curAmountInt.get();
      if(val >= 1) {
        curAmountInt.getAndSet(curAmountInt.get() - 1);
        String curAmountString = curAmountInt.toString();
        curAmount.setText(curAmountString);
      }
    });

    inputGrid.add(curAmount,1,2);
    inputGrid.add(increaseBtn,0,5);
    inputGrid.add(decreaseBtn, 0,6);

    inputGrid.add(new Label("Kategorie: "),0,3);
    TextField categoryInput = new TextField();
    inputGrid.add(categoryInput,1,3);

    inputGrid.add(buttonBar,0,4,3,1);

    addButton.setOnAction(e-> {
      String desc = descriptionInput.getText();
      String cond = conditionInput.getText();

    });

    Scene dialogScene = new Scene(inputGrid);
    dialogScene.getStylesheets().add(getClass().getResource("/addDemand-dialog.css").toExternalForm());

    dialogStage.setScene(dialogScene);
    dialogStage.setTitle("Bedarfsmeldung hinzufügen");
    dialogStage.initModality(Modality.WINDOW_MODAL);
    dialogStage.initStyle(StageStyle.UTILITY);
    dialogStage.initOwner(owner);
  }

  public void show() {
    dialogStage.show();
  }

}
