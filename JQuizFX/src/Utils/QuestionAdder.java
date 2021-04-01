package Utils;

import Model.Answers;
import Model.Question;
import Model.QuestionHandler;

import java.io.IOException;
import java.util.Scanner;

public class QuestionAdder {

    public static void train(){

    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String q;
        String[] answers = new String[4];
        String correct;

        try{
            QuestionHandler.resumeJS();
        }catch(Exception e){
            System.out.println("***RESUME ERROR!*** Failed to read JSON file");
            System.out.println(e);
        }


        System.out.println(QuestionHandler.toStrings());

        if(QuestionHandler.getQuizTitle()==null){
            System.out.println("Insert quiz title: ");
            QuestionHandler.setQuizTitle(sc.nextLine());
        }

        while(true){
            System.out.println("Insert question: ");
            q=sc.nextLine();

            if(q.equals("EXIT")) break;

            System.out.println("Insert ans A: ");
            answers[0] = sc.nextLine();
            System.out.println("Insert ans B: ");
            answers[1] = sc.nextLine();
            System.out.println("Insert ans C: ");
            answers[2] = sc.nextLine();
            System.out.println("Insert ans D: ");
            answers[3] = sc.nextLine();

            System.out.println("Insert the correct answer(A,B,C,D): ");
            correct = sc.nextLine().toUpperCase();

            switch (correct){
                case "A":
                    QuestionHandler.addQuestion(new Question(q, Answers.A, answers));
                    break;
                case "B":
                    QuestionHandler.addQuestion(new Question(q, Answers.B, answers));
                    break;
                case "C":
                    QuestionHandler.addQuestion(new Question(q, Answers.C, answers));
                    break;
                case "D":
                    QuestionHandler.addQuestion(new Question(q, Answers.D, answers));
                    break;
                default:
                    System.err.println("Invalid element!");
                    break;
            }
        }

        QuestionHandler.refreshJS();

        System.out.println("FINISHED!");

    }
}
