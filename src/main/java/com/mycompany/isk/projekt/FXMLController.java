package com.mycompany.isk.projekt;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable {


    private ObservableList<DataTable1> dataTable1;
    private ObservableList<DataTable2> dataTable2;


    @FXML
    private Label label;

    @FXML
    private Button button;

    @FXML
    private Button sim1;

    @FXML
    private Button sim2;

    @FXML
    private Button sim3;

    @FXML
    private TableView<DataTable1> table_1;

    @FXML
    private TableView<DataTable2> table_2;

    @FXML
    private TableColumn<RoutingEntry, Long> networkDestination;

    @FXML
    private TableColumn<RoutingEntry, Long> metric;

    @FXML
    private TableColumn<RoutingEntry, Long> through;

    @FXML
    private TableColumn<RoutingEntry, Long> routerId;

    @FXML
    private TableColumn<RoutingEntry, String> linkIds;

    @FXML
    private Spinner spinner;
    @FXML
    private Spinner iterationSpinner;
    @FXML
    private Spinner breakSpinner;

    RoutingSimModel routingSimModel;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Iterations: " + spinner.getValue());
        routingSimModel = getRoutingSimModel((Integer) spinner.getValue(), (Integer) breakSpinner.getValue());
        new JsonMapping().saveRoutingData(routingSimModel.getRoutingData());
//        dataTable1.clear();
        dataTable2.clear();
        fillSecondTable(routingSimModel);

    }

    @FXML
    private void sim1Action(ActionEvent event) {
        sim2.setDisable(!sim2.isDisable());
        sim3.setDisable(!sim3.isDisable());
        button.setDisable(!button.isDisable());
        breakSpinner.setDisable(!breakSpinner.isDisable());
        spinner.setDisable(!spinner.isDisable());
        iterationSpinner.setDisable(!iterationSpinner.isDisable());
    }

    @FXML
    private void sim2Action(ActionEvent ae) {
        System.out.println("You clicked me!");
        label.setText("Iterations: " + spinner.getValue());
        routingSimModel = RoutingSimModel.builder().build();
        RoutingData[] routingData = new JsonMapping().getRoutingData(true);
        RoutingData routingData1 = Arrays.stream(routingData).filter(x -> (x.getIteration().longValue()) == (new Long((Integer)iterationSpinner.getValue()).longValue())).findAny().get();
        routingSimModel.setRoutingData(routingData1);
        dataTable1.clear();
        dataTable2.clear();
        fillTableOne(routingSimModel);
        fillSecondTable(routingSimModel);
    }

    @FXML
    private void sim3Action(ActionEvent ae) {
        System.out.println("You clicked me!");
        label.setText("Iterations: " + spinner.getValue());
        routingSimModel = RoutingSimModel.builder().build();
        new JsonMapping().getRoutingData(false);
        dataTable1.clear();
        dataTable2.clear();
        fillSecondTable(routingSimModel);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoutingSimModel rsm = getRoutingSimModel((Integer) breakSpinner.getValue(), -1);


        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));
        breakSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, rsm.getRoutingData().getLinks().size(), 0));
        iterationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));

        dataTable1 = FXCollections.observableArrayList();
        dataTable2 = FXCollections.observableArrayList();

        createTable(rsm);
    }

    private RoutingSimModel getRoutingSimModel(Integer iteration, Integer brokenLinkId) {
        JsonMapping jsonMapping = new JsonMapping();
        RoutingData routingData = jsonMapping.getRoutingData();
        routingData.fillRoutingTables();

        RoutingSimModel rsm = new RoutingSimModel().builder().routingData(routingData).build();
        rsm.doSim(iteration != null ? iteration : 0, brokenLinkId == null || brokenLinkId < 0 ? 100 : 0, brokenLinkId != null ? brokenLinkId : 0);
        return rsm;
    }

    private void createTable(RoutingSimModel rsm) {
        networkDestination.setCellValueFactory(new PropertyValueFactory<>("networkDestination"));
        metric.setCellValueFactory(new PropertyValueFactory<>("metric"));

        through.setCellValueFactory(new PropertyValueFactory<>("through"));
        routerId.setCellValueFactory(new PropertyValueFactory<>("routerId"));
        linkIds.setCellValueFactory(new PropertyValueFactory<>("linkIds"));

        fillTableOne(rsm);
    }

    private void fillTableOne(RoutingSimModel rsm) {
        for (Router router : rsm.getRoutingData().getRouters()) {
            List<Link> collect = rsm.getRoutingData().getLinks().stream().filter(x -> x.getIdServerTwo().equals(router.getId()) || x.getIdServerOne().equals(router.getId())).collect(Collectors.toList());
            StringBuilder ids = new StringBuilder();
            ids.append("links: ");
            collect.forEach(x -> ids.append(x.getLinkId() + ", "));
            dataTable1.add(new DataTable1(router.getId(), ids.toString()));
        }
        table_1.setItems(dataTable1);
        table_1.setOnMouseClicked(x -> {
            if (routingSimModel == null) {
                fillSecondTable(rsm);
            } else {
                fillSecondTable(routingSimModel);
            }
        });
    }

    private void fillSecondTable(RoutingSimModel rsm) {
        dataTable2.clear();
        for (DataTable1 dataTable1 : table_1.getSelectionModel().getSelectedItems()) {
            rsm.getRoutingData().getRouters().stream().filter(x -> x.getId().equals(dataTable1.getRouterId())).forEach(x -> {
                x.getRoutingTable().forEach(y -> {
                    dataTable2.add(new DataTable2(y.getNetworkDestination(), y.getMetric(), y.getThrough()));
                });
            });
        }
        table_2.setItems(dataTable2);
    }

}
