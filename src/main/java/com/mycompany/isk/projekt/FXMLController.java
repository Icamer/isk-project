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
import javafx.scene.shape.Line;

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
    private Label lab5;
    @FXML
    private Line lin5;
    @FXML
    private Label lab4;
    @FXML
    private Line lin4;
    @FXML
    private Label lab3;
    @FXML
    private Line lin3;
    @FXML
    private Label lab2;
    @FXML
    private Line lin2;
    @FXML
    private Label lab1;
    @FXML
    private Line lin1;
    @FXML
    private Label lab0;
    @FXML
    private Line lin0;


    @FXML
    private Spinner iterationSpinner;
    private RoutingSimModel routingSimModel;

    @FXML
    private TextField textField;
    private Integer whenBroke=Integer.MIN_VALUE;

    @FXML
    private void sim2Action(ActionEvent ae) {
        System.out.println("click me!");
        String s = textField.getCharacters().toString();
        fillData(s);
    }

    Boolean isBroken = false;
    Integer broken = 0;

    private void fillData(String fileName) {
        routingSimModel = RoutingSimModel.builder().build();
        RoutingData[] routingDataArray = new JsonMapping().getRoutingData(fileName);
        RoutingData routingData = Arrays.stream(routingDataArray).filter(x -> (x.getIteration().longValue()) == (new Long((Integer)iterationSpinner.getValue()).longValue())).findAny().get();
        routingSimModel.setRoutingData(routingData);
        routingSimModel.setSize(routingData.getRouters().size());
        clear();
        fillTables(routingSimModel);
        fillTableViews(routingSimModel);
        setAdditionalVisibilities(routingSimModel);
    }

    private void setAdditionalVisibilities(RoutingSimModel routingSimModel) {
        setVisibilities(true);

        tab5.setVisible(isSizeAboveFour(routingSimModel));
        lab5.setVisible(isSizeAboveFour(routingSimModel));
        lin5.setVisible(isSizeAboveFour(routingSimModel));
        if(routingSimModel.getRoutingData().isBroken || isBroken){
            Integer broke = makingBreaking(routingSimModel);
            hideBroken(broke);
        }
    }

    private void hideBroken(Integer broke) {
        switch (broke){
            case 0:
                lab0.setVisible(false);
                lin0.setVisible(false);
                break;
            case 1:
                lab1.setVisible(false);
                lin1.setVisible(false);
                break;
            case 2:
                lab2.setVisible(false);
                lin2.setVisible(false);
                break;
            case 3:
                lab3.setVisible(false);
                lin3.setVisible(false);
                break;
            case 4:
                lab4.setVisible(false);
                lin4.setVisible(false);
                break;

        }
    }

    private Integer makingBreaking(RoutingSimModel routingSimModel) {
        if((Integer)iterationSpinner.getValue() < whenBroke){
            broken = -1;
            isBroken = false;
            whenBroke = Integer.MIN_VALUE;
        }
        Integer broke = 0;
        if(routingSimModel.getRoutingData().isBroken){
            broke = routingSimModel.getRoutingData().getBroken();
            broken = broke;
            isBroken = routingSimModel.getRoutingData().isBroken;
            whenBroke = (Integer)iterationSpinner.getValue();
        } else {
            broke = broken;
        }
        return broke;
    }

    private void setVisibilities(Boolean visible) {
        tab1.setVisible(visible);
        tab2.setVisible(visible);
        tab3.setVisible(visible);
        tab4.setVisible(visible);

        lab0.setVisible(visible);
        lab1.setVisible(visible);
        lab2.setVisible(visible);
        lab3.setVisible(visible);
        lab4.setVisible(visible);

        lin0.setVisible(visible);
        lin1.setVisible(visible);
        lin2.setVisible(visible);
        lin3.setVisible(visible);
        lin4.setVisible(visible);

        tab5.setVisible(visible);
        lab5.setVisible(visible);
        lin5.setVisible(visible);
    }

    private void fillTableViews(RoutingSimModel routingSimModel) {
        tab1.setItems(dataTable1);
        tab2.setItems(dataTable2);
        tab3.setItems(dataTable3);
        tab4.setItems(dataTable4);
        if(isSizeAboveFour(routingSimModel)){
            tab5.setItems(dataTable5);
        }
    }

    private void fillTables(RoutingSimModel routingSimModel) {
        routingSimModel.getRoutingData().getRouters().get(0).getRoutingTable().forEach(x -> dataTable1.add(new Data(x.getNetworkDestination(), x.getMetric(), x.getThrough())));
        routingSimModel.getRoutingData().getRouters().get(1).getRoutingTable().forEach(x -> dataTable2.add(new Data(x.getNetworkDestination(), x.getMetric(), x.getThrough())));
        routingSimModel.getRoutingData().getRouters().get(2).getRoutingTable().forEach(x -> dataTable3.add(new Data(x.getNetworkDestination(), x.getMetric(), x.getThrough())));
        routingSimModel.getRoutingData().getRouters().get(3).getRoutingTable().forEach(x -> dataTable4.add(new Data(x.getNetworkDestination(), x.getMetric(), x.getThrough())));
        if(isSizeAboveFour(routingSimModel)){
            routingSimModel.getRoutingData().getRouters().get(4).getRoutingTable().forEach(x -> dataTable5.add(new Data(x.getNetworkDestination(), x.getMetric(), x.getThrough())));
        }

    }

    private boolean isSizeAboveFour(RoutingSimModel routingSimModel) {
        return routingSimModel.getSize() > 4;
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
        setVisibilities(false);
        iterationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 17, 0));
        iterationSpinner.setOnMouseReleased(x-> {
            System.out.println("SPIN ME");
            fillData(textField.getCharacters().toString());
        });


        dataTable1 = FXCollections.observableArrayList();
        dataTable2 = FXCollections.observableArrayList();
        dataTable3 = FXCollections.observableArrayList();
        dataTable4 = FXCollections.observableArrayList();
        dataTable5 = FXCollections.observableArrayList();

        createTables();
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
