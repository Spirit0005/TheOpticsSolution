/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theopticssolution;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import static theopticssolution.TheOpticsSolution.stg;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * FXML Controller class
 *
 * @author Hamza
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    } 
    
    @FXML
    private ImageView imageView,imageView1;
    @FXML 
    private Label l1;
    @FXML
    private Button b1;
    @FXML
    private LineChart histogram;
    
    private  String imageFile;
    
    @FXML
    private void uploadImage (ActionEvent actionEvent) throws MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif")); // limit fileChooser options to image files
        File selectedFile = fileChooser.showOpenDialog(stg);

        if (selectedFile != null) {

            imageFile = selectedFile.toURI().toURL().toString();
            System.out.println("Main read path is : "+imageFile);
            Image image = new Image(imageFile);
            imageView.setImage(image);
            l1.setVisible(false);
            b1.setLayoutX(135);
            b1.setText("Start Thresholding");
           b1.setOnAction(e -> {thresholding();});
        } 
    }
    
    @FXML
    private void thresholding(){
        
        // remove C:/ form  path
        imageFile=imageFile.substring(6, imageFile.length());
        
        
        // Create image matrix
        Mat src = Imgcodecs.imread(imageFile, Imgcodecs.IMREAD_COLOR);
          if( src.empty() ) {
            System.out.println("Error opening image!");
           
            System.exit(0);
        }
        
        // [convert_to_gray]
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY);
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".jpg", gray, byteMat);
        Image img1=new Image(new ByteArrayInputStream(byteMat.toArray()));
        
       // Imgproc.medianBlur(gray, gray, 5);
        Imgproc.GaussianBlur(gray, gray, new Size(3,3), 0);
        
        // Thresholding
        final Mat binImg = new Mat();
	Imgproc.threshold(gray, binImg, 0, 255, Imgproc.THRESH_OTSU );
        //+ Imgproc.THRESH_BINARY_INV
        Imgcodecs.imencode(".jpg", binImg, byteMat);
        Image img2=new Image(new ByteArrayInputStream(byteMat.toArray()));
        
        
        // Erosion $ Dilation
         final Mat erd = new Mat();
        Imgproc.erode(binImg, erd, new Mat());
        
        
        //! [display]
       // HighGui.imshow("detected circles", erd);
        //HighGui.waitKey();
       
          final Timeline timeline = new Timeline(
        
        new KeyFrame(Duration.millis(2),new KeyValue(imageView1.imageProperty(),img1)),          
        new KeyFrame(Duration.millis(1500),new KeyValue(imageView.opacityProperty(),0)),
        new KeyFrame(Duration.millis(1500),new KeyValue(imageView1.opacityProperty(),1)),
        new KeyFrame(Duration.millis(1500),new KeyValue(imageView.imageProperty(),img2)),
        new KeyFrame(Duration.millis(3000),new KeyValue(imageView1.opacityProperty(),0)),
        new KeyFrame(Duration.millis(3000),new KeyValue(imageView.opacityProperty(),1))
        
        );
          timeline.play();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void detectCircle(){
        imageFile=imageFile.substring(6, imageFile.length());
        System.out.println("After applying substring method : "+imageFile);
          
         //        String default_file = "src\\.jpg";
//        String filename = ((args.length > 0) ? args[0] : default_file);

        // Load an image
        Mat src = Imgcodecs.imread(imageFile, Imgcodecs.IMREAD_COLOR);
//        Mat src = Imgcodecs.imread("src\\1.jpg", Imgcodecs.IMREAD_COLOR);

        // Check if image is loaded fine
        if( src.empty() ) {
            System.out.println("Error opening image!");
           
            System.exit(0);
        }
        //! [load]
        
        
        //! [convert_to_gray]
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        
       

        //![reduce_noise]
        Imgproc.medianBlur(gray, gray, 5);
        //![reduce_noise]

        //! [houghcircles]
        Mat circles = new Mat();
          Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1.0,
                (double)gray.rows(), // change this value to detect circles with different distances to each other
                100.0, 30.0, 1, 90);

//Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1.0, (double)gray.rows());
        //! [draw]
        for (int x = 0; x < circles.cols(); x++) {
            double[] c = circles.get(0, x);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            // circle center
            Imgproc.circle(src, center, 1, new Scalar(0,100,100), 3, 8, 0 );
            // circle outline
            int radius = (int) Math.round(c[2]);
            Imgproc.circle(src, center, radius, new Scalar(0,0,255), 3, 8, 0 );
        }
        //! [draw]

        //! [display]
        HighGui.imshow("detected circles", src);
        HighGui.waitKey();
        //! [display]

        System.exit(0);
    }
    
    @FXML
    private void exit(){
        
        System.exit(0);
    }
}
    

