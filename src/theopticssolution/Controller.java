/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theopticssolution;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Sound;
import static model.Sound.*;
import static theopticssolution.TheOpticsSolution.stg;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;



/**
 *
 * @author hk_th
 */
public class Controller implements Initializable {

    @FXML
    private Label l1;
    
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Button b1;
    @FXML
    private ImageView img1;
    
    @FXML
    private void mouseEnter() {
        
        b1.setScaleX(1.2);
        b1.setScaleY(1.2);
        //Sound.hover.play();
        
            }
    
    @FXML
    private void mouseExit() {
        
       b1.setScaleX(1.0);
       b1.setScaleY(1.0);
       
            }
    
    @FXML
    private void mouseClickStart() throws IOException {
        
        //Sound.click.play();
        stg.hide();
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
       
        Scene scene = new Scene(root);
        
        stg.setScene(scene);
        
        stg.show();
            }
    
    private void fadeIn(int value ,Node node){
        FadeTransition f=new FadeTransition(Duration.seconds(value),node);
         f.setFromValue(0.0);
         f.setToValue(1.0);
         f.play();
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            // TODO
            TranslateTransition tt1=new TranslateTransition();
            RotateTransition rt1=new RotateTransition(Duration.seconds(1),img1);
            
            TranslateTransition tt2=new TranslateTransition(Duration.seconds(2),img1);
            
            tt2.setToY(-5);
            tt2.setAutoReverse(true);
            tt2.setCycleCount(TranslateTransition.INDEFINITE);
            
           
            rt1.setByAngle(360);
            rt1.setCycleCount(2);
            
            tt1.setDuration(Duration.seconds(2));
            tt1.setNode(img1);
            tt1.setToX(480);
            
            tt1.play();
            rt1.play();
            rt1.setOnFinished((e)->{
                
                fadeIn(2,l3);
                fadeIn(2,b1);
               
             });
            fadeIn(2,l1);
            fadeIn(2,l2);
            tt2.play();
            
            
        
        }
        
        
        

}
