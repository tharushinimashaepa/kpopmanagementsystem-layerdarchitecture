package lk.ijse.layerd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class loginformcontroller {

    @FXML
    private AnchorPane LoginPane;

    @FXML
    private TextField txtU_Id;

    @FXML
    private TextField txtname;

    private String UserName= "tharushi";
    private String password = "1234";
    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DashBoardform.fxml"));
            AnchorPane loadedPane = fxmlLoader.load();
            AnchorPane anchorPane = new AnchorPane(loadedPane);
            LoginPane.getChildren().clear();
            LoginPane.getChildren().setAll(anchorPane);
        }catch (Exception e) {
            e.printStackTrace();
        }


    }


    @FXML
    void btnSignUpOnAction(ActionEvent event) {


    }

}


}
