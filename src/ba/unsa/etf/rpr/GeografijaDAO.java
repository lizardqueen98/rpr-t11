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
            /*Ne radi mi ne mogu skinuti ono sto treba jer nemam acc
        String url = "jdbc:oracle:thin:@ora.db.lab.ri.etf.unsa.ba:1521:ETFLAB";
        String username = "NB18067";
        String password = "p4ltBwq8";
        connection = DriverManager.getConnection(url, username, password);*/
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
           // e.printStackTrace();
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
                try{
                    grad.setDrzava(nadjiDrzavu(result1.getString(1)));
                }catch(Exception e){
                    grad.setDrzava(null);
                }
                gradovi.add(grad);
            }
        }
        catch (Exception e){
            e.printStackTrace();
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
            if(grad.getDrzava()!=null)
                statement.setInt(3,grad.getDrzava().getId());
            else statement.setNull(3,Types.INTEGER);
            statement.executeUpdate();
        }
        catch(Exception e){
            //e.printStackTrace();
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
            try{
                grad.setId(result.getInt(1));
                grad.setNaziv(result.getString(2));
                grad.setBrojStanovnika(result.getInt(3));
                grad.setDrzava(drz);
                drz.setGlavniGrad(grad);
                drz.setNaziv(drzava);
            }catch(Exception e){
                grad=null;
                drz.setGlavniGrad(null);
                drz.setNaziv(drzava);
            }
        }
        catch(Exception e){
            //e.printStackTrace();
            return null;
        }
        return drz;
    }
    public void dodajDrzavu(Drzava drzava){
        try {
            //statement = connection.createStatement();
            statement = connection.prepareStatement("insert into drzava(naziv,glavni_grad) values(?, ?)");
            statement.setString(1,drzava.getNaziv());
            if(drzava.getGlavniGrad()!=null)
                statement.setInt(2,drzava.getGlavniGrad().getId());
            else statement.setNull(2,Types.INTEGER);
            statement.executeUpdate();
        }
        catch(Exception e){
            //e.printStackTrace();
        }
    }
    public ArrayList<Drzava> drzave(){
        ArrayList<Drzava> drzave = new ArrayList<>();
        try {
            //statement = connection.createStatement();
            statement = connection.prepareStatement("SELECT * FROM drzava");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Drzava drzava = new Drzava();
                drzava.setId(result.getInt(1));
                drzava.setNaziv(result.getString(2));
                //drzava.setGlavniGrad(result.getInt(3));
                int idGrada=result.getInt(3);
                PreparedStatement podupit = connection.prepareStatement("SELECT * FROM grad WHERE id=?");
                podupit.setInt(1,idGrada);
                ResultSet rs = podupit.executeQuery();
                Grad grad = new Grad();
                try {
                    grad.setId(rs.getInt(1));
                    grad.setNaziv(rs.getString(2));
                    grad.setBrojStanovnika(rs.getInt(3));
                    grad.setDrzava(drzava);
                    drzava.setGlavniGrad(grad);
                    drzave.add(drzava);
                }catch(Exception e){
                    grad=null;
                    drzava.setGlavniGrad(grad);
                    drzave.add(drzava);
                }
            }
        }
        catch (Exception e){
            //e.printStackTrace();
            //System.out.println("Ne valja upit.");
        }
        return drzave;
    }
    public void obrisiGrad(String grad){
        try {
            statement = connection.prepareStatement("delete from grad where naziv=?");
            statement.setString(1, grad);
            statement.executeUpdate();
        }catch(Exception e){

        }
    }
    public Grad nadjiGrad(String grad){
        Grad grd = new Grad();
        try {
            statement = connection.prepareStatement("SELECT id, broj_stanovnika, drzava FROM grad WHERE naziv=?");
            statement.setString(1, grad);
            ResultSet result = statement.executeQuery();
            grd.setNaziv(grad);
            grd.setId(result.getInt(1));
            grd.setBrojStanovnika(result.getInt(2));
            try{
            PreparedStatement st = connection.prepareStatement("select naziv from drzava where id=?");
            st.setInt(1,result.getInt(3));
            ResultSet rs = st.executeQuery();
            grd.setDrzava(nadjiDrzavu(rs.getString(1)));
            }catch(Exception e){
                grd.setDrzava(null);
            }
        }catch(Exception e){
            //e.printStackTrace();
            return null;
        }
        return grd;
    }

}
