package Main;

import Model.Answers;
import Model.Question;
import Model.QuestionHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import players.Player;
import players.PlayerHandler;
import view.QuizOverview;
import view.RootLayout;

import java.io.IOException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class MainApp extends Application{

    private Stage primaryStage;
    static Scanner sc = new Scanner(System.in);
    private BorderPane rootLayout;
    private ModuleLayer.Controller controller;

    public static void main(String[] args){ launch(args); }

    public MainApp(){}



    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JQuiz!");

        initRootLayout();

        showQuizOverview();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            URL resource = MainApp.class.getResource("../view/RootLayout.fxml");
            loader.setLocation(resource);
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
            rootLayout.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());

            RootLayout controller = loader.getController();
            controller.setMain(this);
            controller.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void start(Stage primaryStage) {
        try{
            QuestionHandler.resumeJS();
            System.out.println("Question Json resumed!");
        }catch(IOException e){
            System.out.println("***RESUME ERROR!*** Failed to read Question JSON file");
        }
        try{
            PlayerHandler.resumeJS();
            System.out.println("Player Json resumed!");
        }catch(IOException e){
            System.out.println("***RESUME ERROR!*** Failed to read Player JSON file");
        }
        try{
                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
                rootLayout = (BorderPane) loader.load();

                // Show the scene containing the root layout.
                Scene scene = new Scene(rootLayout);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }*/


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void consoleMod() throws IOException {
        while(true){
            System.out.println("Insert 1 to start");
            System.out.println("Insert 2 to visualize the players ranking");
            String s = sc.nextLine();

            switch(s){
                case "1": //start the test and save player
                    System.out.println("Your nickname:");
                    String nickname = sc.nextLine();
                    Player currentPlayer = new Player(nickname);
                    for(Question q : QuestionHandler.getQuestionArrayList()){
                        System.out.println(q.getQuestion());
                        Set<Entry<Answers, String>> questionSet = q.getAnswerMap().entrySet();
                        for(Entry entry : questionSet){
                            System.out.print(entry.getKey().toString() + ") " + entry.getValue());
                            System.out.println();
                        }
                        System.out.println();

                        String playerAns = sc.nextLine();
                        switch (playerAns){
                            case "A":
                                currentPlayer.addAnswer(Answers.A);
                                break;
                            case "B":
                                currentPlayer.addAnswer(Answers.B);
                                break;
                            case "C":
                                currentPlayer.addAnswer(Answers.C);
                                break;
                            case "D":
                                currentPlayer.addAnswer(Answers.D);
                                break;
                            default:
                                System.out.println("Error! Invalid input!");
                                System.exit(1);
                                break;
                        }
                    }
                    currentPlayer.setScore(currentPlayer.calculateScore());
                    PlayerHandler.addPlayer(currentPlayer);
                    break;
                case "2": //show all players with theirs scores
                    System.out.println("RANKING:");
                    for(Player p : PlayerHandler.getPlayerArrayList()){
                        System.out.println(p.getName() + "\t score: " + p.getScore());
                    }
                    break;
                default:
                    System.out.println("You do nothing");
                    break;
            }
            PlayerHandler.refreshJS();
        }
    }

    public BorderPane getRootLayout(){
        return rootLayout;
    }

    public void showQuizOverview() {
        try{
            QuestionHandler.resumeJS();
            PlayerHandler.resumeJS();
        }catch(IOException e){
            e.printStackTrace();
        }
        try {
            primaryStage.setHeight(250);
            primaryStage.setWidth(345);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/QuizOverview.fxml"));
            Pane quizOverview = (Pane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(quizOverview);

            // Give the controller access to the main app.
            QuizOverview controller = loader.getController();
            controller.setMain(this);
            controller.init();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startQuiz(Player p){
        try{
            primaryStage.setHeight(580);
            primaryStage.setWidth(620);
            QuestionHandler.resumeJS();
            System.out.println(QuestionHandler.getQuizTitle());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/QuestionOverview.fxml"));
            Pane QuestionOverview = (Pane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(QuestionOverview);

            // Give the controller access to the main app.
            view.QuestionOverview controller = loader.getController();
            controller.setMain(this);
            controller.init(p);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openCongrats(Player p){
        try{
            primaryStage.setHeight(350);
            primaryStage.setWidth(300);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/ResultOverview.fxml"));
            Pane ResultOverview = (Pane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(ResultOverview);

            // Give the controller access to the main app.
            view.ResultOverview controller = loader.getController();
            controller.setMain(this);

            controller.init(p);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showNewPlayerDialog(){
        try{
            primaryStage.setHeight(168);
            primaryStage.setWidth(345);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/NewPlayerDialog.fxml"));
            Pane NewPlayerDialog = (Pane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(NewPlayerDialog);

            // Give the controller access to the main app.
            view.NewPlayerDialog controller = loader.getController();
            controller.setMain(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showRankOverview(){
        try{
            primaryStage.setHeight(400);
            primaryStage.setWidth(248);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/RankOverview.fxml"));
            Pane RankOverview = (Pane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(RankOverview);

            // Give the controller access to the main app.
            view.RankOverview controller = loader.getController();
            controller.setMain(this);
            controller.init();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void close(){
        System.exit(1);
    }
}
