package sample.Database;

import com.mysql.jdbc.JDBC4Connection;
import javafx.scene.Parent;
import sample.model.Task;
import sample.model.User;

import java.sql.*;


public class DatabaseHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,dbUser,dbPass);
        return dbConnection;
    }

    // write
    public void signUpUser(User user){
        String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME + ","
                + Const.USERS_LASTNAME + "," + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD
                + "," + Const.USERS_LOCATION + "," +Const.USERS_GENDER + ")" + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement  = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.setString(6, user.getGender());

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet getTasksByUser (int userId) throws SQLException, ClassNotFoundException {
        ResultSet resultTasks = null;
        String query = "SELECT * FROM " + Const.TASKS_TABLE+ " WHERE " + Const.USERS_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1,userId);

        resultTasks = preparedStatement.executeQuery();

        return  resultTasks;
    }

    public ResultSet getTasksByUser (int userId, int searchPriority) throws SQLException, ClassNotFoundException {
        ResultSet resultTasks = null;
        String query = "SELECT * FROM " + Const.TASKS_TABLE+ " WHERE " + Const.USERS_ID + "=?" + " AND " + Const.TASK_PRIORITY + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1,userId);
        preparedStatement.setInt(2, searchPriority);

        resultTasks = preparedStatement.executeQuery();

        return  resultTasks;
    }

    public void deleteTask(int userId, int taskId) throws ClassNotFoundException, SQLException {
        String query = "DELETE FROM " + Const.TASKS_TABLE + " WHERE "+
                Const.USERS_ID + "=?" + " AND " + Const.TASK_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, taskId);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public ResultSet getUser(User user) {

        ResultSet resultSet = null;

        if (!user.getUserName().equals("") || !user.getPassword().equals("")) {
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " +
                    Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD + "=?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());

                resultSet = preparedStatement.executeQuery();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.out.println("Please enter your credentials");
        }
        return resultSet;
    }

    public int getAllTasks (int userId) throws SQLException, ClassNotFoundException {

        String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE+ " WHERE " + Const.USERS_ID + "=?";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt(1);
            }

        return 0;
    }

    public void insertTask(Task task) {
        String insert = "INSERT INTO " + Const.TASKS_TABLE + "(" + Const.USERS_ID + "," + Const.TASK_DATE + ","
                + Const.TASK_DESCRIPTION + "," + Const.TASK + "," + Const.TASK_PRIORITY
                + ")" + "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement  = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setTimestamp(2, task.getDatecreated());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getTask());
            preparedStatement.setInt(5, task.getPriority());


            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateTask(Timestamp datecreated, String description, String task, int taskId, int priority)
            throws SQLException, ClassNotFoundException {

        String query = "UPDATE tasks SET datecreated=?, description=?, task=?, priority=? WHERE taskid=?";


        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setTimestamp(1, datecreated);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, task);
        preparedStatement.setInt(4, priority);
        preparedStatement.setInt(5, taskId);
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }
}
