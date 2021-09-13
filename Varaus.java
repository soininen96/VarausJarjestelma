package varausjarjestelma;

import java.sql.Date;
import java.util.ArrayList;

public class Varaus {

    private int varausID;
    private int asiakasID;
    private int toimitilaID;
    private Date alkupvm;
    private Date loppupvm;
    private ArrayList<Palvelu> varauksenPalvelut;
    private ArrayList<Laite> varauksenLaitteet;

    public Varaus() {
    }

    public Varaus(Date alkupvm, Date loppupvm) {
        this.alkupvm = alkupvm;
        this.loppupvm = loppupvm;
    }

    public int getVarausID() {
        return varausID;
    }

    public int getAsiakasID() {
        return asiakasID;
    }

    public int getToimitilaID() {
        return toimitilaID;
    }

    public Date getAlkupvm() {
        return alkupvm;
    }

    public Date getLoppupvm() {
        return loppupvm;
    }

    public void setAlkupvm(Date alkupvm) {
        this.alkupvm = alkupvm;
    }

    public void setLoppupvm(Date loppupvm) {
        this.loppupvm = loppupvm;
    }

    public void setAsiakasID(int asiakasID) {
        this.asiakasID = asiakasID;
    }

    public void setToimitilaID(int toimitilaID) {
        this.toimitilaID = toimitilaID;
    }

    public void setVarauksenLaitteet(ArrayList<Laite> varauksenLaitteet) {
        this.varauksenLaitteet = varauksenLaitteet;
    }

    public void setVarauksenPalvelut(ArrayList<Palvelu> varauksenPalvelut) {
        this.varauksenPalvelut = varauksenPalvelut;
    }

    public void setVarausID(int varausID) {
        this.varausID = varausID;
    }

}
