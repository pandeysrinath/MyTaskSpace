package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DatabaseHandler;
import sample.model.Task;

public class CellController extends JFXListCell<Task> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView deleteButton;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView iconImageView;

    @FXML
    private ImageView listUpdateButton;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label taskLabel;

    private FXMLLoader fxmlLoader;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
        assert dateLabel != null : "fx:id=\"dateLabel\" was not injected: check your FXML file 'cell.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'cell.fxml'.";
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'cell.fxml'.";
        assert iconImageView != null : "fx:id=\"iconImageView\" was not injected: check your FXML file 'cell.fxml'.";
        assert listUpdateButton != null : "fx:id=\"listUpdateButton\" was not injected: check your FXML file 'cell.fxml'.";
        assert rootAnchorPane != null : "fx:id=\"rootAnchorPane\" was not injected: check your FXML file 'cell.fxml'.";
        assert taskLabel != null : "fx:id=\"taskLabel\" was not injected: check your FXML file 'cell.fxml'.";

    }

    @Override
    protected void updateItem(Task myTask, boolean empty) {
        super.updateItem(myTask, empty);

        if (empty || myTask == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            taskLabel.setText(myTask.getTask());
            dateLabel.setText(myTask.getDatecreated().toString());
            descriptionLabel.setText(myTask.getDescription());

            int taskId = myTask.getTaskId();

            listUpdateButton.setOnMouseClicked(event -> {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/updateTaskForm.fxml"));


                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                UpdateTaskController updateTaskController = loader.getController();
                updateTaskController.setTaskField(myTask.getTask());
                updateTaskController.setUpdateDescriptionField(myTask.getDescription());
                updateTaskController.setUpdatePriorityField(String.valueOf(myTask.getPriority()));

                updateTaskController.updateTaskButton.setOnAction(event1 -> {

                    Calendar calendar = Calendar.getInstance();

                    java.sql.Timestamp timestamp =
                            new java.sql.Timestamp(calendar.getTimeInMillis());

                    try {

                        System.out.println("taskid " + myTask.getTaskId());

                        databaseHandler = new DatabaseHandler();
                        databaseHandler.updateTask(timestamp, updateTaskController.getDescription(), updateTaskController.getTask(), myTask.getTaskId(), updateTaskController.getPriority());

                        //update our listController
                        // updateTaskController.refreshList();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                });

                stage.show();


            });

            deleteButton.setOnMouseClicked(event -> {

                databaseHandler = new DatabaseHandler();

                try {
                    databaseHandler.deleteTask(AddItemController.userId, taskId);

                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                getListView().getItems().remove(getItem());
            });

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }
}
