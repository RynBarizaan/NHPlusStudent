package controller;

import datastorage.DAOFactory;
import datastorage.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hsqldb.HsqlException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginViewController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorTextField;


    @FXML
    Button loginButton;


    /**
     * Wenn der Login Button betätigt wird wird das eingegebene Passwort mit dem, in der Datenbank gespeicherten Passwort,
     * passend zum Benutzernamen verglichen. Ist das Passwort richtig wird <code>openMainWindow</code> ausgeführt.
     * Sollte das Passwort falsch sein, wird ein error ausgegeben.
     *
     * @param actionEvent
     *  CLick auf den Loginbutton
     * @throws SQLException
     *  SQL Exception kann ausgegeben werden
     */
    public void handleLoginButton(ActionEvent actionEvent) throws SQLException {
        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();
        ResultSet rs = dao.getPasswordFromUsername(this.usernameField.getText());
        try {
            rs.next();
            if (rs.getString("passwort").equals(this.passwordField.getText())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                openMainWindow(actionEvent);
            } else {
                this.errorTextField.setText("Benutzername oder Passwort falsch");
            }
        } catch (HsqlException | SQLException e) {
            this.errorTextField.setText("Benutzername oder Passwort falsch");
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
            stage.setTitle("NHPLus");
            stage.setScene(new Scene(root));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}