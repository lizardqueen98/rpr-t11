package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class DodajDrzavuKontroler {
    public TextField imeDrzave;
    public TextField glavniGrad;
    private GeografijaDAO gdao;

    DodajDrzavuKontroler(GeografijaDAO gdao){
        this.gdao=gdao;
    }

    public void OK(ActionEvent actionEvent) {
    }
}