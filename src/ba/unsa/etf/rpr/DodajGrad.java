package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DodajGrad {
    private Grad grd = new Grad();
    private DodajGrad(String grad){
        grd.setNaziv(grad);
    }
    public Button ok;
    public TextField stanovnici;
    public TextField drzava;
    private GeografijaDAO gdao = GeografijaDAO.getInstance();

    public void ok(ActionEvent actionEvent) {
        grd.setBrojStanovnika(stanovnici.getText());
        grd.setDrzava(gdao.nadjiDrzavu(drzava.getText()));
        gdao.dodajGrad(grd);
    }
}
