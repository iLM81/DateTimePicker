import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DatePicker with Time picker and month and year selection example
 *
 * Leonid Mikhalev
 * mikhalev.leonid@gmail.com
 *
 */
public class DateTimePickerSkin extends DatePickerSkin {

  private DateTimePicker dateTimePicker;
  private DatePickerContent datetimPickerContent;

  private int hours   = 0;
  private int minutes = 0;
  private int seconds = 0;

  // TimePicker control variables
  // TimePicker disabling trigger
  private boolean isTimePickerEnabled = true;

  private HBox hboxMain = new HBox();
  private VBox vboxHours   = new VBox();
  private VBox vboxMinutes = new VBox();
  private VBox vboxSeconds = new VBox();
  private Label lHours   = new Label("Часы:");
  private Label lMinutes = new Label("Минуты:");
  private Label lSeconds = new Label("Секунды:");

  private TextField tfHours   = new TextField();
  private TextField tfMinutes = new TextField();
  private TextField tfSeconds = new TextField();
  private Button bH_plus = new Button();
  private Button bH_minus = new Button();
  private Button bM_plus = new Button();
  private Button bM_minus = new Button();
  private Button bS_plus = new Button();
  private Button bS_minus = new Button();
  // -------------------------------------------------

  // Month picker control variables

  private String[] monthes = {
          "January",
          "February",
          "March",
          "April",
          "May",
          "June",
          "July",
          "August",
          "September",
          "October",
          "November",
          "December",
  };

  // Constructor
  public DateTimePickerSkin(DateTimePicker dateTimePicker, boolean isTimePickerEnabled){
    super(dateTimePicker);
    this.dateTimePicker = dateTimePicker;
    this.isTimePickerEnabled = isTimePickerEnabled;
  }

  // Acquiring DatePickerContent
  @Override
  public Node getPopupContent() {
    if (datetimPickerContent == null) {

      this.datetimPickerContent = (DatePickerContent) super.getPopupContent();

      // Getting DatePicker controls
      BorderPane monthYearPane = (BorderPane) datetimPickerContent.getChildren().get(0);
      HBox monthSpinner = (HBox) monthYearPane.getLeft();
      HBox yearsSpinner = (HBox) monthYearPane.getRight();

      Button bMonthMinus = (Button) monthSpinner.getChildren().get(0);
      Label lMonth = (Label) monthSpinner.getChildren().get(1);
      Button bMonthPlus = (Button) monthSpinner.getChildren().get(2);

      Button bYearMinus = (Button) yearsSpinner.getChildren().get(0);
      Label lYear  = (Label) yearsSpinner.getChildren().get(1);
      Button bYearPlus = (Button) yearsSpinner.getChildren().get(2);

      lMonth.setOnMouseReleased(this :: handleMonthButtonAction);
      lYear.setOnMouseReleased(this :: handleYearButtonAction);

      bMonthMinus.getStyleClass().add( "button-iconified" );
      bMonthPlus.getStyleClass().add( "button-iconified" );

      bYearMinus.getStyleClass().add( "button-iconified" );
      bYearPlus.getStyleClass().add( "button-iconified" );

      bYearMinus.setOnAction(this :: handleYearButtonMinusAction);
      bYearPlus.setOnAction(this :: handleYearButtonPlusAction);

      if ( isTimePickerEnabled ) this.datetimPickerContent.getChildren().add(0, getControls());
    }
    return datetimPickerContent;
  }

  // Label month click event handler
  private void handleMonthButtonAction (MouseEvent event) {
    if ( this.datetimPickerContent == null ) return;
    if ( isTimePickerEnabled && this.datetimPickerContent.getChildren().size() > 3) {
      this.datetimPickerContent.getChildren().remove(0);
      return;
    } else if (!isTimePickerEnabled && this.datetimPickerContent.getChildren().size() > 2){
      this.datetimPickerContent.getChildren().remove(0);
    }
    else {
      this.datetimPickerContent.getChildren().add(0, getMonthContentPane());
      return;
    }
  }
  // Label year click event handler
  private void handleYearButtonAction (MouseEvent event) {
    if ( this.datetimPickerContent == null ) return;
    if ( isTimePickerEnabled && this.datetimPickerContent.getChildren().size() > 3) {
      this.datetimPickerContent.getChildren().remove(0);
      return;
    } else if (!isTimePickerEnabled && this.datetimPickerContent.getChildren().size() > 2){
      this.datetimPickerContent.getChildren().remove(0);
    }
    else {
      this.datetimPickerContent.getChildren().add(0, getYearContentPane(this.dateTimePicker.getDateTimeValue().getYear()));
      return;
    }
  }

