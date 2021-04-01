package view;

import Main.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class RootLayout {
    @FXML
    Menu fileMenu;
    @FXML
    MenuItem close;

    MainApp main;



    public RootLayout() {
    }

    public void setMain(MainApp m){
        main=m;
    }

    public void init(){}

    public void closeOnAction(){
        main.close();
    }
}
