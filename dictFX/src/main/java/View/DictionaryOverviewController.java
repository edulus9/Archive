package View;

import Controller.JSONLoader;
import Main.MainApp;

import java.util.TreeMap;

public class DictionaryOverviewController {
    MainApp main;
    TreeMap<String, String> tm;

    public DictionaryOverviewController() {
         tm = new TreeMap<>();
    }

    public void setMain(MainApp m){
        main=m;
    }

    public void init(){
        JSONLoader jl = new JSONLoader("../../../../dictionary.json");
    }

    public void closeOnAction(){
        main.close();
    }
}
