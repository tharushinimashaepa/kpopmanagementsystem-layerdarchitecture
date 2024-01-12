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

public class musicproducerformcontroller {
    @FXML
    private AnchorPane MusicProducerPane;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colmpid;

    @FXML
    private TableColumn<?, ?> colwork;

    @FXML
    private TableView<musicproducerTm> tblMusicProducer;

    @FXML
    private TextField txtmpid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtwork;

    public void initialize() {
        setCellValueFactory();
        loadAllIdols();
    }

    private void setCellValueFactory() {
        colmpid.setCellValueFactory(new PropertyValueFactory<>("MP_iD"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colwork.setCellValueFactory(new PropertyValueFactory<>("WORK"));
    }

    private void loadAllIdols() {
        var model = new musicproducerModel();

        ObservableList<musicproducerTm> obList = FXCollections.observableArrayList();

        try {
            List<musicproducerDto> dtoList = model.getAllIdols();

            for(musicproducerDto dto : dtoList) {
                obList.add(
                        new musicproducerTm(
                                dto.getMP_id(),
                                dto.getName(),
                                dto.getWork()
                        )
                );
            }

            tblMusicProducer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String MP_id = txtmpid.getText();

        var musicproducerModel = new musicproducerModel();
        try {
            boolean isDeleted = musicproducerModel.deletemusicproducer(MP_id);

            if(isDeleted) {
                tblMusicProducer.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Music Producer deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }





    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String mp_id = txtmpid.getText();
        String name = txtname.getText();
        String work = txtwork.getText();


        var dto = new musicproducerDto(mp_id,name,work);

        var model = new musicproducerModel();
        try {
            boolean isSaved = model.savemusicproducer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "music produser saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }




    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String mp_id = txtmpid.getText();

        var model = new musicproducerModel();
        try {
            musicproducerDto dto = model.searchmusicproducer(mp_id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "any music producer not found!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(musicproducerDto dto) {
        txtmpid.setText(dto.getMP_id());
        txtname.setText(dto.getName());
        txtwork.setText(dto.getWork());
    }





    @FXML
    void btnUpdateOnAtion(ActionEvent event) {
        String mp_id = txtmpid.getText();
        String name = txtname.getText();
        String work = txtwork.getText();


        var dto = new musicproducerDto(mp_id, name, work);

        var model = new musicproducerModel();
        try {
            boolean isUpdated = model.updatemusicproducer(dto);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Music Producers updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    void clearFields() {
        txtmpid.setText("");
        txtname.setText("");
        txtwork.setText("");
    }
    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML for the previous view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
            AnchorPane loadedPane = fxmlLoader.load();

            // Replace the current content in FansclubPane with the loadedPane
            MusicProducerPane.getChildren().clear();
            MusicProducerPane.getChildren().setAll(loadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


}
