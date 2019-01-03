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
        Drzava drzava = new Drzava();
        drzava.setNaziv(imeDrzave.getText());
        drzava.setGlavniGrad(gdao.nadjiGrad(glavniGrad.getText()));
        gdao.dodajDrzavu(drzava);
    }
}
