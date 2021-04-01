package JCovReader;

import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

public class JCOVReader {

    private Reader fr;
    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gs = builder.create();

    private ArrayList<Case> CAL = new ArrayList<>();

    public JCOVReader(String path){
        try{
            fr=new InputStreamReader(new URL(path).openStream(), "UTF-8");
        }catch (IOException e){
            throw new ReadException("Invalid path!");
        }
    }

    public ArrayList<Case> getCAL() {
        return CAL;
    }

    public void updateData(int region){
        JsonArray jarr=gs.fromJson(fr, JsonArray.class);

        for (int i = 0; i < jarr.size()-1; i++) {
            if(jarr.get(i).getAsJsonObject().get("codice_regione").getAsString().equals(Integer.toString(region))){
                CAL.add(caseBuilder(jarr.get(i)));
            }
        }
    }

    public void updateData(){
        JsonArray jarr=gs.fromJson(fr, JsonArray.class);

        for (int i = 0; i < jarr.size()-1; i++) {
            CAL.add(caseBuilder(jarr.get(i)));
        }
    }


    private Case caseBuilder(JsonElement je){
        Case c = new Case();
        if(je==null) return c;

        c.setDate(je.getAsJsonObject().get("data").getAsString());
        c.setState(je.getAsJsonObject().get("stato").getAsString());
        c.setRegion(je.getAsJsonObject().get("denominazione_regione").getAsString());
        c.setRegionId(je.getAsJsonObject().get("codice_regione").getAsInt());
        c.setNewlyInfected(je.getAsJsonObject().get("nuovi_positivi").getAsLong());
        c.setTotalInfected(je.getAsJsonObject().get("totale_positivi").getAsLong());

        return c;
    }


}

class ReadException extends RuntimeException{
    public ReadException(String message){
        super(message);
    }
}