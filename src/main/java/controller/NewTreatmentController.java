package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import utils.DateConverter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The <code>NewTreatmentController</code> contains the entire logic of the new treatment view. It determines which data is displayed and how to react to events.
 */
public class NewTreatmentController {
    @FXML
    private Label lblSurname;
    @FXML
    private Label lblFirstname;
    @FXML
    private TextField txtBegin;
    @FXML
    private TextField txtEnd;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextArea taRemarks;
    @FXML
    private DatePicker datepicker;
    @FXML
    private ComboBox<String> comboBox;

    private AllTreatmentController controller;
    private final ObservableList<String> myComboBoxData =
            FXCollections.observableArrayList();
    private Patient patient;
    private ArrayList<Caregiver> caregiverList;
    private Stage stage;

    /**
     *
     * @param controller
     * @param stage
     * @param patient
     */
    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller= controller;
        this.patient = patient;
        this.stage = stage;
        comboBox.setItems(myComboBoxData);
        comboBox.getSelectionModel().select(0);
        showPatientData();
        createComboBoxData();
    }

    /**
     * sets the label for the patients firstname and surname as String
     */
    private void showPatientData(){
        this.lblFirstname.setText(patient.getFirstName());
        this.lblSurname.setText(patient.getSurname());
    }

    private void createComboBoxData(){
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            caregiverList = (ArrayList<Caregiver>) dao.readAll();
            for (Caregiver caregiver: caregiverList) {
                this.myComboBoxData.add(caregiver.getSurname());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAdd(){
        try {
            String c = this.comboBox.getSelectionModel().getSelectedItem();
            Caregiver caregiver = searchInList(c);
            LocalDate date = this.datepicker.getValue();
            String s_begin = txtBegin.getText();
            LocalTime begin = DateConverter.convertStringToLocalTime(txtBegin.getText());
            LocalTime end = DateConverter.convertStringToLocalTime(txtEnd.getText());
            String description = txtDescription.getText();
            String remarks = taRemarks.getText();
            Treatment treatment = new Treatment(patient.getPid(), caregiver.getCid(), date,
                    begin, end, description, remarks);
            createTreatment(treatment);
            controller.readAllAndShowInTableView();
            stage.close();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Pflegekraft für die Behandlung fehlt!");
            alert.setContentText("Wählen Sie über die Combobox einen Pflegekraft aus!");
            alert.showAndWait();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Felder wurden nicht korrekt ausgefüllt!");
            alert.setContentText("Bitte füllen Sie alle Felder komplett und mit gültigen Werten aus!");
            alert.showAndWait();
        }

    }

    private Caregiver searchInList(String surname){
        for (Caregiver caregiver : this.caregiverList) {
            if (caregiver.getSurname().equals(surname)) {
                return caregiver;
            }
        }
        return null;
    }

    private void createTreatment(Treatment treatment) {
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.create(treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel(){
        stage.close();
    }
}