  // Button year plus event handler
  private void handleYearButtonPlusAction (ActionEvent event) {
    if ( this.datetimPickerContent == null ) return;
      if ( isTimePickerEnabled && this.datetimPickerContent.getChildren().size() > 3) {
        // if year dialog is shown
        this.datetimPickerContent.getChildren().remove(0);
        int year = this.dateTimePicker.getDateTimeValue().getYear()+10;
        int month = this.dateTimePicker.getDateTimeValue().getMonthValue();
        int day = this.dateTimePicker.getDateTimeValue().getDayOfMonth();
        int hour = this.dateTimePicker.getDateTimeValue().getHour();
        int minute = this.dateTimePicker.getDateTimeValue().getMinute();
        this.dateTimePicker.setDateTimeValue(LocalDateTime.of(year, month, day, hour, minute));
        this.datetimPickerContent.getChildren().add(0, getYearContentPane(this.dateTimePicker.getDateTimeValue().getYear()));
        return;
      } else if (!isTimePickerEnabled && this.datetimPickerContent.getChildren().size() > 2){
        // if year dialog is shown
        this.datetimPickerContent.getChildren().remove(0);
        int year = this.dateTimePicker.getDateTimeValue().getYear()+10;
        int month = this.dateTimePicker.getDateTimeValue().getMonthValue();
        int day = this.dateTimePicker.getDateTimeValue().getDayOfMonth();
        int hour = this.dateTimePicker.getDateTimeValue().getHour();
        int minute = this.dateTimePicker.getDateTimeValue().getMinute();
        this.dateTimePicker.setDateTimeValue(LocalDateTime.of(year, month, day, hour, minute));
        this.datetimPickerContent.getChildren().add(0, getYearContentPane(this.dateTimePicker.getDateTimeValue().getYear()));
        return;
      }
      else {
      this.dateTimePicker.setDateTimeValue(this.dateTimePicker.getDateTimeValue().plusYears(1));
      return;
    }
  }

  // Button year minus event handler
  private void handleYearButtonMinusAction (ActionEvent event) {
    if ( this.datetimPickerContent == null ) return;
    if ( isTimePickerEnabled && this.datetimPickerContent.getChildren().size() > 3) {
      // if year dialog is shown
      this.datetimPickerContent.getChildren().remove(0);
      int year = this.dateTimePicker.getDateTimeValue().getYear()-10;
      int month = this.dateTimePicker.getDateTimeValue().getMonthValue();
      int day = this.dateTimePicker.getDateTimeValue().getDayOfMonth();
      int hour = this.dateTimePicker.getDateTimeValue().getHour();
      int minute = this.dateTimePicker.getDateTimeValue().getMinute();
      this.dateTimePicker.setDateTimeValue(LocalDateTime.of(year, month, day, hour, minute));
      this.datetimPickerContent.getChildren().add(0, getYearContentPane(this.dateTimePicker.getDateTimeValue().getYear()));
      return;
    } else if ( !isTimePickerEnabled && this.datetimPickerContent.getChildren().size() > 2 ) {
      this.datetimPickerContent.getChildren().remove(0);
      int year = this.dateTimePicker.getDateTimeValue().getYear()-10;
      int month = this.dateTimePicker.getDateTimeValue().getMonthValue();
      int day = this.dateTimePicker.getDateTimeValue().getDayOfMonth();
      int hour = this.dateTimePicker.getDateTimeValue().getHour();
      int minute = this.dateTimePicker.getDateTimeValue().getMinute();
      this.dateTimePicker.setDateTimeValue(LocalDateTime.of(year, month, day, hour, minute));
      this.datetimPickerContent.getChildren().add(0, getYearContentPane(this.dateTimePicker.getDateTimeValue().getYear()));
      return;
    }
    else {
      this.dateTimePicker.setDateTimeValue(this.dateTimePicker.getDateTimeValue().minusYears(1));
      return;
    }
  }

