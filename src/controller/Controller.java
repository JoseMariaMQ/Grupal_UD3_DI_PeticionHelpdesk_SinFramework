package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.AccessingDataBase;
import model.Services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements EventHandler<ActionEvent> {

    public TextField textFieldName;
    public TextField textFieldPhone;
    public TextField textFieldEmail;
    public TextField textFieldCity;
    public TextField textFieldCP;
    public Button btnPetition;
    public Label labelMessage;
    public Button btnShowServices;
    public RadioButton radioReparacion;
    public RadioButton radioMantenimiento;
    public RadioButton radioInstalacion;
    public RadioButton radioWeb;
    public RadioButton radioMoviles;
    public RadioButton radioSoftware;
    @FXML
    ToggleGroup group = new ToggleGroup();

    private String nameUser;
    private int phone;
    private String email;
    private String city;
    private int cp;
    private String service;

    public void initialize() {
        btnPetition.setOnAction(this);
        btnShowServices.setOnAction(this::showServices);

        //Agrupamos radiobutton
        radioReparacion.setToggleGroup(group);
        radioReparacion.setSelected(true);
        radioMantenimiento.setToggleGroup(group);
        radioInstalacion.setToggleGroup(group);
        radioInstalacion.setToggleGroup(group);
        radioWeb.setToggleGroup(group);
        radioMoviles.setToggleGroup(group);
        radioSoftware.setToggleGroup(group);

    }

    @Override
    public void handle(ActionEvent actionEvent) {
        //Acciones cuando se pulsa el botón enviar petición
        if(actionEvent.getSource() == btnPetition) {
            RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
            //Comprobación campos de entrada vacíos
            String[] fields = new String[] {
                    textFieldName.getText(),
                    textFieldPhone.getText(),
                    textFieldEmail.getText(),
                    textFieldCity.getText(),
                    textFieldCP.getText(),
                    selectedRadioButton.getText()
            };
            //Recorremos el array de los campos y comprobamos si alguno está vacío,
            // y comprobamos el correo electrónico
            int cont = 0;
            for (String field : fields) {
                if (field.equals("")) {
                    //Mostramos texto si algún campo está vacío
                    labelMessage.setText("Tienes que rellenar todos los campos.");
                    cont++;
                } else if(cont == 0) {
                    labelMessage.setText(null);
                }
            }
            if(cont == 0 && !validateMail(textFieldEmail.getText())) {
                labelMessage.setText("El correo electrónico no es válido.");
            } else if(cont == 0 && validateMail(textFieldEmail.getText())) {
                labelMessage.setText(null);

                //Descargamos datos de los campos y convertimos al tipo de datos que necesitamos
                nameUser = textFieldName.getText();
                phone = Integer.parseInt(textFieldPhone.getText());
                city = textFieldCity.getText();
                email = textFieldEmail.getText();
                cp = Integer.parseInt(textFieldCP.getText());
                service = selectedRadioButton.getText();

                //Instanciamos servicio e introducimos los datos de los campos
                Services s = new Services(nameUser, phone, email, city, cp, service);
                //Instanciamos AccessingDataBase
                AccessingDataBase accessingDataBase = new AccessingDataBase();
                //Llamamos método connect
                accessingDataBase.connect();
                //Llamamos método que inserta datos en la BBDD
                accessingDataBase.insertService(s);
                //Mostramos texto de inserción correcta
                labelMessage.setText("Servicio enviado con éxito");

                //Vaciamos los campos una vez insertados en BBDD
                textFieldName.setText(null);
                textFieldPhone.setText(null);
                textFieldCity.setText(null);
                textFieldEmail.setText(null);
                textFieldCP.setText(null);
                radioReparacion.setSelected(true);
            }
        }

    }

    private void showServices(ActionEvent actionEvent) {
        if(actionEvent.getSource() == btnShowServices) {
            ShowServicesController sSC = new ShowServicesController();
            sSC.servicesWindow();
        }
    }

    public static boolean validateMail(String email) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

}
