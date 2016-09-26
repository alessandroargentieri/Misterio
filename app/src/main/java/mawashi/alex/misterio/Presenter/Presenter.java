package mawashi.alex.misterio.Presenter;

/**
 * Created by alessandro.argentier on 23/09/2016.
 */
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import mawashi.alex.misterio.Model.Model;
import mawashi.alex.misterio.Model.ModelDAO;


public class Presenter {

    public final String TAG_PRESENTER = "Presenter";
    ListView listView;
    ArrayList<Model> prodottiArray = new ArrayList<>();
    ProdottiAdapter prodottiAdapter;

    Context context;
    ModelDAO modelDAO;

    //costruttore
    public Presenter(Context context){
        this.context = context;
        modelDAO = new ModelDAO();

    }


    public void addProduct(String nome, String descrizione, String prezzo){
        Model newCandidate = new Model(nome, descrizione, prezzo);
        modelDAO.sendItemToWS(newCandidate);
        fillList();
        prodottiAdapter.notifyDataSetChanged();
    }


    public void initLista(Context c, ListView listView){
        //affidare la lista all'adapter
        this.listView = listView;
        prodottiAdapter = new ProdottiAdapter(c, prodottiArray);
        listView.setAdapter(prodottiAdapter);
        // gestisco l'evento onClick sulla riga e richiamo la descrizione dal Model
        listView.setOnItemClickListener(new ListClickHandler());
        fillList();
        prodottiAdapter.notifyDataSetChanged();

    }


    public void fillList(){
        prodottiArray.clear();
        ArrayList<Model> result = new ArrayList<>();
        result = modelDAO.getItemsFromWS();
        for(int i=0; i<result.size();i++){
            prodottiArray.add(result.get(i));
        }
    }


    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int pos, long arg3) {
            final Model itemclicked = prodottiArray.get(pos);
            Toast.makeText(context, itemclicked.getDescrizione(), Toast.LENGTH_LONG).show();
        }
    }

}