  // Gridpane container for month and year picker
  private GridPane getContainer(){
    GridPane pane = new GridPane();
    //pane.getStyleClass().add("container-dark");
    pane.getStylesheets().add(this.getClass().getResource("/css/datetimepicker.css").toExternalForm());
    pane.getStyleClass().add("gridpane-datepicker");

    pane.setHgap(1);
    pane.setVgap(1);
    pane.setAlignment(Pos.CENTER);

    ColumnConstraints column1 = new ColumnConstraints();
    ColumnConstraints column2 = new ColumnConstraints();
    ColumnConstraints column3 = new ColumnConstraints();

    column1.setHalignment(HPos.CENTER);
    column2.setHalignment(HPos.CENTER);
    column3.setHalignment(HPos.CENTER);

    column1.setHgrow(Priority.NEVER);
    column2.setHgrow(Priority.NEVER);
    column3.setHgrow(Priority.NEVER);
    return pane;
  }

  // Month picker controls
  private GridPane getMonthContentPane() {
    GridPane pane =getContainer();

    int index = 0;
    for ( int row = 0; row < 3; row ++ ) {
      for ( int col = 0; col < 4; col ++ ) {
        Label label = new Label(monthes[index]);
        label.setMinWidth(78);
        label.setPrefWidth(78);
        label.setMaxWidth(78);
        label.setMinHeight(37);
        label.setPrefHeight(37);
        label.setMaxHeight(37);
        label.setAlignment(Pos.CENTER);
        label.getStylesheets().add(this.getClass().getResource("/css/datetimepicker.css").toExternalForm());
        if (index == LocalDate.now().getMonthValue() - 1 &&
                LocalDate.now().getYear() == this.dateTimePicker.getDateTimeValue().getYear()) label.getStyleClass().add( "label-current" );
        label.setOnMouseReleased( e -> {
          //System.out.println(e.getSource());
          for (int i = 0; i < monthes.length; i ++) {
            if (((Label) e.getSource()).getText().compareTo(monthes[i]) == 0) {
              int year = this.dateTimePicker.getDateTimeValue().getYear();
              int month = i+1;
              int day = this.dateTimePicker.getDateTimeValue().getDayOfMonth();
              int hour = this.dateTimePicker.getDateTimeValue().getHour();
              int minute = this.dateTimePicker.getDateTimeValue().getMinute();
              this.dateTimePicker.setDateTimeValue(LocalDateTime.of(year, month, day, hour, minute));
              if ( this.datetimPickerContent.getChildren().size() > 2) {
                this.datetimPickerContent.getChildren().remove(0);
                return;
              }else {
                this.datetimPickerContent.getChildren().add(0, getMonthContentPane());
                return;
              }
            }
          }
        });
        pane.add(label, col, row);
        index++;
      }
    }

    return pane;
  }

  // Years picker controls
  private GridPane getYearContentPane(int year) {
    GridPane pane = getContainer();

    String[] years = new String[12];
    int k = 0;
    for ( int interval = year - 6; interval <= year + 5; interval ++ ) {
      years[k] = Integer.toString(interval);
      k++;
    }

    int index = 0;
    for ( int row = 0; row < 3; row ++ ) {
      for ( int col = 0; col < 4; col ++ ) {
        Label label = new Label(years[index]);
        label.setMinWidth(78);
        label.setPrefWidth(78);
        label.setMaxWidth(78);
        label.setMinHeight(37);
        label.setPrefHeight(37);
        label.setMaxHeight(37);
        label.setAlignment(Pos.CENTER);
        label.getStylesheets().add(this.getClass().getResource("/css/datetimepicker.css").toExternalForm());
        if (years[index].compareTo(Integer.toString(LocalDate.now().getYear())) == 0 ) label.getStyleClass().add( "label-current" );
        label.setOnMouseReleased( e -> {
          //System.out.println(e.getSource());
          for (int i = 0; i < monthes.length; i ++) {
            if (((Label) e.getSource()).getText().compareTo(years[i]) == 0) {
              int yr = Integer.parseInt(years[i]);
              int month = this.dateTimePicker.getDateTimeValue().getMonthValue();
              int day = this.dateTimePicker.getDateTimeValue().getDayOfMonth();
              int hour = this.dateTimePicker.getDateTimeValue().getHour();
              int minute = this.dateTimePicker.getDateTimeValue().getMinute();
              this.dateTimePicker.setDateTimeValue(LocalDateTime.of(yr, month, day, hour, minute));
              if ( this.datetimPickerContent.getChildren().size() > 2) {
                this.datetimPickerContent.getChildren().remove(0);
                return;
              }else {
                this.datetimPickerContent.getChildren().add(0, getYearContentPane(this.dateTimePicker.getDateTimeValue().getYear()));
                return;
              }
            }
          }
        });
        pane.add(label, col, row);
        index++;
      }
    }

    return pane;
  }

