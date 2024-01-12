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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class idolformcontroller {
    @FXML
    private AnchorPane IdolPane;

    @FXML
    private TableColumn<?, ?> colFandomName;

    @FXML
    private TableColumn<?, ?> colIdolId;

    @FXML
    private TableColumn<?, ?> colIdolName;

    @FXML
    private TableColumn<?, ?> colMemberCount;

    @FXML
    private TableView<idolTm> tblIdol;

    @FXML
    private TextField txtMemberCount;

    @FXML
    private TextField txtFadomName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    public void initialize() {
        setCellValueFactory();
        loadAllIdols();
    }

    private void setCellValueFactory() {
        colIdolId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colIdolName.setCellValueFactory(new PropertyValueFactory<>("Group Name"));
        colMemberCount.setCellValueFactory(new PropertyValueFactory<>("Member Co"));
        colFandomName.setCellValueFactory(new PropertyValueFactory<>("Fandom Name"));
    }

    private void loadAllIdols() {
        var model = new idolModel();

        ObservableList<idolTm> obList = FXCollections.observableArrayList();

        try {
            List<idolDto> dtoList = model.getAllIdols();

            for (idolDto dto : dtoList) {
                obList.add(
                        new idolTm(
                                dto.getI_id(),
                                dto.getName(),
                                dto.getMember_count(),
                                dto.getFandom_name()
                        )
                );
            }

            tblIdol.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String I_id = txtId.getText();

        var idolModel = new idolModel();
        try {
            boolean isDeleted = idolModel.deleteidol(I_id);

            if (isDeleted) {
                tblIdol.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "idol deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnSaveOnAcion(ActionEvent event) {
        boolean isValid = validIdol();
        if (isValid) {
            String I_id = txtId.getText();
            String name = txtName.getText();
            String member_count = txtMemberCount.getText();
            String fandom_name = txtFadomName.getText();


            var dto = new idolDto(I_id, name, member_count, fandom_name);

            var model = new idolModel();
            try {
                boolean isSaved = model.saveidol(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "idol saved!").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private boolean validIdol() {
        String idText = txtId.getText();
        boolean isCustomerIDValidated = Pattern.matches("[I][0-9]{3,}", idText);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Idol ID!").show();
            return false;
        }


        String name = txtName.getText();
        boolean isCustomerTitleValidated = Pattern.matches("[a-zA-Z]{3,}", name);
        if (!isCustomerTitleValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Idol ").show();
            return false;
        }
        return true;
    }


    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String I_id = txtId.getText();

        var model = new idolModel();
        try {
            idolDto dto = model.searchidol(I_id);

            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "anu idols not found!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(idolDto dto) {
        txtId.setText(dto.getI_id());
        txtName.setText(dto.getName());
        txtMemberCount.setText(dto.getMember_count());
        txtFadomName.setText(dto.getFandom_name());
    }


    @FXML
    void btnUpdateOnAcion(ActionEvent event) {
        String I_id = txtId.getText();
        String name = txtName.getText();
        String member_count = txtMemberCount.getText();
        String fandom_name = txtFadomName.getText();

        var dto = new idolDto(I_id, name, member_count, fandom_name);

        var model = new idolModel();
        try {
            boolean isUpdated = model.updateidol(dto);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "idol updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtMemberCount.setText("");
        txtFadomName.setText("");
    }

    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML for the previous view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
            AnchorPane loadedPane = fxmlLoader.load();

            // Replace the current content in FansclubPane with the loadedPane
            IdolPane.getChildren().clear();
            IdolPane.getChildren().setAll(loadedPane);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}


}
