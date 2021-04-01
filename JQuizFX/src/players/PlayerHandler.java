package players;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerHandler {
    private static TypeToken<ArrayList<Player>> PARR = new TypeToken<ArrayList<Player>>() {};
    private static ArrayList<Player> playerArrayList = new ArrayList<>();

    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gs = builder.create();

    public static void refreshJS() throws IOException {
        try{
            FileWriter fw = new FileWriter("Players.json");
            fw.write(gs.toJson(playerArrayList, PARR.getType()));
            fw.close();
        }catch(IOException e){
            throw(e);
        }
    }

    public static void resumeJS() throws IOException{
        FileReader fr = new FileReader("Players.json");
        playerArrayList = gs.fromJson(fr, PARR.getType());
        fr.close();
    }

    public static void addPlayer(Player p){
        if(playerArrayList.contains(p)){
            int i=playerArrayList.indexOf(p);

            if(playerArrayList.get(i).getScore()<p.getScore()) playerArrayList.set(i, p);
        }else{
            playerArrayList.add(p);
        }
        update();
    }

    public static ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    private static void update(){
        playerArrayList.sort(Player::compareTo);
    }

    public static Player get(String name){
        return playerArrayList.get(playerArrayList.indexOf(new Player(name)));
    }

    public static String toStrings() {
        String s = "";
        int i = 1;
        for (Player player : playerArrayList) {
            s += "Player n." + i + ":\n";
            s += "name: " + player.getName() + "\n";
            s += "score: " + player.getScore() + "\n";
            i++;
        }
        return s;
    }


}
