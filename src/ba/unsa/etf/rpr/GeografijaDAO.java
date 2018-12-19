package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.ArrayList;

public class GeografijaDAO {
    private Connection connection;
    private static GeografijaDAO instance;
    private String URL = "jdbc:sqlite:baza.db";
    private Statement statement;
    public GeografijaDAO(){try {
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
            grad.setDrzava(result.getInt(3));
        }
        catch(Exception e){
            System.out.println("Ne valja upit");
        }
        return grad;
    }
    public void obrisiDrzavu(String drzava){

    }
    public ArrayList<Grad> gradovi(){
        return null;
    }
    public void dodajGrad(Grad grad){

    }
    public void izmijeniGrad(Grad grad){

    }
    public Drzava nadjiDrzavu(String drzava){
        return null;
    }

}
