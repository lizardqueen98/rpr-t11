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
        Grad grad = new Grad();
        grad.setNaziv(imeGrada.getText());
        grad.setBrojStanovnika(Integer.parseInt(brojStanovnika.getText()));
        grad.setDrzava(gdao.nadjiDrzavu(drzava.getText()));
        gdao.dodajGrad(grad);
    }
}
