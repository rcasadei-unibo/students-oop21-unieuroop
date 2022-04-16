package unieuroop.view.department;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import unieuroop.controller.department.ControllerDepartmentImpl;
import unieuroop.controller.serialization.Pages;
import unieuroop.controller.staff.ControllerStaffImpl;
import unieuroop.model.department.Department;
import unieuroop.view.loader.Loader;
import unieuroop.view.staff.ViewEditStaff;

public final class ViewLabelDepartment implements Initializable {

    @FXML private Button btnEditProducts;
    @FXML private Button btnEditStaff;
    @FXML private Label lblDepartment;

    private final Department department;
    private final ControllerStaffImpl controllerStaff;
    private final ControllerDepartmentImpl controllerDepartment;

    public ViewLabelDepartment(final Department department, final ControllerStaffImpl controllerStaff, final ControllerDepartmentImpl controllerDepartment) {
        this.department = department;
        this.controllerStaff = controllerStaff;
        this.controllerDepartment = controllerDepartment;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.lblDepartment.setText(this.department.toString());
    }

    @FXML
    public void buttonEditStaffHandler(final ActionEvent event) {
        try {
            final var controller = new ViewEditStaff(this.controllerStaff, this.controllerDepartment, department);
            final Stage stage = Loader.loadStage(Pages.EDIT_STAFF_DEPARTMENTS.getPath(), "Edit Staff", controller, 500, 500);
            final Stage currentStage = (Stage) this.btnEditStaff.getScene().getWindow();
            currentStage.hide();
            stage.showAndWait();
            currentStage.show();
        } catch (IOException e) {
            final Alert alertError = new Alert(AlertType.ERROR);
            alertError.setContentText(e.getMessage());
            alertError.showAndWait();
        }
    }

    @FXML
    public void buttonEditProductsHandler(final ActionEvent event) {
        
    }
}