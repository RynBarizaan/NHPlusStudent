package controller;

import datastorage.ConnectionBuilder;
import datastorage.DAOFactory;
import datastorage.UserDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;

public class LoginViewController {
    @FXML
    private BorderPane mainBorderPane;

    private Stage primaryStage;

    private UserDAO dao;

    /**
     * Wenn der Login Button betätigt wird wird das eingebene Passwort mit dem, in der Datenbank gesepicherten PAsswort,
     * passend zum Benutzernamen verglichen. Ist das PAsswort richtig wird <code>openMainWindow</code> ausgeführt.
     * Sollte das PAsswort falsch sein, wird ein error ausgegeben.
     * @param actionEvent
     * @throws SQLException
     */
    public void handleLoginButton(ActionEvent actionEvent) throws SQLException {
        this.dao = DAOFactory.getDAOFactory().createUserDAO();
        if (dao.getPasswordFromUsername("dominik.m").equals("geheim")) {
            openMainWindow(actionEvent);
        }
    }

    /**
     * Methode zum öffnen einen neuen Fensters für die MainView, sollte nur noch erfolgreichem Login genutzt werden.
     */
    private void openMainWindow(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
