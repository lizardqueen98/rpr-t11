package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        System.out.println(gdao.glavniGrad(grad.getText()).getNaziv());
    }

    public void dodajdrz(ActionEvent actionEvent) {
        //novi prozor
    }

    public void dodajgrd(ActionEvent actionEvent) {
        //novi prozor
    }
}
