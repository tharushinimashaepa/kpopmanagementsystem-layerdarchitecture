package lk.ijse.layerd.controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class dashboardformcontroller {

    @FXML
    private AnchorPane DashBordPane;

    @FXML
    private Button btnEvent;

    @FXML
    private Button btnIdolGroup;

    @FXML
    private Button btnMerchandice;

    @FXML
    private Button btnMusic;

    @FXML
    private Button btnMusicproducer;

    @FXML
    private Button btneventmanager;

    @FXML
    private AnchorPane subPane;


    private void setUI(String s) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/"+s));
        Pane registerpane = fxmlLoader.load();

        try {
            subPane.getChildren().clear();
            subPane.getChildren().setAll(registerpane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @FXML
    void btnEventOnAction(ActionEvent event) throws IOException {
        setUI("EventForm.fxml");

    }


    public void setEventForm(String EventForm) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EventForm.fxml"));
        Pane EventFormPane = fxmlLoader.load();
        try {
            subPane.getChildren().clear();
            subPane.getChildren().setAll(EventFormPane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }





    @FXML
    void btnFansClubOnAction(ActionEvent event) throws IOException {
        setUI("FansClubForm.fxml");
    }

    private void setFansClubForm(String FansClubForm) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FansClubForm.fxml"));
        Pane FansclubPane = fxmlLoader.load();
        try {
            subPane.getChildren().clear();
            subPane.getChildren().setAll(FansclubPane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @FXML
    void btnIdolOnAction(ActionEvent event) throws IOException {
        setUI("IdolForm.fxml");
    }



    @FXML
    void btnMerchandiceOnAction(ActionEvent event) throws IOException {
        setUI("MechandiceForm.fxml");
    }



    @FXML
    void btnMusicOnAction(ActionEvent event) throws IOException {
        setUI("MusicForm.fxml");


    }

    @FXML
    void btnMusicProducerOnAction(ActionEvent event) throws IOException {
        setUI("MusicProducerForm.fxml");



    }

}

