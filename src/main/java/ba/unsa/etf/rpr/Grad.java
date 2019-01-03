package ba.unsa.etf.rpr;

public class Grad implements Comparable<Grad>{
    private int id;
    private String naziv;
    private int brojStanovnika;
    private Drzava drzava;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    @Override
    public int compareTo(Grad o) {
        if(brojStanovnika>o.brojStanovnika) return -1;
        if(brojStanovnika==o.brojStanovnika) return 0;
        return 1;
    }
    @Override
    public String toString(){
        return naziv;
    }
}
