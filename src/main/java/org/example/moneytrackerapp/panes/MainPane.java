package org.example.moneytrackerapp.panes;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.HelloApplication;

public class MainPane extends BorderPane {
    public MainPane(){
        HelloApplication.mainStage.setTitle("Money Tracker App");
        Text text = new Text("Works");
        this.setCenter(text);
    }
}
