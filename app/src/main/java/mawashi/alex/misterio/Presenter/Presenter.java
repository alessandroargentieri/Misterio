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
        // gestisco l'evento onClick sulla riga
        //  listView.setOnItemClickListener(new ListClickHandler());

        fillCandidateList();

        prodottiAdapter.notifyDataSetChanged();

    }


    public void fillList(){
        prodottiArray.clear();
        ArrayList<Model> result;// = new ArrayList<>();
      //  result = ModelDAO.getItemsFromWS();
        result = ModelDAO.getCandidates();
        /////
      /*  candidateArray.clear();
        candidateArray.add(new CandidateModel("alessio@gioia.it", "alessio", "gioia"));
        candidateArray.add(new CandidateModel("michele@gioia.it", "michele", "gioia"));
        candidateArray.add(new CandidateModel("antonio@gioia.it", "antonio", "gioia"));
        candidateArray.add(new CandidateModel("lucia@gioia.it", "lucia", "gioia"));
        candidateArray.add(new CandidateModel("marica@gioia.it", "marica", "gioia"));
        candidateArray.add(new CandidateModel("debora@gioia.it", "debora", "gioia"));
        candidateArray.add(new CandidateModel("teo@gioia.it", "teo", "gioia"));
        candidateArray.add(new CandidateModel("elisa@gioia.it", "elisa", "gioia")); */
        /////
        for(int i=0; i<result.size();i++){
            candidateArray.add(result.get(i));
        }
    }

    public void fillSpinner(Context c, Spinner spinner){
        ArrayList<CourseModel> corsi = new ArrayList<>();
        corsi = courseDAO.getCourses();
        Log.i(TAG_PRESENTER, "Lettura corsi dal DB andato a buon fine");

        ArrayList<String> course_name = new ArrayList<>();
        for(int i=0; i<corsi.size(); i++){
            CourseModel cm = corsi.get(i);
            course_name.add(cm.getCoursename());
        }
        Log.i(TAG_PRESENTER, "Lettura nomi corsi andato a buon fine");
        //la lista nello spinner non ha un formato custom, quindi non bisogna creare una classe adapter custom
        //gli passiamo direttamente l'arraylist completo e non vuoto da riempire, quindi non si usa .notifyDataSetChanged()
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, course_name);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }



    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int pos, long arg3) {
            final CandidateModel itemclicked = candidateArray.get(pos);
            Toast.makeText(context, itemclicked.getCourse(), Toast.LENGTH_LONG).show();
        }
    }

}