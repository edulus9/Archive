package players;

import Model.Answers;
import Model.Question;
import Model.QuestionHandler;

import java.util.ArrayList;

public class Player implements Comparable<Player>{
    private String name;
    private int score;
    private ArrayList<Answers> playerAnswers = new ArrayList<>();

    public Player(String name){
        this.name = name;
    }
    public Player(String name, int s){
        this.name = name;
        score=s;
    }

    public int getScore() {
        return score;
    }
    public String getName() { return name; }

    public void setScore(int score) {
        this.score = score;
    }

    public void addAnswer(Answers a){
        playerAnswers.add(a);
    }

    public int calculateScore(){
        int ret = 0;
        ArrayList<Question> questions = QuestionHandler.getQuestionArrayList();
        ArrayList<Answers> correctAnswers = new ArrayList<>();

        for(Question q : questions){
            correctAnswers.add(q.getCorrectAnswer());
        }

        for (int i = 0; i < correctAnswers.size(); i++){
            if(correctAnswers.get(i) == this.playerAnswers.get(i)){
                ret += 1;
            }
        }
        return ret;
    }

    @Override
    public int compareTo(Player o) {
        if(this.score == o.getScore()){
            if(this.name.compareTo(o.getName()) == 0){
                return 0;
            } else if(this.name.compareTo(o.getName()) > 0){
                return 1;
            } else {
                return -1;
            }
        } else if(this.score > o.getScore()){
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(this.getClass().equals(o.getClass())){
            Player p=(Player) o;
            return (this.name.equals(p.name));
        }
        return false;

    }
}
