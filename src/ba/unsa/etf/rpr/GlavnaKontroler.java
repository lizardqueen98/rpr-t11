package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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
    public Menu jezik;
    public TextField drzavaIzvjestaj;
    private GeografijaDAO gdao;
    private ObservableList<Grad> gradovi = FXCollections.observableArrayList();
    private ObservableList<Drzava> drzave = FXCollections.observableArrayList();
    private ResourceBundle bundle;

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
            drzave.clear();
            gradovi.clear();
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
            stage.setOnCloseRequest(event -> {
                gradovi.clear();
                gradovi.addAll(gdao.gradovi());
                stage.close();
            });
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
            stage.setOnCloseRequest(event -> {
                drzave.clear();
                drzave.addAll(gdao.drzave());
                stage.close();
            });
        }catch(Exception e){

        }

    }
    public void ispisiGradove(ActionEvent actionEvent){
        try{
            new GradoviReport().showReport(gdao.getConnection(),null);
        }catch(JRException e){
            e.printStackTrace();
        }
    }
    private void doSave(File datoteka) {
        try {
            new GradoviReport().saveAs(datoteka.getAbsolutePath(), gdao.getConnection());
        } catch (Exception e) {

        }
    }
    public void save(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter prvi = new FileChooser.ExtensionFilter("PDF", "*.pdf");
        FileChooser.ExtensionFilter drugi = new FileChooser.ExtensionFilter("DOCX", "*.docx");
        FileChooser.ExtensionFilter treci = new FileChooser.ExtensionFilter("XSLX", "*.xslx");
        fileChooser.getExtensionFilters().addAll(prvi,drugi,treci);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File fajl = fileChooser.showSaveDialog(new Stage());
        if (fajl != null)
            doSave(fajl);
    }

    public void Bosanski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        reloadScene();
    }

    public void Engleski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "EN"));
        reloadScene();
    }

    public void Njemacki(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("de", "DE"));
        reloadScene();
    }

    public void Francuski(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("fr", "FR"));
        reloadScene();
    }
    public void reloadScene(){
        try{
        bundle = ResourceBundle.getBundle("Translation");
        Scene scene = drzava.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("glavna.fxml"), bundle);
        loader.setController(this);
        scene.setRoot(loader.load());
        } catch (IOException e) {

        }
    }

    public void izvjestajZaDrzavu(ActionEvent actionEvent) {
        try{
            new GradoviReport().showReport(gdao.getConnection(), gdao.nadjiDrzavu(drzavaIzvjestaj.getText()));
        }catch(JRException e){
            e.printStackTrace();
        }
    }
}
