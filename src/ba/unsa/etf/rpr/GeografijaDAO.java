package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;

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
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(upit);
            int id = result.getInt(1);
            //System.out.println(id);
            result = statement.executeQuery("SELECT naziv, broj_stanovnika, drzava FROM grad WHERE id="+id);
            grad.setId(id);
            grad.setNaziv(result.getString(1));
            grad.setBrojStanovnika(result.getInt(2));
            grad.setDrzava(nadjiDrzavu(drzava));
        }
        catch(Exception e){
        }
        return grad;
    }
    public void obrisiDrzavu(String drzava){
        try {
            int idDrzave=nadjiDrzavu(drzava).getId();
            statement = connection.createStatement();
            statement.executeQuery("delete drzava where id="+idDrzave);
            statement.executeQuery("delete grad where drzava="+idDrzave);
        }catch(Exception e){

        }
    }
    public ArrayList<Grad> gradovi(){
        TreeSet<Grad> gradovi = new TreeSet<>();
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM grad");
            while (result.next()) {
                Grad grad = new Grad();
                grad.setId(result.getInt(1));
                grad.setNaziv(result.getString(2));
                grad.setBrojStanovnika(result.getInt(3));
                int idDrzave = result.getInt(4);
                //mora se napraviti nova statement!!!
                Statement statement1=connection.createStatement();
                ResultSet result1 = statement1.executeQuery("SELECT naziv FROM drzava WHERE id="+idDrzave);
                grad.setDrzava(nadjiDrzavu(result1.getString(1)));
                gradovi.add(grad);
            }
        }
        catch (Exception e){
            //System.out.println("Ne valja upit.");
        }
        ArrayList<Grad> listaGradova = new ArrayList<>();
        listaGradova.addAll(gradovi);
        return listaGradova;
    }
    public void dodajGrad(Grad grad){
        try{
            statement = connection.createStatement();
            statement.executeQuery("insert into grad(naziv,broj_stanovnika,drzava) values('"+grad.getNaziv()+"', "+grad.getBrojStanovnika()+", "+grad.getDrzava().getId()+")");
        }
        catch(Exception e){
            //e.printStackTrace();
        }
    }
    public void izmijeniGrad(Grad grad){
        try{
            statement = connection.createStatement();
            statement.executeQuery("UPDATE grad SET naziv='"+grad.getNaziv()+"', broj_stanovnika="+grad.getBrojStanovnika()+", drzava="+grad.getDrzava().getId()+" WHERE id="+grad.getId());
        }
        catch(Exception e){
            //e.printStackTrace();
        }
    }
    public Drzava nadjiDrzavu(String drzava){
        Drzava drz = new Drzava();
        try {
            //ako pozivas ovu fju iz neke druge poremeti se statement pravi novu svaki put
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT id, glavni_grad FROM drzava WHERE naziv='"+drzava+"'");
            drz.setId(result.getInt(1));
            int idGrada = result.getInt(2);
            result = statement.executeQuery("SELECT * FROM grad WHERE id="+idGrada);
            Grad grad =new Grad();
            grad.setId(result.getInt(1));
            grad.setNaziv(result.getString(2));
            grad.setBrojStanovnika(result.getInt(3));
            grad.setDrzava(drz);
            drz.setGlavniGrad(grad);
            drz.setNaziv(drzava);
        }
        catch(Exception e){
            //System.out.println("Ne valja upit.");
        }
        return drz;
    }
    public void dodajDrzavu(Drzava drzava){
        try {
            statement = connection.createStatement();
            statement.executeQuery("insert into drzava(naziv,glavni_grad) values('" + drzava.getNaziv() + "'," + drzava.getGlavniGrad().getId() + ")");
        }
        catch(Exception e){

        }
    }

}
