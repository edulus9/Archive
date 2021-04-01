package Model;

import java.util.TreeMap;

public class Question {
    private String question = "";
    private Answers correctAnswer = null;
    private TreeMap<Answers, String> answerMap = new TreeMap<>();

    public Question(String question, Answers correctAnswer, String[] answ) {
        this.question = question;
        this.correctAnswer = correctAnswer;

        Answers[] values = Answers.values();

        if(answ.length!=values.length) throw new QuestionException("Error while generating question, please contact developers!");

        for (int i = 0; i < values.length; i++) {
            answerMap.put(values[i], answ[i]);
        }
    }

    public String getQuestion() {
        return question;
    }

    public Answers getCorrectAnswer() {
        return correctAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrectAnswer(Answers correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public TreeMap<Answers, String> getAnswerMap() {
        return answerMap;
    }

    @Override
    public String toString() {
        String s="";
        s+= "Question: " + question + "\n";
        s+= "Answer A: " + answerMap.get(Answers.A) + "\n";
        s+= "Answer B: " + answerMap.get(Answers.B) + "\n";
        s+= "Answer C: " + answerMap.get(Answers.C) + "\n";
        s+= "Answer D: " + answerMap.get(Answers.D) + "\n";
        s+="Correct answer: " + answerMap.get(correctAnswer) + "\n";
        return s;
    }
}

