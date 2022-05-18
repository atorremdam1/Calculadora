
package ut9.calculadora.gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import ut9.calculadora.modelo.Calculadora;

import javax.swing.*;

public class GuiCalculadora extends Application {

    private Calculadora calculadora; // el modelo
    private TextField txtNumero1;
    private TextField txtNumero2;
    private RadioButton rbtSuma;
    private RadioButton rbtResta;
    private RadioButton rbtProducto;
    private RadioButton rbtDivision;
    private Button btnCalcular;
    private Label lblResultado;

    @Override
    public void start(Stage stage) {

        this.calculadora = new Calculadora();
        BorderPane root = crearGui();

        Scene scene = new Scene(root, 600, 300);
        stage.setScene(scene);
        stage.setTitle("- Calculadora sencilla -");
        scene.getStylesheets().add(
                getClass().getResource("/application.css")
                        .toExternalForm());
        stage.show();
    }

    private BorderPane crearGui() {

        BorderPane panel = new BorderPane();
        panel.setPadding(new Insets(0));
        panel.setMaxWidth(Integer.MAX_VALUE);

        Label lblTitulo = new Label("Calculadora");
        lblTitulo.setId("titulo");
        lblTitulo.setMaxWidth(Integer.MAX_VALUE);

        lblResultado = new Label("Resultado");
        lblResultado.setId("resultado");
        lblResultado.setMaxWidth(Integer.MAX_VALUE);

        panel.setTop(lblTitulo);
        panel.setCenter(crearGrid());
        panel.setBottom(lblResultado);


        return panel;
    }

    private GridPane crearGrid() {

        GridPane panel = new GridPane();
        panel.setPadding(new Insets(10,5,10,5));
        panel.setHgap(8);
        panel.setVgap(8);
        panel.setAlignment(Pos.CENTER);
        ColumnConstraints col1 =  new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        col1.setPercentWidth(20);
        col1.setHalignment(HPos.RIGHT);
        col2.setPercentWidth(60);
        col3.setPercentWidth(20);
        panel.getColumnConstraints().addAll(col1,col2,col3);
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow(Priority.ALWAYS);
        row2.setVgrow(Priority.ALWAYS);
        row3.setVgrow(Priority.ALWAYS);
        panel.getRowConstraints().addAll(row1,row2,row3);

        Label lblNum1 = new Label("Numero 1");

        Label lblNum2 = new Label("Numero 2");

        txtNumero1 = new TextField();
        txtNumero1.setMaxWidth(Integer.MAX_VALUE);

        txtNumero2 = new TextField();
        txtNumero2.setMaxWidth(Integer.MAX_VALUE);

        btnCalcular = new Button("Calcular");
        btnCalcular.getStyleClass().add("button");
        btnCalcular.setMaxHeight(Integer.MAX_VALUE);
        btnCalcular.setOnAction(event -> calcular());

        panel.add(lblNum1,0,0);
        panel.add(txtNumero1,1,0);
        panel.add(lblNum2,0,1);
        panel.add(txtNumero2,1,1);
        panel.add(btnCalcular,2,0,1,3);
        panel.add(crearPanelBotonesOperaciones(),1,2);

        return panel;
    }

    private HBox crearPanelBotonesOperaciones() {

        HBox panel = new HBox();
        panel.setSpacing(10);
        panel.setAlignment(Pos.CENTER);

        rbtSuma = new RadioButton("Suma");
        rbtResta = new RadioButton("Resta");
        rbtProducto = new RadioButton("Producto");
        rbtDivision = new RadioButton("División entera");
        rbtSuma.setSelected(true);

        ToggleGroup group = new ToggleGroup();
        rbtSuma.setToggleGroup(group);
        rbtResta.setToggleGroup(group);
        rbtDivision.setToggleGroup(group);
        rbtProducto.setToggleGroup(group);

        panel.getChildren().addAll(rbtSuma,rbtResta,rbtProducto,rbtDivision);

        return panel;
    }

    private void calcular() {

        Integer num1 = null;
        Integer num2 = null;
        try {
            num1 = Integer.parseInt(txtNumero1.getText());
            num2 = Integer.parseInt(txtNumero2.getText());

        if (rbtSuma.isSelected()) {
            lblResultado.setText("Suma: " + calculadora.sumar(num1,num2));
            cogerFoco(txtNumero1);
        }
        else if (rbtResta.isSelected()) {
            lblResultado.setText("Resta: " + calculadora.restar(num1,num2));
            cogerFoco(txtNumero1);
        }
        else if (rbtProducto.isSelected()) {
            lblResultado.setText("Producto: " + calculadora.multiplicar(num1,num2));
            cogerFoco(txtNumero1);
        }
        else if (rbtDivision.isSelected()) {
            try {
                lblResultado.setText("División entera: " + calculadora.dividir(num1, num2));
            } catch (ArithmeticException e) {
                mostrarMensaje("Introduce un numero distinto de 0");
            }

            cogerFoco(txtNumero1);
        }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error al convertir numero");
        } catch (NullPointerException e) {
            mostrarMensaje("Teclee valor");
        }
    }

    private void mostrarMensaje(String mensaje) {

        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle("Aviso al usuario/a");
        alerta.setHeaderText(mensaje);
        alerta.showAndWait();
    }

    private void cogerFoco(TextField target) {

        target.requestFocus();
        target.selectAll();

    }

    public static void main(String[] args) {

        launch(args);
    }
}
