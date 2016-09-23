package mawashi.alex.misterio.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mawashi.alex.misterio.R;

public class ViewActivity extends AppCompatActivity {

    EditText nomeEdit;
    EditText descrizioneEdit;
    EditText prezzoEdit;
    ListView listaView;

    ArrayList <String> listaArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    JSONObject JsonToSend;// = new JSONObject();
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        nomeEdit = (EditText) findViewById(R.id.edit_nome);
        descrizioneEdit = (EditText) findViewById(R.id.edit_descrizione);
        prezzoEdit = (EditText) findViewById(R.id.edit_prezzo);
        listaView = (ListView) findViewById(R.id.lista);

        //associo l'adapter all'arrayList ancora vuoto e gli do un layout standard
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaArray);
        //setto l'adapter così formato alla listView
        listaView.setAdapter(adapter);

        context = this;

    }

    //pulsante per aggiungere un prodotto
    public void Aggiungi(View v){

        //componiamo il JSON da inviare al WS in PHP
        JsonToSend = new JSONObject();

        try{
            JsonToSend.put("nome",nomeEdit.getText().toString());
            JsonToSend.put("descrizione",descrizioneEdit.getText().toString());
            JsonToSend.put("prezzo",prezzoEdit.getText().toString());
        }catch(Exception e){Log.e("ERRORE JSON","ERRORE CREAZIONE JSON: " + e.toString());}

        new AsyncAggiungi().execute();

    }

    //pulsante per aggiornare la lista
    public void Aggiorna(View v){
        new AsyncAggiorna().execute();

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



    private class AsyncAggiorna extends AsyncTask<Void,Void,Void>{

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
                HttpGet get = new HttpGet("http://alessandroargentieri.altervista.org/ws_db.php");
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(get);

                String result = EntityUtils.toString(response.getEntity());
                Log.e("RESPONSE", "GET RESPONSE: " + result);
                listaArray.clear();
                //adesso facciamo un bel parsing dei risultati e visualizziamo tutto nella lista
                JSONObject JO = new JSONObject(result);
                JSONArray ja = JO.getJSONArray("result");
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String id_art = jo.getString("id_articolo");
                    String nome_art = jo.getString("nome");
                    String descr_art = jo.getString("descrizione");
                    String prezzo_art = jo.getString("prezzo");
                    listaArray.add("ID: " + id_art + "\nNOME: " + nome_art + "\nDESCRIZIONE: " + descr_art + "\nPREZZO: " + prezzo_art);
                }



            }catch(Exception e){Log.e("ERRORE","ERRORE ASYNCAGGIORNA: " + e.toString());}
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            //chiedo all'adapter di aggiornare la listView alla luce dell'aggiornamento effettuato
            adapter.notifyDataSetChanged();
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }


}


/* FORMATO DEL MESSAGGIO JSON CHE QUESTA APP RICEVE E DI CUI SI DEVE FARE IL PARSING:

{"result":[
			{
			 "id_articolo":"1",
			 "nome":"maglietta",
			 "descrizione":"maglietta colorata sono disponibili varie taglie e vari colori, tra cui il rosso, il blu, il nero, il giallo e il bianco. Cotone 100 per cento.",
			 "prezzo":"15.99"
			},
			{
			 "id_articolo":"2",
			 "nome":"pantaloncino",
			 "descrizione":"pantaloncino di cotone con varie taglie e risvoltino sotto la coscia. E' presente nelle colorazioni: giallo, amaranto, blu notte, grigio tempesta, ocra gialla.",
			 "prezzo":"13.45"
			},
			{
			 "id_articolo":"3",
			 "nome":"cannottiera",
			 "descrizione":"cannottiera di cotone presente nelle taglie S, M, L, XL, XXL. I colori sono il nero, il bianco e il grigio scuro.",
			 "prezzo":"4.99"
			}
		  ]
}
 */



/* Pagina PHP presente sul server che permette l'inserimento del nuovo dato
<?php

//$nome         = "giacca";
//$descrizione  = "giacca seta finemente rifinita";
//$prezzoString = "149.99";
//$prezzo 	  = floatval($prezzoString);

//$nome         = $_POST['nome'];
//$descrizione  = $_POST['descrizione'];
//$prezzoString = $_POST['prezzo'];
//$prezzo 	  = floatval($prezzoString);


$JsonArray = $_POST["jsonarray"];
   // echo 'risposta: '.$JsonArray;

    $jsonObject = json_decode($JsonArray);
    $nome = $jsonObject->nome;
    $descrizione = $jsonObject->descrizione;
    $prezzo = floatval($jsonObject->prezzo);

$conn = mysql_connect('localhost', 'alessandroargentieri', '');
mysql_select_db('my_alessandroargentieri');

$query = "INSERT INTO tabella_articoli (nome,descrizione,prezzo)
 					 VALUES ('".$nome."','".$descrizione."',".$prezzo.")";

$cur = mysql_query ($query);

//$row = mysql_fetch_array($cur);
//echo "QUERY: ".$query;

mysql_close($conn);

?>
 */

/* Pagina PHP presente sul server che permette l'invio degli articoli presenti sul DB

<?php

$conn = mysql_connect('localhost', 'alessandroargentieri', '');
mysql_select_db('my_alessandroargentieri');

$cur = mysql_query ("
	SELECT id_articolo, nome, descrizione, prezzo
	FROM tabella_articoli
");
$result = '{"result":[';
while ($riga = mysql_fetch_row($cur)){
    $result = $result.'{"id_articolo":"'.$riga[0].'","nome":"'.$riga[1].'","descrizione":"'.$riga[2].'","prezzo":"'.$riga[3].'"},';
	//echo "</br></br>$riga[0]</br>$riga[1]</br>$riga[2]</br>$riga[3]</br>";
}
$result = eliminaUltimo($result);
$result = $result.']}';
    echo $result;
mysql_close($conn);

function eliminaUltimo($stringa){
	return substr($stringa, 0, strlen($stringa)-1);
}

?>

 */