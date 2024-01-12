package lk.ijse.layerd.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class merchandiseformcontroller {

    @FXML
    private AnchorPane MerchandisePane;

    @FXML
    private TableColumn<?, ?> colprice;

    @FXML
    private TableColumn<?, ?> colmrid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colqty_on_hand;

    @FXML
    private TableView<merchandiseTm> tblmerchandise;

    @FXML
    private TextField txtMrid;

    @FXML
    private TextField txtname;


    @FXML
    private TextField txtqty_on_hand;
    @FXML
    private TextField txtprice;

    public void initialize() {
        setCellValueFactory();
        loadAllIdols();
    }

    private void setCellValueFactory() {
        colmrid.setCellValueFactory(new PropertyValueFactory<>("MR ID"));
        colname.setCellValueFactory(new PropertyValueFactory<>("NAME"));
        colqty_on_hand.setCellValueFactory(new PropertyValueFactory<>("QTY ON HAND"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("PRICE"));

    }

    private void loadAllIdols() {
        var model = new mechandiseModel();

        ObservableList<merchandiseTm> obList = FXCollections.observableArrayList();

        try {
            List<mechandiseDto> dtoList = model.getallmerchandise();

            for (mechandiseDto dto : dtoList) {
                obList.add(
                        new merchandiseTm(
                                dto.getMR_id(),
                                dto.getName(),
                                dto.getQty_on_hand(),
                                dto.getPrice()
                        )
                );
            }

            tblmerchandise.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String MR_id =txtMrid.getText();

        var merchandiseModel = new mechandiseModel();
        try {
            boolean isDeleted = merchandiseModel.deletemerchandise(MR_id);

            if(isDeleted) {
                tblmerchandise.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Merchandise deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }




    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isValid = validateMerchandise();
        if(isValid){

            String MR_ID = txtMrid.getText();
            String name = txtname.getText();
            String qty_on_hand = txtqty_on_hand.getText();
            String price = txtprice.getText();


            var dto = new mechandiseDto(MR_ID, name, qty_on_hand, price);

            var model = new mechandiseModel();
            try {
                boolean isSaved = model.savemerchandise(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "merchandise saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void clearFields() {
        txtMrid.setText("");
        txtname.setText("");
        txtqty_on_hand.setText("");
        txtprice.setText("");
    }


    private boolean validateMerchandise() {
        String idText = txtMrid.getText();
        boolean isCustomerIDValidated = Pattern.matches("[MR][0-9]{3,}", idText);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Merchandise ID!").show();
            return false;
        }


        String name = txtname.getText();
        boolean isCustomerTitleValidated = Pattern.matches("[a-zA-Z]{3,}",name);
        if (!isCustomerTitleValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Merchandise title").show();
            return false;
        }
        return true;
    }



    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String MR_ID = txtMrid.getText();

        var model = new mechandiseModel();
        try {
            mechandiseDto dto = model.searchmechandise(MR_ID);

            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "merchandise not found!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(mechandiseDto dto) {
        txtMrid.setText(dto.getMR_id());
        txtname.setText(dto.getName());
        txtqty_on_hand.setText(dto.getQty_on_hand());
        txtprice.setText(dto.getPrice());
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String MR_id = txtMrid.getText();
        String name = txtname.getText();
        String qty_on_hand = txtqty_on_hand.getText();
        String price = txtprice.getText();

        var dto = new mechandiseDto(MR_id,name,qty_on_hand,price);

        var model = new mechandiseModel();
        try {
            boolean isUpdated = model.updatemerchandise(dto);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "merchandise updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load( "src/main/resources/reports/Merchandise Report.jrxml");
            JRDesignQuery query = new JRDesignQuery();
            query.setText("");
            jasperDesign.setQuery(query);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());

            JFrame frame = new JFrame("Jasper Report Viewer");
            JRViewer viewer = new JRViewer(jasperPrint);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(viewer);
            frame.setSize(new Dimension(1200, 800));
            frame.setVisible(true);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML for the previous view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
            AnchorPane loadedPane = fxmlLoader.load();

            // Replace the current content in FansclubPane with the loadedPane
            MerchandisePane.getChildren().clear();
            MerchandisePane.getChildren().setAll(loadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}






}
