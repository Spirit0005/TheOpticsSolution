/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theopticssolution;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 *
 * @author hk_th
 */
public class TheOpticsSolution extends Application {
    
    static Stage stg;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Intro.fxml"));
        
        Scene scene = new Scene(root);
        this.stg=stage;
        
        stg.setScene(scene);
        stg.getIcons().add(new Image("/images/icon.png"));
        stg.setTitle("The Optics Solution");
        stg.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }
    
}
