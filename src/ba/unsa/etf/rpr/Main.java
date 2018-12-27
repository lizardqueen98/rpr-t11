package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class Main extends Application {

    public static String ispisiGradove() {
        GeografijaDAO gdao = GeografijaDAO.getInstance();
        String s=new String();
        ArrayList<Grad> gradovi = gdao.gradovi();
        for (Grad g : gradovi) {
            s+=g.getNaziv() + " (" + g.getDrzava().getNaziv() + ")" +" - "+ g.getBrojStanovnika()+"\n";
        }
        return s;
    }
    public static void glavniGrad(){
        GeografijaDAO gdao = GeografijaDAO.getInstance();
        Scanner sc = new Scanner(System.in);
        String drzava = sc.nextLine();
        Grad grad = gdao.glavniGrad(drzava);
        System.out.println("Glavni grad "+drzava+"-e je "+grad.getNaziv());
    }
    public static void main(String[] args) {
        launch(args);
        /*System.out.println("Gradovi su:\n" + ispisiGradove());
        glavniGrad();*/
        //GeografijaDAO gdao = GeografijaDAO.getInstance();
        /*Grad g=gdao.glavniGrad("Francuska");
        System.out.println(g.getBrojStanovnika()+" "+g.getNaziv());
        Drzava d=gdao.nadjiDrzavu("Velika Britanija");
        System.out.println(d.getId()+" "+d.getGlavniGrad().getNaziv());*/
        //ArrayList<Grad> gradovi = gdao.gradovi();
        /*for(Grad g:gradovi){
            System.out.println(g.getBrojStanovnika()+" "+g.getNaziv()+" "+g.getDrzava().getNaziv());}*/
        /*GeografijaDAO dao = GeografijaDAO.getInstance();*/
        /*Grad bech = gdao.glavniGrad("Austrija");
        bech.setNaziv("Vienna");
        gdao.izmijeniGrad(bech);

        ArrayList<Grad> gradovi = gdao.gradovi();
        System.out.println(gradovi.get(2).getNaziv());*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("bs", "BA"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("glavna.fxml"),bundle);
        GeografijaDAO gdao = GeografijaDAO.getInstance();
        loader1.setController(new GlavnaKontroler(gdao));
        Parent root1 = loader1.load();
        primaryStage.setTitle("Meni");
        primaryStage.setScene(new Scene(root1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }
}