  /**
   * Контрол Time Picker
   *
   * Any control can be added instead of TimePicker even complex fxml
   *
   * @return
   */
  // Time Picker Control
  private HBox getControls(){
    hboxMain.getStyleClass().add("container-dark");
    hboxMain.setSpacing(5d);
    hboxMain.setPadding(new Insets(10,5,10,5));

    vboxHours.getStyleClass().add("container-dark");
    vboxHours.setSpacing(3d);

    vboxMinutes.getStyleClass().add("container-dark");
    vboxMinutes.setSpacing(3d);

    vboxSeconds.getStyleClass().add("container-dark");
    vboxSeconds.setSpacing(3d);

    tfHours.setPrefWidth(30);
    tfHours.setMinWidth(30);
    tfHours.setMaxWidth(30);
    tfHours.setDisable(false);
    tfHours.textProperty().addListener(this::hoursChangeListener);

    tfMinutes.setPrefWidth(30);
    tfMinutes.setMinWidth(30);
    tfMinutes.setMaxWidth(30);
    tfMinutes.setDisable(false);
    tfMinutes.textProperty().addListener(this::minutesChangeListener);

    tfSeconds.setPrefWidth(30);
    tfSeconds.setMinWidth(30);
    tfSeconds.setMaxWidth(30);
    tfSeconds.setDisable(false);
    tfSeconds.textProperty().addListener(this::secondsChangeListener);

    tfHours.setText(String.format("%02d", hours));
    tfMinutes.setText(String.format("%02d", minutes));
    tfSeconds.setText(String.format("%02d", seconds));

    bH_plus.getStyleClass().add("button-iconified");
    bH_plus.setGraphic(new ImageView(this.getClass().getResource( "/img/arrow-up-16.png" ).toExternalForm()));
    bH_plus.setText("");
    bH_plus.setPrefWidth(16);
    bH_plus.setMinWidth(16);
    bH_plus.setMaxWidth(16);
    bH_plus.setPrefHeight(11);
    bH_plus.setMinHeight(11);
    bH_plus.setMaxHeight(11);
    bH_plus.setOnAction(this::hoursPlus);

    bH_minus.getStyleClass().add("button-iconified");
    bH_minus.setGraphic(new ImageView(this.getClass().getResource( "/img/arrow-down-16.png" ).toExternalForm()));
    bH_minus.setText("");
    bH_minus.setPrefWidth(16);
    bH_minus.setMinWidth(16);
    bH_minus.setMaxWidth(16);
    bH_minus.setPrefHeight(11);
    bH_minus.setMinHeight(11);
    bH_minus.setMaxHeight(11);
    bH_minus.setOnAction(this::hoursMinus);

    bM_plus.getStyleClass().add("button-iconified");
    bM_plus.setGraphic(new ImageView(this.getClass().getResource( "/img/arrow-up-16.png" ).toExternalForm()));
    bM_plus.setText("");
    bM_plus.setPrefWidth(16);
    bM_plus.setMinWidth(16);
    bM_plus.setMaxWidth(16);
    bM_plus.setPrefHeight(11);
    bM_plus.setMinHeight(11);
    bM_plus.setMaxHeight(11);
    bM_plus.setOnAction(this::minutesPlus);

    bM_minus.getStyleClass().add("button-iconified");
    bM_minus.setGraphic(new ImageView(this.getClass().getResource( "/img/arrow-down-16.png" ).toExternalForm()));
    bM_minus.setText("");
    bM_minus.setPrefWidth(16);
    bM_minus.setMinWidth(16);
    bM_minus.setMaxWidth(16);
    bM_minus.setPrefHeight(11);
    bM_minus.setMinHeight(11);
    bM_minus.setMaxHeight(11);
    bM_minus.setOnAction(this::minutesMinus);

    bS_plus.getStyleClass().add("button-iconified");
    bS_plus.setGraphic(new ImageView(this.getClass().getResource( "/img/arrow-up-16.png" ).toExternalForm()));
    bS_plus.setText("");
    bS_plus.setPrefWidth(16);
    bS_plus.setMinWidth(16);
    bS_plus.setMaxWidth(16);
    bS_plus.setPrefHeight(11);
    bS_plus.setMinHeight(11);
    bS_plus.setMaxHeight(11);
    bS_plus.setOnAction(this::secondsPlus);

    bS_minus.getStyleClass().add("button-iconified");
    bS_minus.setGraphic(new ImageView(this.getClass().getResource( "/img/arrow-down-16.png" ).toExternalForm()));
    bS_minus.setText("");
    bS_minus.setPrefWidth(16);
    bS_minus.setMinWidth(16);
    bS_minus.setMaxWidth(16);
    bS_minus.setPrefHeight(11);
    bS_minus.setMinHeight(11);
    bS_minus.setMaxHeight(11);
    bS_minus.setOnAction(this::secondsMinus);

    vboxHours.getChildren().add(bH_plus);
    vboxHours.getChildren().add(bH_minus);

    vboxMinutes.getChildren().add(bM_plus);
    vboxMinutes.getChildren().add(bM_minus);

    vboxSeconds.getChildren().add(bS_plus);
    vboxSeconds.getChildren().add(bS_minus);

    hboxMain.setAlignment(Pos.CENTER_LEFT);
    hboxMain.getChildren().addAll(lHours, tfHours, vboxHours, lMinutes, tfMinutes, vboxMinutes, lSeconds, tfSeconds, vboxSeconds);
    return hboxMain;
  }
  // -------------------------------------------------------------------------------------------------------------------

