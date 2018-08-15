import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * 1. DatePicker with TimePicker
 * 2. Month/Year selection on according labels clicked
 * 3. CSS Styling
 *
 * Leonid Mikhalev
 * mikhalev.leonid@gmail.com
 *
 */
public class app extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Init controls
    DateTimePicker dateTimePicker = new DateTimePicker(true);
    DateTimePicker datePicker = new DateTimePicker(false);

    // Add listener
    dateTimePicker.dateTimeValueProperty().addListener((observable, oldValue, newValue) -> {
      System.out.println(oldValue + ", " + newValue);
    });
    datePicker.dateTimeValueProperty().addListener((observable, oldValue, newValue) -> {
      System.out.println(oldValue + ", " + newValue);
    });

    // Add Container
    VBox vBox = new VBox();
    vBox.getStylesheets().add(this.getClass().getResource("/css/datetimepicker.css").toExternalForm());
    vBox.getStyleClass().add("gridpane-datepicker");

    // Adding controls to container
    vBox.getChildren().addAll(dateTimePicker, datePicker);

    Scene scene = new Scene(vBox,320, 240);
    scene.getStylesheets().add(this.getClass().getResource("/css/datetimepicker.css").toExternalForm());

    primaryStage.setTitle("DateTimePicker Example");
    primaryStage.setScene(scene);
    primaryStage.show();


  }

}
