package view;

import Main.MainApp;
import Model.QuestionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import players.Player;
import players.PlayerHandler;

import java.io.IOException;


public class ResultOverview {
    Player current;
    MainApp main;
    @FXML
    Label totalQuestions;
    @FXML
    Label correctQuestions;
    @FXML
    Label playerName;
    @FXML
    ImageView victoryImage;
    @FXML
    Button restart;

    public ResultOverview(){

    }

    public void setMain(MainApp m){
        main=m;
    }

    public void init(Player p){
        current=p;
        if(p==null) System.exit(1);
        p.setScore(p.calculateScore());
        playerName.setText(p.getName());
        totalQuestions.setText(Integer.toString(QuestionHandler.getQuestionArrayList().size()));
        correctQuestions.setText(Integer.toString(p.getScore()));
    }

    public void restartOnAction(){
        try{
            PlayerHandler.refreshJS();
        }catch (IOException e){
            e.printStackTrace();
        }

        main.showQuizOverview();
    }


}
