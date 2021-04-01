package Utils;

import Model.QuestionHandler;
import players.Player;
import players.PlayerHandler;

import java.util.Scanner;

public class PlayerAdder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name="";
        int score=0;

        try{
            PlayerHandler.resumeJS();
        }catch(Exception e){
            System.out.println("***RESUME ERROR!*** Failed to read JSON file");
            System.out.println(e);
        }

        System.out.println(PlayerHandler.toStrings());

        while(!(name.equals("EXIT"))){
            System.out.println("Insert player name: ");
            name=sc.nextLine();
            System.out.println("Insert player score: ");
            score=Integer.parseInt(sc.nextLine());

            if(!(name.equals("EXIT")))PlayerHandler.addPlayer(new Player(name, score));
        }

        try{
            PlayerHandler.refreshJS();
        }catch(Exception e){
            System.out.println("***RESUME ERROR!*** Failed to write JSON file");
            System.out.println(e);
        }
    }
}
