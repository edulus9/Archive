package Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.TreeMap;

public class JSONLoader {

    private FileReader fr;
    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gs = builder.create();


    public JSONLoader(String path){
        try{
            fr=new FileReader(path);
        }catch (IOException e){
            throw new ReadException("Invalid path!");
        }
    }


    public TreeMap retrieve(){
        JsonArray jarr=gs.fromJson(fr, JsonArray.class);

        TreeMap<String, String> tm = new TreeMap<>();
        for (int i=0; i<jarr.size(); i++) {
            tm.put(jarr.get(i).getAsJsonObject().get("Entry").getAsString(), jarr.get(i).getAsJsonObject().get("Definition").getAsString());
        }
        return tm;
    }

}

class ReadException extends RuntimeException{
    public ReadException(String message){
        super(message);
    }
}
