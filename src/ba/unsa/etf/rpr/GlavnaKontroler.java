package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavnaKontroler implements Initializable {
    public TableView<Drzava> drzava;
    public TableView<Grad> grad;
    public TableColumn nazivDrzave;
    public TableColumn<Grad,String> glavniGrad;
    public TableColumn imeGrada;
    public TableColumn brojStanovnika;
    public TableColumn<Drzava,String> Drzava;
    public MenuButton izbor;
    private GeografijaDAO gdao;
    private ObservableList<Grad> gradovi = FXCollections.observableArrayList();
    private ObservableList<Drzava> drzave = FXCollections.observableArrayList();

    GlavnaKontroler(GeografijaDAO gdao){
        this.gdao=gdao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            imeGrada.setCellValueFactory(new PropertyValueFactory<>("naziv"));
            brojStanovnika.setCellValueFactory(new PropertyValueFactory<>("brojStanovnika"));
            Drzava.setCellValueFactory(new PropertyValueFactory<>("drzava"));
            glavniGrad.setCellValueFactory(new PropertyValueFactory<>("glavniGrad"));
            nazivDrzave.setCellValueFactory(new PropertyValueFactory<>("naziv"));
            gradovi.addAll(gdao.gradovi());
            drzave.addAll(gdao.drzave());
            grad.setItems(gradovi);
            drzava.setItems(drzave);
        }catch(Exception e){

        }
    }
    public void izbrisiGrad(){
        gdao.obrisiGrad(grad.getSelectionModel().getSelectedItem().getNaziv());
        gradovi.clear();
        gradovi.addAll(gdao.gradovi());
    }
   public void izbrisiDrzavu(){
        gdao.obrisiDrzavu(drzava.getSelectionModel().getSelectedItem().getNaziv());
        drzave.clear();
        gradovi.clear();
        drzave.addAll(gdao.drzave());
        gradovi.addAll(gdao.gradovi());
        grad.setItems(gradovi);
        drzava.setItems(drzave);
   }

    public void dodajGrad(ActionEvent actionEvent) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("dodajGrad.fxml"));
            GeografijaDAO gdao = GeografijaDAO.getInstance();
            loader1.setController(new DodajGradKontroler(gdao));
            Parent root1 = loader1.load();
            Stage stage = new Stage();
            stage.setTitle("Dodaj grad");
            stage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }catch(Exception e){

        }
    }

    public void dodajDrzavu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("dodajDrzavu.fxml"));
            GeografijaDAO gdao = GeografijaDAO.getInstance();
            loader1.setController(new DodajDrzavuKontroler(gdao));
            Parent root1 = loader1.load();
            Stage stage = new Stage();
            stage.setTitle("Dodaj drzavu");
            stage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        }catch(Exception e){

        }
    }
}
