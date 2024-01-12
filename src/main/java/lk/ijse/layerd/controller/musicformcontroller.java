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

public class musicformcontroller {

    @FXML
    private AnchorPane MusicPane;

    @FXML
    private TableColumn<?, ?> colGroupname;

    @FXML
    private TableColumn<?, ?> colMid;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableView<musicTm> tblMusic;

    @FXML
    private TableColumn<?, ?> colgenere;

    @FXML
    private TextField txtGenre;


    @FXML
    private TextField txtGroupname;

    @FXML
    private TextField txtMid;

    @FXML
    private TextField txtTitle;

    public void initialize() {
        setCellValueFactory();
        loadAllmusic();
    }

    private void setCellValueFactory() {
        colMid.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colGroupname.setCellValueFactory(new PropertyValueFactory<>("Group Name"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Member Co"));
        colgenere.setCellValueFactory(new PropertyValueFactory<>("GENRE"));
    }

    private void loadAllmusic() {
        var model = new musicModel();

        ObservableList<musicTm> obList = FXCollections.observableArrayList();

        try {
            List<musicDto> dtoList = model.getAllmusics();

            for(musicDto dto : dtoList) {
                obList.add(
                        new musicTm(
                                dto.getM_id(),
                                dto.getGroup_name(),
                                dto.getTitle(),
                                dto.getGenre()
                        )
                );
            }

            tblMusic.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String M_id = txtMid.getText();

        var musicmodel = new musicModel();
        try {
            boolean isDeleted = musicmodel.deletemusic(M_id);

            if(isDeleted) {
                tblMusic.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "music deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }




    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String M_id = txtMid.getText();
        String Groupname = txtGroupname.getText();
        String title = txtTitle.getText();
        String genre = txtGenre.getText();

        var dto = new musicDto(M_id,Groupname,title,genre);

        var model = new musicModel();
        try {
            boolean isSaved = model.savemusic(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "music saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }




    @FXML
    void btnSerachOnAction(ActionEvent event) {
        String M_id = txtMid.getText();

        var model = new musicModel();
        try {
            musicDto dto = model.searchmusic(M_id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, " Any Kind of Music not found!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(musicDto dto) {
        txtMid.setText(dto.getM_id());
        txtGroupname.setText(dto.getGroup_name());
        txtTitle.setText(dto.getTitle());
        txtGenre.setText(dto.getGenre());
    }





    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String M_Id = txtMid.getText();
        String Groupname = txtGroupname.getText();
        String title = txtTitle.getText();
        String genre = txtGenre.getText();
        var dto = new musicDto(M_Id,Groupname,title,genre);

        var model = new musicModel();
        try {
            boolean isUpdated = model.updatemusic(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "music updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    void clearFields() {
        txtMid.setText("");
        txtGroupname.setText("");
        txtTitle.setText("");
        txtGenre.setText("");
    }
    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML for the previous view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
            AnchorPane loadedPane = fxmlLoader.load();

            // Replace the current content in FansclubPane with the loadedPane
            MusicPane.getChildren().clear();
            MusicPane.getChildren().setAll(loadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




}
