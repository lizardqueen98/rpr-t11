package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class DodajGradKontroler {
    public TextField imeGrada;
    public TextField brojStanovnika;
    public TextField drzava;
    private GeografijaDAO gdao;

    DodajGradKontroler(GeografijaDAO gdao){
        this.gdao=gdao;
    }

    public void OK(ActionEvent actionEvent) {

    }
}
