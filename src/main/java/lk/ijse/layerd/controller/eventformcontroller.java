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

public class eventformcontroller {


        @FXML
        private AnchorPane EventformPane;

        @FXML
        private TableColumn<?, ?> colEvid;

        @FXML
        private TableColumn<?, ?> colgenre;

        @FXML
        private TableColumn<?, ?> colname;

        @FXML
        private TableColumn<?, ?> colprice;

        @FXML
        private TableColumn<?, ?> colvenue;

        @FXML
        private TableView<eventTm> tblevent;

        @FXML
        private TextField txtEvid;

        @FXML
        private TextField txtGenreofEvent;

        @FXML
        private TextField txtName;

        @FXML
        private TextField txtTiketPrice;

        @FXML
        private TextField txtVenue;

        public void initialize() {
            setCellValueFactory();
            loadAllEvents();
        }


        private void setCellValueFactory() {
            colEvid.setCellValueFactory(new PropertyValueFactory<>("EV ID"));
            colname.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colgenre.setCellValueFactory(new PropertyValueFactory<>("GENRE"));
            colvenue.setCellValueFactory(new PropertyValueFactory<>("VENUE"));
            colprice.setCellValueFactory(new PropertyValueFactory<>("PRICE"));
        }

        private void loadAllEvents() {
            var model = new eventModel();

            ObservableList<eventTm> obList = FXCollections.observableArrayList();

            try {
                List<eventDto> dtoList = model.getAllIdols();

                for(eventDto dto : dtoList) {
                    obList.add(
                            new eventTm(
                                    dto.getEV_id(),
                                    dto.getName(),
                                    dto.getGenre_of_event(),
                                    dto.getVenue(),
                                    dto.getPrice()
                            )
                    );
                }

                tblevent.setItems(obList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void btnSaveOnAction(ActionEvent event) {
            boolean isValid = validateEvent();
            if (isValid) {
                String EV_id = txtEvid.getText();
                String name = txtName.getText();
                String genere_of_event = txtGenreofEvent.getText();
                String venue = txtVenue.getText();
                String price = txtTiketPrice.getText();


                var dto = new eventDto(EV_id, name, genere_of_event, venue, price);

                var model = new eventModel();
                try {
                    boolean isSaved = model.saveevent(dto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "New Event Added!").show();
                        clearFields();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        }

        private boolean validateEvent() {
            String idText = txtEvid.getText();
            boolean isCustomerIDValidated = Pattern.matches("[Ev][0-9]{3,}", idText);
            if (!isCustomerIDValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid Event ID!").show();
                return false;
            }


            //validate customer title
            String name = txtName.getText();
//        boolean isCustomerTitleValidated = Pattern.compile("Mr|Mrs|Miss").matcher(titleText).matches();
            boolean isCustomerTitleValidated = Pattern.matches("[a-zA-Z]{3,}",name);
            if (!isCustomerTitleValidated) {
                new Alert(Alert.AlertType.ERROR, "Invalid Event title").show();
                return false;
            }
            return true;
        }


        @FXML
        void btnSearchOnAction(ActionEvent event) {
            String EV_id = txtEvid.getText();

            var model = new eventModel();
            try {
                eventDto dto = model.searchevent(EV_id);

                if(dto != null) {
                    fillFields(dto);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Not any events found!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        private void fillFields(eventDto dto) {
            txtEvid.setText(dto.getEV_id());
            txtName.setText(dto.getName());
            txtGenreofEvent.setText(dto.getGenre_of_event());
            txtTiketPrice.setText(dto.getPrice());
            txtVenue.setText(dto.getVenue());
        }




        @FXML
        void btnUpdateOnAction(ActionEvent event) {
            String EV_id = txtEvid.getText();
            String name = txtName.getText();
            String genere_of_event = txtGenreofEvent.getText();
            String venue  =txtVenue.getText();
            String price  =txtTiketPrice.getText();

            var dto = new eventDto(EV_id, name,genere_of_event,venue,price);

            var model = new eventModel();
            try {
                boolean isUpdated = model.updateevent(dto);
                System.out.println(isUpdated);
                if(isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Events updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }



        @FXML
        void btndeleteteOnAction(ActionEvent event) {
            String EV_id = txtEvid.getText();

            var eventModel = new eventModel();
            try {
                boolean isDeleted = eventModel.deleteevent(EV_id);

                if(isDeleted) {
                    tblevent.refresh();
                    new Alert(Alert.AlertType.CONFIRMATION, "Event deleted!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }




        void clearFields() {
            txtEvid.setText("");
            txtName.setText("");
            txtGenreofEvent.setText("");
            txtVenue.setText("");
            txtTiketPrice.setText("");
        }
        @FXML
        public void btnBackOnAction(ActionEvent actionEvent) {
            try {
                // Load the FXML for the previous view
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
                AnchorPane loadedPane = fxmlLoader.load();

                // Replace the current content in FansclubPane with the loadedPane
                EventformPane.getChildren().clear();
                EventformPane.getChildren().setAll(loadedPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

