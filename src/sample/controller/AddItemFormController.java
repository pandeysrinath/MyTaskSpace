package sample.controller;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Database.DatabaseHandler;
import sample.model.Task;

public class AddItemFormController {
    private int userId;

    private DatabaseHandler databaseHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField priorityField;

    @FXML
    private JFXButton saveTaskButton;

    @FXML
    private TextField taskField;

    @FXML
    private Label successLabel;

    @FXML
    private JFXButton todosButton;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        saveTaskButton.setOnAction(event -> {

            Task task = new Task();

            Calendar calendar = Calendar.getInstance();

            java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

            String taskText = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();
            String taskPriority = priorityField.getText().trim();

            if (!taskText.equals("") || !taskDescription.equals("") || !taskPriority.equals("")) {

                task.setUserId(AddItemController.userId);
                task.setDatecreated(timestamp);
                task.setDescription(taskDescription);
                task.setTask(taskText);
                task.setPriority(Integer.parseInt(taskPriority));

                databaseHandler.insertTask(task);

                successLabel.setVisible(true);

                todosButton.setVisible(true);
                int taskNumber = 0;
                try {
                    taskNumber = databaseHandler.getAllTasks(AddItemController.userId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                todosButton.setText("My Task's " + "(" + taskNumber + ")");

                taskField.setText("");
                descriptionField.setText("");
                priorityField.setText("");

                todosButton.setOnAction(event1 -> {
                    // Send user to list view
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/view/list.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                });
            } else {
                System.out.println("Nothing added!");
            }

        });
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println(this.userId);
    }

}
