package controller;

import datastorage.DAOFactory;
import datastorage.LockedPatientDAO;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.LockedPatient;
import model.Patient;
import utils.DateConverter;
import utils.DeleteHandler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**
 * The <code>AllPatientController</code> contains the entire logic of the patient view. It determines which data is displayed and how to react to events.
 */
public class AllPatientController {
    @FXML
    private TableView<Patient> tableView;
    @FXML
    private TableColumn<Patient, Integer> colID;
    @FXML
    private TableColumn<Patient, String> colFirstName;
    @FXML
    private TableColumn<Patient, String> colSurname;
    @FXML
    private TableColumn<Patient, String> colDateOfBirth;
    @FXML
    private TableColumn<Patient, String> colCareLevel;
    @FXML
    private TableColumn<Patient, String> colRoom;

    @FXML
    Button btnLockPatient;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtSurname;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtBirthday;
    @FXML
    TextField txtCarelevel;
    @FXML
    TextField txtRoom;
    @FXML

    private final ObservableList<Patient> tableviewContent = FXCollections.observableArrayList();
    private PatientDAO patientDao;

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize() {
        readAllAndShowInTableViewWithDelete();

        this.colID.setCellValueFactory(new PropertyValueFactory<>("pid"));

        //CellValuefactory zum Anzeigen der Daten in der TableView
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        //CellFactory zum Schreiben innerhalb der Tabelle
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        this.colDateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colCareLevel.setCellValueFactory(new PropertyValueFactory<>("careLevel"));
        this.colCareLevel.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colRoom.setCellValueFactory(new PropertyValueFactory<>("roomnumber"));
        this.colRoom.setCellFactory(TextFieldTableCell.forTableColumn());

        //Anzeigen der Daten
        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * handles new firstname value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new birthdate value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditDateOfBirth(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setDateOfBirth(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new carelevel value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditCareLevel(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setCareLevel(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new roomnumber value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditRoomnumber(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setRoomnumber(event.getNewValue());
        doUpdate(event);
    }

    /**
     * updates a patient by calling the update-Method in the {@link PatientDAO}
     *
     * @param t row to be updated by the user (includes the patient)
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> t) {
        try {
            patientDao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link PatientDAO} and shows patients in the table
     */
    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.patientDao = DAOFactory.getDAOFactory().createPatientDAO();
        List<Patient> allPatients;
        try {
            allPatients = patientDao.readAll();
            for (Patient p : allPatients) {
                this.tableviewContent.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link PatientDAO} and shows patients in the table
     * but deletes Patients that will be locked or older than 10yrs
     */
    @FXML
    private void readAllAndShowInTableViewWithDelete() {
        DeleteHandler deleteHandler;
        deleteHandler = new DeleteHandler();
        try {
            deleteHandler.deleteLockedPatientAfter10Years();
            deleteHandler.autoDeletePatientsAfterLastTreatment();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.readAllAndShowInTableView();
    }

    /**
     * Wenn der Lock-Button gedr??ckt wird, wird diese Methode aufgerufen,
     * Sie l??scht einen Patienten aus der Patienten Tabelle und verschiebt ihn in die
     * Lockedpatient Tabelle.
     * @throws SQLException
     */
    @FXML
    public void handleLockPatient() throws SQLException {
        patientDao = DAOFactory.getDAOFactory().createPatientDAO();
        LockedPatientDAO lockedDao = DAOFactory.getDAOFactory().createLockedPatientDAO();
        TreatmentDAO treatmentDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        LockedPatient lockedPatient = new LockedPatient(selectedItem.getPid(), selectedItem.getFirstName(), selectedItem.getSurname(), DateConverter.convertStringToLocalDate(selectedItem.getDateOfBirth()), selectedItem.getCareLevel(), selectedItem.getRoomnumber(), LocalDate.now().plusYears(10));
        lockedDao.create(lockedPatient);
        treatmentDao.deleteByPid(selectedItem.getPid());
        patientDao.deleteById(selectedItem.getPid());
        this.readAllAndShowInTableView();
    }

    /**
     * handles a add-click-event. Creates a patient and calls the create method in the {@link PatientDAO}
     */
    @FXML
    public void handleAdd() {
        String surname = this.txtSurname.getText();
        String firstname = this.txtFirstname.getText();
        String birthday = this.txtBirthday.getText();
        LocalDate date = DateConverter.convertStringToLocalDate(birthday);
        String carelevel = this.txtCarelevel.getText();
        String room = this.txtRoom.getText();
        try {
            Patient p = new Patient(firstname, surname, date, carelevel, room);
            patientDao.create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * removes content from all textfields
     */
    private void clearTextfields() {
        this.txtFirstname.clear();
        this.txtSurname.clear();
        this.txtBirthday.clear();
        this.txtCarelevel.clear();
        this.txtRoom.clear();
    }
}