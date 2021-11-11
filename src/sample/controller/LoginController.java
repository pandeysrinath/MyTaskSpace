package sample.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Database.DatabaseHandler;
import sample.animations.Shaker;
import sample.model.User;

public class LoginController {

    private int UserId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton loginButton;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private JFXButton loginSingnupButton;

    @FXML
    private TextField loginUsername;

    DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        loginButton.setOnAction(event -> {

            String loginText = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPwd);

            ResultSet userRow = databaseHandler.getUser(user);

            int counter = 0;

            try {
                while (userRow.next()){
                    counter++;
                    String name = userRow.getString("firstname");
                    UserId = userRow.getInt("userid");
                    System.out.println("Welcome! " + name);
                }
                if (counter == 1) {
                    showAddItemScreen();
                }
                else {
                    Shaker userNameShaker = new Shaker(loginUsername);
                    Shaker passwordShaker = new Shaker(loginPassword);
                    userNameShaker.shake();
                    passwordShaker.shake();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        loginSingnupButton.setOnAction(event -> {
           // Take users to signup screen.
            loginSingnupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));

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

    }

    private void showAddItemScreen() {

        loginButton.setOnAction(event -> {
            // Take users to AddItem screen.
            loginSingnupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/additem.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            AddItemController addItemController = loader.getController();
            addItemController.setUserId(UserId);

            stage.showAndWait();
        });
    }
}
