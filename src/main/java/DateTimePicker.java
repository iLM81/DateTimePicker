import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom DatePicker Class
 *
 * // Functional design in DateTimePickerSkin class
 *
 * Usage example:
 *
 * // Initialization
 * boolean isTimePickerEnabled = true; // false - if no time picker needed
 * DateTimePicker dateTimePicker = new DateTimePicker(isTimePickerEnabled);
 *
 * // EventHandler example
 * // LocalDateTime oldValue;
 * // LocalDateTime newValue;
 * dateTimePicker.dateTimeValueProperty().addListener((observable, oldValue, newValue) -> {
 *   System.out.println(oldValue + ", " + newValue);
 * });
 *
 * // Getting value example
 * dtPickerDateFrom.getDateTimeValue(); // returns LocalDateTime
 * dtPickerDateFrom.getDateValue();     // returns LocalDate
 *
 * // Setting value example
 * dtPickerDateFrom.setDateTimeValue(LocalDateTime.now());
 *
 * Leonid Mikhalev
 * mikhalev.leonid@gmail.com
 *
 */
public class DateTimePicker extends DatePicker {

  // Default date and date time formats
  public static final String DefaultFormat = "dd.MM.yyyy HH:mm";
  public static final String NoTimeFormat  = "dd.MM.yyyy";

  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DefaultFormat);;
  private ObjectProperty<LocalDateTime> dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.now());

  // Default TimePicker control trigger
  private boolean isTimePickerEnabled = false;

  // DatePicker customization
  @Override
  protected Skin<?> createDefaultSkin () {
      return new DateTimePickerSkin(this, this.isTimePickerEnabled);
  }

  // Class Body with public methods
  private ObjectProperty<String> format = new SimpleObjectProperty<String>() {
    public void set(String newValue) {
      super.set(newValue);
      formatter = DateTimeFormatter.ofPattern(newValue);
    }
  };

  // Constructor
  public DateTimePicker() {

    if (this.isTimePickerEnabled)
      setFormat(DefaultFormat);
    else
      setFormat(NoTimeFormat);
    setConverter(new InternalConverter());

    valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == null) {
        dateTimeValue.set(null);
      } else {
        if (dateTimeValue.get() == null) {
          dateTimeValue.set(LocalDateTime.of(newValue, LocalTime.now()));
        } else {
          LocalTime time = dateTimeValue.get().toLocalTime();
          dateTimeValue.set(LocalDateTime.of(newValue, time));
        }
      }
    });

    dateTimeValue.addListener((observable, oldValue, newValue) -> {
      setValue(newValue == null ? null : newValue.toLocalDate());
    });

    getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue)
        simulateEnterPressed();
    });
  }

  // Constructor
  public DateTimePicker(boolean isTimePickerEnabled) {

    this.isTimePickerEnabled = isTimePickerEnabled;
    if (this.isTimePickerEnabled)
      setFormat(DefaultFormat);
    else
      setFormat(NoTimeFormat);

    setConverter(new InternalConverter());

    valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == null) {
        dateTimeValue.set(null);
      } else {
        if (dateTimeValue.get() == null) {
          dateTimeValue.set(LocalDateTime.of(newValue, LocalTime.now()));
        } else {
          LocalTime time = dateTimeValue.get().toLocalTime();
          dateTimeValue.set(LocalDateTime.of(newValue, time));
        }
      }
    });

    dateTimeValue.addListener((observable, oldValue, newValue) -> {
      setValue(newValue == null ? null : newValue.toLocalDate());
    });

    getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue)
        simulateEnterPressed();
    });
  }

  public ObjectProperty<LocalDateTime> dateTimeValueProperty() {
    return dateTimeValue;
  }

  public void setDateTimeValue(LocalDateTime dateTimeValue) {
    this.dateTimeValue.set(dateTimeValue);
  }

  public LocalDateTime getDateTimeValue() {
    return dateTimeValue.get();
  }

  public LocalDate getDateValue() {
    return dateTimeValue.get().toLocalDate();
  }

  public ObjectProperty<String> formatProperty() {
    return format;
  }

  public void setFormat(String format) {
    this.format.set(format);
  }

  public String getFormat() {
    return format.get();
  }

  class InternalConverter extends StringConverter<LocalDate> {
    public String toString(LocalDate object) {
      LocalDateTime value = getDateTimeValue();
      return (value != null) ? value.format(formatter) : "";
    }

    public LocalDate fromString(String value) {
      if (value == null) {
        dateTimeValue.set(null);
        return null;
      }

      dateTimeValue.set(LocalDateTime.parse(value, formatter));
      return dateTimeValue.get().toLocalDate();
    }
  }

  private void simulateEnterPressed() {
    getEditor().fireEvent(new KeyEvent(getEditor(), getEditor(), KeyEvent.KEY_PRESSED, null, null, KeyCode.ENTER, false, false, false, false));
  }
}
