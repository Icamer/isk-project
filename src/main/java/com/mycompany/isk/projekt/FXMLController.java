package com.mycompany.isk.projekt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TableView tableView;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<RoutingEntry, String> networkDestination = new TableColumn<>("Z");
        networkDestination.setMinWidth(100L);
        networkDestination.setCellValueFactory(new PropertyValueFactory<>("networkDestination"));
        
//        TableColumn<RoutingEntry, String> netmask = new TableColumn<>("X");
//        netmask.setMinWidth(100L);
//        netmask.setCellValueFactory(new PropertyValueFactory<>("netmask"));
//        
//        TableColumn<RoutingEntry, String> gateway = new TableColumn<>("C");
//        gateway.setMinWidth(100L);
//        gateway.setCellValueFactory(new PropertyValueFactory<>("gateway"));
        
        TableColumn<RoutingEntry, String> interfaceId = new TableColumn<>("B");
        interfaceId.setMinWidth(100L);
        interfaceId.setCellValueFactory(new PropertyValueFactory<>("interfaceId"));
        
        TableColumn<RoutingEntry, String> metric = new TableColumn<>("D");
        metric.setMinWidth(100L);
        metric.setCellValueFactory(new PropertyValueFactory<>("metric"));

        tableView.setItems(getEntries());
        tableView.getColumns().addAll(networkDestination, interfaceId, metric);
//        tableView.getColumns().addAll(networkDestination, netmask, gateway, interfaceStr, metric);

    }

    public ObservableList<RoutingEntry> getEntries() {
        //todo
        return mockEntries();
    }

    public ObservableList<RoutingEntry> mockEntries() {
        ObservableList<RoutingEntry> entrys = FXCollections.observableArrayList();
//        entrys.add(new RoutingEntry("d1", 1L, 5L, 0L));
//        entrys.add(new RoutingEntry("r6", 0L, 10L, 0L));
        return entrys;
    }
}
