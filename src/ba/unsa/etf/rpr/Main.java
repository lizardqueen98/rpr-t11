package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

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
        System.out.println("Gradovi su:\n" + ispisiGradove());
        glavniGrad();
        /*
        Grad g=gdao.glavniGrad("Francuska");
        System.out.println(g.getBrojStanovnika()+" "+g.getNaziv());
        Drzava d=gdao.nadjiDrzavu("Engleska");
        System.out.println(d.getId()+" "+d.getGlavniGrad().getNaziv());*/
        /*ArrayList<Grad> gradovi = gdao.gradovi();
        for(Grad g:gradovi){
            System.out.println(g.getBrojStanovnika()+" "+g.getNaziv()+" "+g.getDrzava().getNaziv());*/
        /*GeografijaDAO dao = GeografijaDAO.getInstance();
        Grad bech = dao.glavniGrad("Austrija");
        bech.setNaziv("Vienna");
        dao.izmijeniGrad(bech);

        ArrayList<Grad> gradovi = dao.gradovi();
        System.out.println(gradovi.get(2).getNaziv());*/
    }
}
