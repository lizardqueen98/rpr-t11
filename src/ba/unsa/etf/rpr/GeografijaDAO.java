package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.ArrayList;

public class GeografijaDAO {
    private Connection connection;
    private static GeografijaDAO instance;
    private String URL = "jdbc:sqlite:baza.db";
    private Statement statement;
    public GeografijaDAO(){
        try {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(URL);
        statement = connection.createStatement();
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println(e.getMessage());
    }
    }
    private static void initialize() {
        instance = new GeografijaDAO();
    }

    public static void removeInstance() {
        instance = null;
    }

    public static GeografijaDAO getInstance() {
        if(instance == null)
            initialize();
        return instance;
    }
    public Grad glavniGrad(String drzava){
        String upit = "SELECT glavni_grad FROM drzava WHERE naziv='"+drzava+"'";
        Grad grad = new Grad();
        try {
            ResultSet result = statement.executeQuery(upit);
            int id = result.getInt(1);
            //System.out.println(id);
            result = statement.executeQuery("SELECT naziv, broj_stanovnika, drzava FROM grad WHERE id="+id);
            grad.setId(id);
            grad.setNaziv(result.getString(1));
            grad.setBrojStanovnika(result.getInt(2));
            int idDrazve = result.getInt(3);
            result = statement.executeQuery("SELECT naziv FROM drzava WHERE id="+idDrazve);
            grad.setDrzava(result.getString(1));
        }
        catch(Exception e){
            System.out.println("Ne valja upit.");
        }
        return grad;
    }
    public void obrisiDrzavu(String drzava){

    }
    public ArrayList<Grad> gradovi(){
        ArrayList<Grad> gradovi = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM grad");
            while (result.next()) {
                Grad grad = new Grad();
                grad.setId(result.getInt(1));
                grad.setNaziv(result.getString(2));
                grad.setBrojStanovnika(result.getInt(3));
                int idDrzave = result.getInt(4);
                Statement statement1=connection.createStatement();
                ResultSet result1 = statement1.executeQuery("SELECT naziv FROM drzava WHERE id="+idDrzave);
                grad.setDrzava(result1.getString(1));
                gradovi.add(grad);
            }
        }
        catch (Exception e){
            System.out.println("Ne valja upit.");
        }
        return gradovi;
    }
    public void dodajGrad(Grad grad){

    }
    public void izmijeniGrad(Grad grad){

    }
    public Drzava nadjiDrzavu(String drzava){
        Drzava drz = new Drzava();
        try {
            ResultSet result = statement.executeQuery("SELECT id, glavni_grad FROM drzava WHERE naziv='"+drzava+"'");
            drz.setId(result.getInt(1));
            int idGrada = result.getInt(2);
            result = statement.executeQuery("SELECT naziv FROM grad WHERE id="+idGrada);
            drz.setGlavniGrad(result.getString(1));
            drz.setNaziv(drzava);
        }
        catch(Exception e){
            System.out.println("Ne valja upit.");
        }
        return drz;
    }

}
