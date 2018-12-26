package ba.unsa.etf.rpr;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class GeografijaDAO {
    private Connection connection;
    private static GeografijaDAO instance;
    private String URL = "jdbc:sqlite:baza.db";
    private PreparedStatement statement;
    private GeografijaDAO(){
        try {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(URL);
        Statement punjenje = connection.createStatement();
        punjenje.executeUpdate("delete from drzava");
        punjenje.executeUpdate("delete from grad");
        punjenje.executeUpdate("insert into drzava(id,naziv,glavni_grad) values (1,'Francuska',1)");
        punjenje.executeUpdate("insert into drzava(id,naziv,glavni_grad) values (2,'Velika Britanija',2)");
        punjenje.executeUpdate("insert into drzava(id,naziv,glavni_grad) values (3,'Austrija',3)");
        punjenje.executeUpdate("insert into grad(id,naziv,broj_stanovnika,drzava) values (1,'Pariz',2206488,1)");
        punjenje.executeUpdate("insert into grad(id,naziv,broj_stanovnika,drzava) values (2,'London',8825000,2)");
        punjenje.executeUpdate("insert into grad(id,naziv,broj_stanovnika,drzava) values (3,'Beč',1899055,3)");
        punjenje.executeUpdate("insert into grad(id,naziv,broj_stanovnika,drzava) values (4,'Manchester',545500,2)");
        punjenje.executeUpdate("insert into grad(id,naziv,broj_stanovnika,drzava) values (5,'Graz',280200,3)");

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
        Grad grad = new Grad();
        try {
            statement = connection.prepareStatement("SELECT glavni_grad FROM drzava WHERE naziv=?");
            statement.setString(1,drzava);
            ResultSet result = statement.executeQuery();
            int id = result.getInt(1);

            //System.out.println(id);
            statement = connection.prepareStatement("SELECT naziv, broj_stanovnika, drzava FROM grad WHERE id=?");
            statement.setInt(1,id);
            result = statement.executeQuery();
            grad.setId(id);
            grad.setNaziv(result.getString(1));
            grad.setBrojStanovnika(result.getInt(2));
            grad.setDrzava(nadjiDrzavu(drzava));
        }
        catch(Exception e){
            return null;
        }
        return grad;
    }
    public void obrisiDrzavu(String drzava){
        try {
            int idDrzave=nadjiDrzavu(drzava).getId();
            statement = connection.prepareStatement("delete from drzava where id=?");
            statement.setInt(1,idDrzave);
            statement.executeUpdate();
            statement = connection.prepareStatement("delete from grad where drzava=?");
            statement.setInt(1,idDrzave);
            statement.executeUpdate();
            //statement = connection.createStatement();
            //statement.executeUpdate("delete from drzava where id="+idDrzave);
            //statement = connection.createStatement();
            //statement.executeUpdate("delete from grad where drzava="+idDrzave);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<Grad> gradovi(){
        ArrayList<Grad> gradovi = new ArrayList<>();
        try {
            //statement = connection.createStatement();
            statement = connection.prepareStatement("SELECT * FROM grad order by broj_stanovnika desc");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Grad grad = new Grad();
                grad.setId(result.getInt(1));
                grad.setNaziv(result.getString(2));
                grad.setBrojStanovnika(result.getInt(3));
                int idDrzave = result.getInt(4);
                //mora se napraviti nova statement!!!
                PreparedStatement podupit = connection.prepareStatement("SELECT naziv FROM drzava WHERE id=?");
                //Statement statement1=connection.createStatement();
                podupit.setInt(1,idDrzave);
                ResultSet result1 = podupit.executeQuery();
                grad.setDrzava(nadjiDrzavu(result1.getString(1)));
                gradovi.add(grad);
            }
        }
        catch (Exception e){
            //System.out.println("Ne valja upit.");
        }
        return gradovi;
    }
    public void dodajGrad(Grad grad){
        try{
            //statement = connection.createStatement();
            statement = connection.prepareStatement("insert into grad(naziv,broj_stanovnika,drzava) values(?, ?, ?)");
            statement.setString(1,grad.getNaziv());
            statement.setInt(2,grad.getBrojStanovnika());
            statement.setInt(3,grad.getDrzava().getId());
            statement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void izmijeniGrad(Grad grad){
        try{
            //statement = connection.createStatement();
            statement = connection.prepareStatement("UPDATE grad SET naziv = ?, broj_stanovnika = ?, drzava = ? WHERE id = ?");
            statement.setString(1,grad.getNaziv());
            statement.setInt(2,grad.getBrojStanovnika());
            statement.setInt(3,grad.getDrzava().getId());
            statement.setInt(4,grad.getId());
            statement.executeUpdate();
        }
        catch(Exception e){
            //e.printStackTrace();
        }
    }
    public Drzava nadjiDrzavu(String drzava){
        Drzava drz = new Drzava();
        try {
            //ako pozivas ovu fju iz neke druge poremeti se statement pravi novu svaki put
            //Statement statement = connection.createStatement();
            statement = connection.prepareStatement("SELECT id, glavni_grad FROM drzava WHERE naziv=?");
            statement.setString(1,drzava);
            ResultSet result = statement.executeQuery();
            drz.setId(result.getInt(1));
            int idGrada = result.getInt(2);
            statement = connection.prepareStatement("SELECT * FROM grad WHERE id=?");
            statement.setInt(1,idGrada);
            result = statement.executeQuery();
            Grad grad =new Grad();
            grad.setId(result.getInt(1));
            grad.setNaziv(result.getString(2));
            grad.setBrojStanovnika(result.getInt(3));
            grad.setDrzava(drz);
            drz.setGlavniGrad(grad);
            drz.setNaziv(drzava);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Ne valja upit.");
        }
        return drz;
    }
    public void dodajDrzavu(Drzava drzava){
        try {
            //statement = connection.createStatement();
            statement = connection.prepareStatement("insert into drzava(naziv,glavni_grad) values(?, ?)");
            statement.setString(2,drzava.getNaziv());
            statement.setInt(3,drzava.getGlavniGrad().getId());
            statement.executeUpdate();
        }
        catch(Exception e){

        }
    }

}
