package view;

import Main.MainApp;
import Model.QuestionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import players.Player;
import players.PlayerHandler;

import java.io.IOException;

public class NewPlayerDialog {
    MainApp main;

    @FXML
    TextField nameField;
    @FXML
    Button confirm, discard;

    public NewPlayerDialog(){}

    public void setMain(MainApp m){
        main=m;
    }

    public void confirmOnAction(){
        if(!(nameField.getText().equals(""))){
            PlayerHandler.addPlayer(new Player(nameField.getText()));
            try{
                PlayerHandler.refreshJS();
            }catch(IOException e){
                e.printStackTrace();
            }
            discardOnAction();
        }
    }

    public void discardOnAction(){
        main.showQuizOverview();
    }
}
