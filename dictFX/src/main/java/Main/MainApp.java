package Main;

import View.DictionaryOverviewController;
import View.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ModuleLayer.Controller controller;

    public static void main(String[] args){ launch(args); }

    public MainApp(){}



    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        initRootLayout();

        showDictionary();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            URL resource = MainApp.class.getClassLoader().getResource("../View/RootLayout.fxml");;
            loader.setLocation(resource);
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);

            RootLayoutController controller = loader.getController();
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

    public BorderPane getRootLayout(){
        return rootLayout;
    }

    public void close(){
        System.exit(1);
    }

    public void showDictionary(){
        try {
            primaryStage.setHeight(250);
            primaryStage.setWidth(345);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../View/DictionaryOverview.fxml"));
            Pane dictionaryOverview = (Pane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(dictionaryOverview);

            // Give the controller access to the main app.
            DictionaryOverviewController controller = loader.getController();
            controller.setMain(this);
            controller.init();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
