package sample.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UpdateTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField updateDescriptionField;

    @FXML
    private TextField updatePriorityField;

    @FXML
    public JFXButton updateTaskButton;

    @FXML
    private TextField updateTaskField;

    @FXML
    void initialize() {

    }

    public void setTaskField(String task) {
        this.updateTaskField.setText(task);
    }

    public String getTask() {
        return this.updateTaskField.getText().trim();
    }

    public void setUpdateDescriptionField(String description) {
        this.updateDescriptionField.setText(description);
    }

    public String getDescription() {
        return this.updateDescriptionField.getText().trim();
    }

    public int getPriority() { return Integer.parseInt(this.updatePriorityField.getText().trim()); }

   public void setUpdatePriorityField(String priority) { this.updatePriorityField.setText(priority); }
}
