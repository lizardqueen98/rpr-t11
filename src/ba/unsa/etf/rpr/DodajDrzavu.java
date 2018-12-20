package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DodajDrzavu {
    private Drzava drz = new Drzava();
    public DodajDrzavu(String drzava){
        drz.setNaziv(drzava);
    }
    public Button ok;
    public TextField glgr;
    private GeografijaDAO gdao = GeografijaDAO.getInstance();

    public void ok(ActionEvent actionEvent) {
    }
}
