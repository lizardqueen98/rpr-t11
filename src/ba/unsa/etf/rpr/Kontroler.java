package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Kontroler {
    public TextField grad;
    public TextField drzava;
    public Button izbrisi;
    public Button glgr;
    public Button dodajdrz;
    public Button dodajgrd;
    private GeografijaDAO gdao = GeografijaDAO.getInstance();

    public void izbrisi(ActionEvent actionEvent) {
        gdao.obrisiDrzavu(drzava.getText());
    }

    public void glgr(ActionEvent actionEvent) {
        System.out.println(gdao.glavniGrad(drzava.getText()).getNaziv());
    }

    public void dodajdrz(ActionEvent actionEvent) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("dodajdrz.fxml"));
            loader1.setController(new DodajDrzavu(drzava.getText()));
            Parent root1 = loader1.load();
            Stage stage = new Stage();
            stage.setTitle("Meni");
            stage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }
        catch(Exception e){

        }
    }

    public void dodajgrd(ActionEvent actionEvent) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("dodajgrd.fxml"));
            loader1.setController(new DodajGrad(grad.getText()));
            Parent root1 = loader1.load();
            Stage stage = new Stage();
            stage.setTitle("Meni");
            stage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }
        catch(Exception e){

        }
    }
}
