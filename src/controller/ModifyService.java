package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AccessingDataBase;
import model.Services;

public class ModifyService {
    public TextField fieldName;
    public TextField fieldPhone;
    public TextField fieldEmail;
    public TextField fieldCity;
    public TextField fieldCP;
    public ComboBox<String> comboBoxServices;
    public Button btnModifyService;
    public Button btnClose;

    private static Services s;
    public Label labelMessageInfo;

    public void initialize() {
        btnClose.setOnAction(this::closeWindow);
        btnModifyService.setOnAction(this::updateService);

        fieldName.setText(s.getUser());
        fieldPhone.setText(String.valueOf(s.getPhone()));
        fieldEmail.setText(s.getEmail());
        fieldCity.setText(s.getCity());
        fieldCP.setText(String.valueOf(s.getCp()));
        //Rellenamos ComboBox
        comboBoxServices.getItems().addAll(
                "Reparación",
                "Mantenimiento",
                "Instalación",
                "Desarrollo Web",
                "Desarrollo Aplicaciones Móviles",
                "Desarrollo Software"
        );
        //Ponemos el foco en el elegido para modificar
        comboBoxServices.getSelectionModel().select(s.getServices());
    }


    public void modifyService(TableRow<Services> row) {
        s = row.getItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/modifyservice.fxml"));
            Parent root1 = fxmlLoader.load();
            Scene scene = new Scene(root1, 400, 430);
            Stage stage = new Stage();
            stage.setTitle("Modificar servicio");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateService(ActionEvent actionEvent) {
        //Modificación de datos
        if(actionEvent.getSource() == btnModifyService) {
            Services sModify = new Services(s.getId(), fieldName.getText(), Integer.parseInt(fieldPhone.getText()), fieldEmail.getText(), fieldCity.getText(), Integer.parseInt(fieldCP.getText()), comboBoxServices.getValue());
            //Comprobamos si ha modificado datos y llamamos método update
            if(!sModify.getUser().equals(s.getUser()) || sModify.getPhone() != s.getPhone() || !sModify.getEmail().equals(s.getEmail()) || !sModify.getCity().equals(s.getCity()) || sModify.getCp() != s.getCp() || !sModify.getServices().equals(s.getServices())) {
                AccessingDataBase aDB = new AccessingDataBase();
                aDB.connect();
                aDB.updateService(sModify);
                labelMessageInfo.setText("Modificado correctamente");
                System.out.println("Modificado correctamente");
                Stage stage = (Stage) btnModifyService.getScene().getWindow();
                stage.close();
            } else {
                labelMessageInfo.setText("No se han modificado datos");
                System.out.println("No has modificado datos");
            }
        }
    }

    //Método de cerrar ventana con botón
    public void closeWindow(ActionEvent actionEvent) {
        if(actionEvent.getSource() == btnClose) {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        }
    }
}
