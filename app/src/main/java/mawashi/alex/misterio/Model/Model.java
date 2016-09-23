package mawashi.alex.misterio.Model;

/**
 * Created by alessandro.argentier on 23/09/2016.
 */
public class Model {

    private String nome, descrizione, prezzo;

    //costruttore vuoto
    public Model(){}

    //costruttore pieno
    public Model(String nome, String descrizione, String prezzo){
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    //metodi getter e setter dei parametri privati

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    //********************

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    //********************

    public void setPrezzo(String prezzo){
        this.prezzo = prezzo;
    }

    public String getPrezzo(){
        return this.prezzo;
    }


}