  // Controls Events Handlers
  private void hoursPlus(ActionEvent event){
    hours++;
    if (hours > 23) hours = 0;
    tfHours.setText(String.format("%02d", hours));
  }
  private void hoursMinus(ActionEvent event){
    hours--;
    if (hours < 0) hours = 23;
    tfHours.setText(String.format("%02d", hours));
  }
  private void minutesPlus(ActionEvent event){
    minutes++;
    if (minutes > 59) minutes = 0;
    tfMinutes.setText(String.format("%02d", minutes));
  }
  private void minutesMinus(ActionEvent event){
    minutes--;
    if (minutes < 0) minutes = 59;
    tfMinutes.setText(String.format("%02d", minutes));
  }
  private void secondsPlus(ActionEvent event){
    seconds++;
    if (seconds > 59) seconds = 0;
    tfSeconds.setText(String.format("%02d", seconds));
  }
  private void secondsMinus(ActionEvent event){
    seconds--;
    if (seconds < 0) seconds = 59;
    tfSeconds.setText(String.format("%02d", seconds));
  }

  private void hoursChangeListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue){
    dateTimePicker.setDateTimeValue(dateTimePicker.getDateTimeValue().toLocalDate().atTime(Integer.parseInt(newValue), minutes, seconds));
  }
  private void minutesChangeListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue){
    dateTimePicker.setDateTimeValue(dateTimePicker.getDateTimeValue().toLocalDate().atTime(hours, Integer.parseInt(newValue), seconds));
  }
  private void secondsChangeListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue){
    dateTimePicker.setDateTimeValue(dateTimePicker.getDateTimeValue().toLocalDate().atTime(hours, minutes, Integer.parseInt(newValue)));
  }
  // -------------------------------------------------------------------------------------------------------------------

}
