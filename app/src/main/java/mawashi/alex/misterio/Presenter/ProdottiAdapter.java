package mawashi.alex.misterio.Presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mawashi.alex.misterio.Model.Model;
import mawashi.alex.misterio.R;

/**
 * Created by alessandro.argentier on 08/09/2016.
 * Ovviamente in questo caso non è possibile utilizzare codice Java puro e siamo costretti a ricevere
 * come parametri View e Context. Pertanto, utilizziamo delle WeakReference
 */

public class ProdottiAdapter extends ArrayAdapter<Model> {

    private Context mContext;
    private ArrayList<Model> prodottiArrayList;

    private String nome;
    private String descrizione;
    private String prezzo;


    public ProdottiAdapter(Context mContext, ArrayList<Model> arrayList) {
        super(mContext, R.layout.custom_list_item, arrayList);
        this.mContext = mContext;
        this.prodottiArrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //1. Create inflater
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //2. Get the row View from inflater
        View rowView = inflater.inflate(R.layout.custom_list_item, parent, false);
        //3. Get the elements of custom_list_item
        TextView nome_txt = (TextView) rowView.findViewById(R.id.nomeText);
        TextView descrizione_txt = (TextView) rowView.findViewById(R.id.descrizioneText);
        TextView prezzo_txt = (TextView) rowView.findViewById(R.id.prezzoText);

        //4.fill the row
        Model item = prodottiArrayList.get(position);
        nome_txt.setText(item.getNome());
        descrizione_txt.setText(item.getDescrizione());
        prezzo_txt.setText(item.getPrezzo());

        //5.row built, it can be returned
        return rowView;
    }


}