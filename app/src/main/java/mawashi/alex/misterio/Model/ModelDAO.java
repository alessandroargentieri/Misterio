package mawashi.alex.misterio.Model;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alessandro.argentier on 23/09/2016.
 */
public class ModelDAO {

    //costruttore
    public ModelDAO(){}



    //metodi
    //riceve un Model e crea un JSON per l'inserimento nel DB remoto... necessita di ModelToJSON()
    public void sendItemToWS(Model m){
        JSONObject JsonToSend = ModelToJson(m);

        new AsyncAggiungi().execute();

    }

    //otteniamo dal WS l'arraylist di Model... necessita di JsonToModel()
    public ArrayList<Model> getItemsFromWS(){
        ArrayList<Model> items = new ArrayList<>();

        return items;
    }


    //void di trasformazione del formato del dato
    public Model JsonToModel(JSONObject jo){
        Model m=null;

        return m;
    }

    //void di trasformazione del formato del dato
    public JSONObject ModelToJson(Model m){
        JSONObject jo = new JSONObject();
        try{
            jo.put("nome",m.getNome());
            jo.put("descrizione",m.getDescrizione());
            jo.put("prezzo",m.getPrezzo());
        }catch(Exception e){
            Log.e("ERRORE JSON","ERRORE CREAZIONE JSON: " + e.toString());
        }
        return jo;
    }


    private class AsyncAggiungi extends AsyncTask<Void,Void,Void> {

        ProgressDialog progressDialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute(){
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Contatto il Web Service");
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void...params){
            try{
                HttpPost post = new HttpPost("http://alessandroargentieri.altervista.org/ws_insert_db.php");
                HttpClient client = new DefaultHttpClient();
                String se = (JsonToSend.toString());
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("jsonarray", se));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //Riceviamo la risposta JSON del WS
                HttpResponse response  = client.execute(post);
                String result = EntityUtils.toString(response.getEntity());
                Log.e("RISPOSTA result: ", result);

            }catch(Exception e){Log.e("ERRORE","ERRORE ASYNCAGGIUNGI: " + e.toString());}
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }




}
