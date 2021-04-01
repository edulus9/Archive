package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionHandler {
    private static TypeToken<ArrayList<Question>> QARR = new TypeToken<ArrayList<Question>>() {};
    private static ArrayList<Question> questionArrayList = new ArrayList<>();



    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gs = builder.create();
    private static String quizTitle=null;

    public static String getQuizTitle(){
        return quizTitle;
    }

    public static void setQuizTitle(String title){
        quizTitle=title;
    }

    public static void addQuestion(Question q){
        questionArrayList.add(q);
    }

    public static ArrayList<Question> getQuestionArrayList() {
        return questionArrayList;
    }

    public static void refreshJS() throws IOException {
        try{
            FileWriter fw = new FileWriter("Questions.json");
            /*fw.write(gs.toJson(questionArrayList, QARR.getType()));
            fw.write(gs.toJson(quizTitle, quizTitle.getClass()));*/

            JsonObject jo = new JsonObject();

            jo.add("title", gs.toJsonTree(quizTitle));
            jo.add("QAL", gs.toJsonTree(questionArrayList));


            fw.write(jo.toString());


            fw.close();
        }catch(Exception e){
            throw(e);
        }
    }

    public static void resumeJS() throws IOException{
        FileReader fr = new FileReader("Questions.json");
        try{
            /*questionArrayList = gs.fromJson(fr, QARR.getType());
            quizTitle = gs.fromJson(fr, quizTitle.getClass());*/

            JsonObject jo = gs.fromJson(fr, JsonObject.class);

            quizTitle=jo.get("title").getAsString();
            questionArrayList=gs.fromJson(jo.get("QAL"), QARR.getType());



        }catch(Exception e){
            throw(e);
        }
        fr.close();
    }

    public static String toStrings() {
        String s = "";
        int i = 1;
        for (Question question : questionArrayList) {
            s += "Question n." + i + ":\n";
            s += question;
            i++;
        }
        return s;
    }
}
