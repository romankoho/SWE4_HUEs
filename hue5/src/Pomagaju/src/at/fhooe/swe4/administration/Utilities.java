package at.fhooe.swe4.administration;

import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class Utilities {

  public static Button createTextButton(String id, String caption) {
    Button button = new Button(caption);
    button.setId(id);
    button.setPadding(new Insets(5));
    button.setPrefSize(50,40);
    button.setMinSize(50,40);
    return button;
  }

}
