package mawashi.alex.misterio.Model;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alessandro.argentier on 23/09/2016.
 */
public class ModelDAO {

    //costruttore
    public ModelDAO(){}

    //metodi
    public void sendItemToWS(Model m){

    }

    public ArrayList<Model> getItemsFromWS(){  //sarà un ArrayList di Model invece che un banale void
        ArrayList<Model> items = new ArrayList<>();

        return items;
    }

    public ArrayList<Model> getCandidates() {
        ArrayList<Model> result = new ArrayList<>();
        return result;
    }


    public Model JsonToModel(JSONObject jo){
        Model m=null;

        return m;
    }

    public JSONObject ModelToJson(Model m){
        JSONObject jo = null;

        return jo;
    }



}
