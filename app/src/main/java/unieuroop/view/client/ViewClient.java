package unieuroop.view.client;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.checkerframework.common.returnsreceiver.qual.This;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import unieuroop.controller.client.ControllerClientImpl;
import unieuroop.model.person.Client;
import unieuroop.view.menu.ViewMainMenu;

public class ViewClient implements Initializable {

    @FXML
    private ListView<Client> listClients;
    @FXML 
    private TextField tbxName;
    @FXML 
    private TextField tbxSurname;
    @FXML 
    private DatePicker dtBirthday;
    @FXML 
    private TextField tbxCode;
    @FXML
    private Button btnAddClient;
    @FXML
    private Button btnEditClient;
    @FXML
    private Button btnDeleteClient;
    private ObservableList<Client> items;
    private final ViewMainMenu viewMenu;
    private final ControllerClientImpl controller;
    private final Client c1 = new Client("Mario", "Rossi", LocalDate.of(2000, 1, 10), 11);
    private final Client c2 = new Client("Luigi", "Verdi", LocalDate.of(1999, 2, 15), 12);
    private final Client c3 = new Client("Marco", "Bianchi", LocalDate.of(2002, 3, 16), 13);

    public ViewClient(final ViewMainMenu view, final ControllerClientImpl controller) {
        this.viewMenu = view;
        this.controller = controller;
        this.items.add(c1);
        this.items.add(c2);
        this.items.add(c3);
        this.listClients.setItems(items);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        btnAddClient.setOnMouseClicked((e) -> {
            try {
                this.items.add(this.controller.addClient(this.tbxName.getText(), this.tbxSurname.getText(), this.dtBirthday.getValue()));
                this.listClients.setItems(items);
            } catch (IllegalArgumentException illegalExc) {
                illegalExc.printStackTrace();
            }
        });

        btnEditClient.setOnMouseClicked((e) -> {
            this.controller.editClient();
        });

        btnDeleteClient.setOnMouseClicked((e) -> {
            final Client client = this.listClients.getSelectionModel().getSelectedItem();
            this.controller.deleteClient(client);
            this.items.remove(client);
            this.listClients.setItems(items);
        });
    }
}
