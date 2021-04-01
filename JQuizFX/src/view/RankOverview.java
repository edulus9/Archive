package view;

import Main.MainApp;
import Model.QuestionHandler;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import players.Player;
import players.PlayerHandler;

import java.util.ArrayList;
import java.util.List;

public class RankOverview {
    MainApp main;

    @FXML
    Label quizName;
    @FXML
    TableColumn<Player, String> players;
    @FXML
    TableColumn<Player, Integer> scores;
    @FXML
    TableView<Player> scoreTable;
    @FXML
    Button back;

    public RankOverview(){}

    public void setMain(MainApp m){
        main=m;
    }

    public void init(){
        quizName.setText(QuestionHandler.getQuizTitle());
        ObservableList<Player> rank = FXCollections.<Player>observableArrayList();
        rank.addAll(PlayerHandler.getPlayerArrayList());



        scoreTable.setItems(rank);
        players.setCellValueFactory(new PropertyValueFactory<Player, String>("Name"));
        scores.setCellValueFactory(new PropertyValueFactory<Player, Integer>("Score"));

    }

    public void showPlayerRank(){

    }

    public void backOnAction(){
        main.showQuizOverview();
    }
}
