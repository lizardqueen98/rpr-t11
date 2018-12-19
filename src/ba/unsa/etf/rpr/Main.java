package ba.unsa.etf.rpr;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static String ispisiGradove(){
        return null;
    }
    public static void glavniGrad(){

    }
    public static void main(String[] args) {
        /*System.out.println("Gradovi su:\n" + ispisiGradove());
        glavniGrad();*/
        GeografijaDAO gdao = new GeografijaDAO();/*
        Grad g=gdao.glavniGrad("Francuska");
        System.out.println(g.getBrojStanovnika()+" "+g.getNaziv());
        Drzava d=gdao.nadjiDrzavu("Engleska");
        System.out.println(d.getId()+" "+d.getGlavniGrad());*/
        ArrayList<Grad> gradovi = gdao.gradovi();
        for(Grad g:gradovi){
            System.out.println(g.getBrojStanovnika()+" "+g.getNaziv()+" "+g.getDrzava());
        }
    }
}
