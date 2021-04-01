package view;

import Main.MainApp;
import Model.Answers;
import Model.Question;
import Model.QuestionHandler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import players.Player;
import java.util.ArrayList;

public class QuestionOverview {
    private ArrayList<Question> questions = QuestionHandler.getQuestionArrayList();
    private int index = 0, correct=0;
    private IntegerProperty seconds = new SimpleIntegerProperty();
    private Player current;
    private MainApp main;
    private Timeline barT = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
            new KeyFrame(Duration.seconds(30), e-> {
    }, new KeyValue(seconds, 30))
            );
    private Timeline indT  = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
            new KeyFrame(Duration.seconds(30), e-> {
                // do anything you need here on completion...
                nextOnAction();
                System.out.println("Redrawing...");
            }, new KeyValue(seconds, 30))
    );

    @FXML
    AnchorPane root = new AnchorPane();
    @FXML
    Label questionNumber;
    @FXML
    Label questionLabel;
    @FXML
    Label QuizTitle;
    @FXML
    Button send;
    @FXML
    RadioButton answer1;
    @FXML
    RadioButton answer2;
    @FXML
    RadioButton answer3;
    @FXML
    RadioButton answer4;

    @FXML
    ProgressBar tRemainingBar;
    @FXML
    ProgressIndicator tRemainingIndicator;

    public QuestionOverview(){
    }
    public void init(Player p){
        System.out.println("Initializing questions....");
        setupTimers();
        current=p;
        redraw();

    }

    public void redraw(){
        stopTimers();
        if(index>=questions.size()){
            nextOnAction(); return;
        }


         questionNumber.setText("Question no." + Integer.toString(index + 1));

         questionLabel.setText(questions.get(index).getQuestion());

         QuizTitle.setText(QuestionHandler.getQuizTitle());

         send.setText("Next");

         answer1.setText("A) " + questions.get(index).getAnswerMap().get(Answers.A));

         answer2.setText("B) " + questions.get(index).getAnswerMap().get(Answers.B));

         answer3.setText("C) " + questions.get(index).getAnswerMap().get(Answers.C));

        answer4.setText("D) " + questions.get(index).getAnswerMap().get(Answers.D));


        startTimers();
    }

    public void setAnswer1(){
        answer1.setSelected(true);
        answer2.setSelected(false);
        answer3.setSelected(false);
        answer4.setSelected(false);
    }
    @FXML
    public void setAnswer2(){
        answer1.setSelected(false);
        answer2.setSelected(true);
        answer3.setSelected(false);
        answer4.setSelected(false);
    }
    @FXML
    public void setAnswer3(){
        answer1.setSelected(false);
        answer2.setSelected(false);
        answer3.setSelected(true);
        answer4.setSelected(false);
    }
    @FXML
    public void setAnswer4(){
        answer1.setSelected(false);
        answer2.setSelected(false);
        answer3.setSelected(false);
        answer4.setSelected(true);
    }

    @FXML
    public void nextOnAction(){
        if(index >= questions.size()){
            main.openCongrats(current);
        }else{
            if(answer1.isSelected()){
                associator(1);
            }else if(answer2.isSelected()){
                associator(2);
            }else if(answer3.isSelected()){
                associator(3);
            }else if(answer4.isSelected()){
                associator(4);
            }else if(tRemainingBar.getProgress()==1.0){
                associator(0);
            }
        }
    }

    private void associator(int i){
        switch(i){
            case 1:
                current.addAnswer(Answers.A);
                break;
            case 2:
                current.addAnswer(Answers.B);
                break;
            case 3:
                current.addAnswer(Answers.C);
                break;
            case 4:
                current.addAnswer(Answers.D);
                break;
            default:
                current.addAnswer(Answers.NULL);
                break;
        }
        index++;
        redraw();
    }

    public void setMain(MainApp m){
        main=m;
        redraw();
    }

    /*private void startBar(){
        IntegerProperty seconds = new SimpleIntegerProperty();
        tRemainingBar.progressProperty().bind(seconds.divide(10.0));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
                new KeyFrame(Duration.seconds(10), e-> {
                }, new KeyValue(seconds, 10))
        );
        timeline.setCycleCount(0);
        timeline.play();
    }

    private void startIndicator(){
        IntegerProperty seconds = new SimpleIntegerProperty();
        int i=0;
        tRemainingIndicator.progressProperty().bind(seconds.divide(10.0));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
                new KeyFrame(Duration.seconds(10), e-> {
                    // do anything you need here on completion...

                    nextOnAction();
                }, new KeyValue(seconds, 10))
        );
        timeline.setCycleCount(0);
        timeline.play();
    }*/

    private void setupTimers(){

        tRemainingIndicator.progressProperty().bind(seconds.divide(30.0));

        tRemainingBar.progressProperty().bind(seconds.divide(30.0));
        barT.setCycleCount(0);
        indT.setCycleCount(0);
    }

    private void startTimers(){
        barT.play();
        indT.play();
    }

    private void stopTimers(){
        barT.stop();
        indT.stop();
    }


}
