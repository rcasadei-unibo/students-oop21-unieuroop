package unieuroop.view.staff;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import unieuroop.controller.department.ControllerDepartmentImpl;
import unieuroop.controller.staff.ControllerStaffImpl;
import unieuroop.model.department.Department;
import unieuroop.model.person.Staff;

public final class ViewEditStaff implements Initializable {

    @FXML private ListView<Staff> listAddStaff;
    @FXML private ListView<Staff> listRemoveStaff;
    @FXML private Button buttonAddStaff;
    @FXML private Button buttonRemoveStaff;

    private final Set<Staff> selectedAddStaff = new HashSet<>();
    private final Set<Staff> selectedRemoveStaff = new HashSet<>();
    private final ControllerStaffImpl controllerStaff;
    private final ControllerDepartmentImpl controllerDepartment;
    private final Department department;
    public ViewEditStaff(final ControllerStaffImpl controllerStaff, final ControllerDepartmentImpl controllerDepartment, 
            final Department department) {
        this.controllerStaff = controllerStaff;
        this.controllerDepartment = controllerDepartment;
        this.department = department;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.listAddStaff.getItems().addAll(this.controllerStaff.getUnsignedStaff());
        this.listRemoveStaff.getItems().addAll(this.controllerDepartment.getStaffOf(department));
    }

    @FXML
    public void buttonAddHandler(final ActionEvent event) {
        try {
            this.controllerDepartment.addStaff(this.department, this.selectedAddStaff);
            this.listAddStaff.getItems().clear();
            this.listRemoveStaff.getItems().clear();
            this.listAddStaff.getItems().addAll(this.controllerStaff.getUnsignedStaff());
            this.listRemoveStaff.getItems().addAll(this.controllerDepartment.getStaffOf(department));
        } catch (IOException e) {
            final Alert alertError = new Alert(AlertType.ERROR);
            alertError.setContentText(e.getMessage());
            alertError.showAndWait();
        } catch (IllegalArgumentException ex) {
            final Alert alertError = new Alert(AlertType.ERROR);
            alertError.setContentText(ex.getMessage());
            alertError.showAndWait();
        }
    }

    @FXML
    public void buttonRemoveHandler(final ActionEvent event) {
        try {
            this.controllerDepartment.removeStaff(department, this.selectedRemoveStaff);
            this.listAddStaff.getItems().clear();
            this.listRemoveStaff.getItems().clear();
            this.listAddStaff.getItems().addAll(this.controllerStaff.getUnsignedStaff());
            this.listRemoveStaff.getItems().addAll(this.controllerDepartment.getStaffOf(department));
            final Stage currentStage = (Stage) this.buttonRemoveStaff.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            final Alert alertError = new Alert(AlertType.ERROR);
            alertError.setContentText(e.getMessage());
            alertError.showAndWait();
        } catch (IllegalArgumentException ex) {
            final Alert alertError = new Alert(AlertType.ERROR);
            alertError.setContentText(ex.getMessage());
            alertError.showAndWait();
        }
    }

    @FXML
    public void listSelectUnsignedHandler(final MouseEvent event) {
        final var staff = this.listAddStaff.getSelectionModel().getSelectedItem();
        if (!Objects.isNull(staff)) {
             this.selectedAddStaff.add(staff);
        }
    }

    @FXML
    public void listRemoveStaffHandler(final MouseEvent event) {
        final var staff = this.listRemoveStaff.getSelectionModel().getSelectedItem();
        if (!Objects.isNull(staff)) {
             this.selectedRemoveStaff.add(staff);
        }
    }
}
