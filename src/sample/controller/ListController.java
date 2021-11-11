package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sample.Database.DatabaseHandler;
import sample.model.Task;

public class ListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField listPriorityField;

    @FXML
    private TextField listDescriptionField;

    @FXML
    private JFXButton listSaveTaskButton;

    @FXML
    private JFXListView<Task> listTask;

    @FXML
    private TextField listTaskField;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXComboBox<Integer> searchByPriorityComboBox;

    @FXML
    private ImageView listRefreshButton;


    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshedTasks;


    private DatabaseHandler databaseHandler;

    private static int selectedPriority;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        searchByPriorityComboBox.getItems().addAll(1,2,3);

        tasks = FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()){
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            System.out.println("User Tasks: " + resultSet.getString("task"));

            tasks.addAll(task);
        }

        listTask.setItems(tasks);
        listTask.setCellFactory(CellController -> new CellController());

        listRefreshButton.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        listSaveTaskButton.setOnAction(event -> {
            addNewTask();
        });
    }


    public void refreshList() throws SQLException {

        System.out.println("refreshList in ListCont called");

        refreshedTasks = FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = null;

        try {
            resultSet = databaseHandler.getTasksByUser(AddItemController.userId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            refreshedTasks.addAll(task);
        }
        listTask.setItems(refreshedTasks);
        listTask.setCellFactory(CellController -> new CellController());
    }

    public void addNewTask() {
            if (!listTaskField.getText().equals("") || !listDescriptionField.getText().equals("") || !listPriorityField.getText().equals("")){
               Task myNewTask = new Task();

                Calendar calendar = Calendar.getInstance();

                java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

                myNewTask.setUserId(AddItemController.userId);
                myNewTask.setTask(listTaskField.getText().trim());
                myNewTask.setDescription(listDescriptionField.getText().trim());
                myNewTask.setPriority(Integer.parseInt(listPriorityField.getText().trim()));
                myNewTask.setDatecreated(timestamp);

               databaseHandler = new DatabaseHandler();
               databaseHandler.insertTask(myNewTask);

               listTaskField.setText("");
               listDescriptionField.setText("");
               listPriorityField.setText("");

                try {
                    initialize();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }

    @FXML
    void getSelectedPriority(ActionEvent event) throws SQLException, ClassNotFoundException {
    selectedPriority = searchByPriorityComboBox.getValue();

        tasks = FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId,selectedPriority);

        while (resultSet.next()){
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            System.out.println("User Tasks: " + resultSet.getString("task"));

            tasks.addAll(task);
        }

        listTask.setItems(tasks);
        listTask.setCellFactory(CellController -> new CellController());
    }

}
