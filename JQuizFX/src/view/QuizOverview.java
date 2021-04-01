package view;

import Model.QuestionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import Main.MainApp;
import javafx.scene.layout.Pane;
import players.Player;
import players.PlayerHandler;

import java.io.IOException;
import java.net.URL;

public class QuizOverview {
    Player selected=null;

    @FXML
    Pane root;
    @FXML
    private Label quizTitleLabel;
    @FXML
    private Label quoteLabel;
    @FXML
    private Button play;
    @FXML
    private Button rank;
    @FXML
    private Button exit;
    @FXML
    private MenuButton playerChooser;
    @FXML
    private MenuItem newPlayer;

    MainApp main;


    public QuizOverview() {
        /*play = new Button();
        play.setOnAction(e -> {
            playButton();
        });*/
    }
    @FXML
    public void init(){
        quizTitleLabel.setText(QuestionHandler.getQuizTitle());
        quoteLabel.setText("Will you stand the test of time?");
        System.out.println("QuizOverview initialized!");

        MenuItem mi;
        for (Player p : PlayerHandler.getPlayerArrayList()) {
            mi = new MenuItem(p.getName());

            mi.setOnAction(event -> {
                MenuItem source = (MenuItem) event.getSource();
                playerSelected(source.getText());
            });

            playerChooser.getItems().add(mi);
        }
        selected=null;
    }

    public void setMain(MainApp m){
        main=m;
    }

    public void playButton(){
        if(selected!=null){
            selected=new Player(selected.getName());
            main.startQuiz(selected);
        }
    }
    @FXML
    public void rankButton(){
        main.showRankOverview();
    }

    public void exitButton(){
        main.close();
    }

    public void newPlayer(){
        main.showNewPlayerDialog();
    }

    private EventHandler playerSelected(String s) {
        System.out.println("Setting...." + s);
        selected = PlayerHandler.get(s);
        playerChooser.setText(s);

        return null;
    }


}
