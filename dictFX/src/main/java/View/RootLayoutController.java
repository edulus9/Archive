package View;

import Main.MainApp;

public class RootLayoutController {
        MainApp main;



        public RootLayoutController() {
        }

        public void setMain(MainApp m){
            main=m;
        }

        public void init(){}

        public void closeOnAction(){
            main.close();
        }
    }
