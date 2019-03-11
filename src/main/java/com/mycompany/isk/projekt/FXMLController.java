package com.mycompany.isk.projekt;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable {


    private ObservableList<Data> dataTable1;
    private ObservableList<Data> dataTable2;
    private ObservableList<Data> dataTable3;
    private ObservableList<Data> dataTable4;
    private ObservableList<Data> dataTable5;

    @FXML
    private TableView<Data> tab1;
    @FXML
    private TableView<Data> tab2;
    @FXML
    private TableView<Data> tab3;
    @FXML
    private TableView<Data> tab4;
    @FXML
    private TableView<Data> tab5;

    @FXML
    private TableColumn<RoutingEntry, Long> t1c1;
    @FXML
    private TableColumn<RoutingEntry, Long> t1c2;
    @FXML
    private TableColumn<RoutingEntry, Long> t1c3;

    @FXML
    private TableColumn<RoutingEntry, Long> t2c1;
    @FXML
    private TableColumn<RoutingEntry, Long> t2c2;
    @FXML
    private TableColumn<RoutingEntry, Long> t2c3;

    @FXML
    private TableColumn<RoutingEntry, Long> t3c1;
    @FXML
    private TableColumn<RoutingEntry, Long> t3c2;
    @FXML
    private TableColumn<RoutingEntry, Long> t3c3;

    @FXML
    private TableColumn<RoutingEntry, Long> t4c1;
    @FXML
    private TableColumn<RoutingEntry, Long> t4c2;
    @FXML
    private TableColumn<RoutingEntry, Long> t4c3;

    @FXML
    private TableColumn<RoutingEntry, Long> t5c1;
    @FXML
    private TableColumn<RoutingEntry, Long> t5c2;
    @FXML
    private TableColumn<RoutingEntry, Long> t5c3;

    @FXML
    private Spinner iterationSpinner;
    private RoutingSimModel routingSimModel;


    @FXML
    private void sim2Action(ActionEvent ae) {
        System.out.println("click me!");
        fillData();
    }

    private void fillData() {
        routingSimModel = RoutingSimModel.builder().build();
        RoutingData[] routingDataArray = new JsonMapping().getRoutingData(true);
        RoutingData routingData = Arrays.stream(routingDataArray).filter(x -> (x.getIteration().longValue()) == (new Long((Integer)iterationSpinner.getValue()).longValue())).findAny().get();
        routingSimModel.setRoutingData(routingData);
        clear();

    }

    private void clear() {
        dataTable1.clear();
        dataTable2.clear();
        dataTable3.clear();
        dataTable4.clear();
        dataTable5.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iterationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 17, 0));
        iterationSpinner.setOnMouseReleased(x-> {
            System.out.println("SPIN ME");
            fillData();
        });

        dataTable1 = FXCollections.observableArrayList();
        dataTable2 = FXCollections.observableArrayList();
        dataTable3 = FXCollections.observableArrayList();
        dataTable4 = FXCollections.observableArrayList();
        dataTable5 = FXCollections.observableArrayList();

        createTables();
    }

    private RoutingSimModel getRoutingSimModel(Integer iteration, Integer brokenLinkId) {
        JsonMapping jsonMapping = new JsonMapping();
        RoutingData routingData = jsonMapping.getRoutingData();
        routingData.fillRoutingTables();

        RoutingSimModel rsm = new RoutingSimModel().builder().routingData(routingData).build();
        rsm.doSim(iteration != null ? iteration : 0, brokenLinkId == null || brokenLinkId < 0 ? 100 : 0, brokenLinkId != null ? brokenLinkId : 0);
        return rsm;
    }

    private void createTables() {
        t1c1.setCellValueFactory(new PropertyValueFactory<>("networkDestination"));
        t1c2.setCellValueFactory(new PropertyValueFactory<>("metric"));
        t1c3.setCellValueFactory(new PropertyValueFactory<>("through"));

        t2c1.setCellValueFactory(new PropertyValueFactory<>("networkDestination"));
        t2c2.setCellValueFactory(new PropertyValueFactory<>("metric"));
        t2c3.setCellValueFactory(new PropertyValueFactory<>("through"));

        t3c1.setCellValueFactory(new PropertyValueFactory<>("networkDestination"));
        t3c2.setCellValueFactory(new PropertyValueFactory<>("metric"));
        t3c3.setCellValueFactory(new PropertyValueFactory<>("through"));

        t4c1.setCellValueFactory(new PropertyValueFactory<>("networkDestination"));
        t4c2.setCellValueFactory(new PropertyValueFactory<>("metric"));
        t4c3.setCellValueFactory(new PropertyValueFactory<>("through"));

        t5c1.setCellValueFactory(new PropertyValueFactory<>("networkDestination"));
        t5c2.setCellValueFactory(new PropertyValueFactory<>("metric"));
        t5c3.setCellValueFactory(new PropertyValueFactory<>("through"));
    }



}
