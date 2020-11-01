package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.shape.*;
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    final long startNanoTime = System.nanoTime();
    Image red;
    Image yellow;
    Image blue;
    Image green;
    GraphicsContext gc;
    private double leftPaddleDY;
    private double rightPaddleDY;
    private double y=400;
    private int colour=0;
    @Override
    public void start(Stage theStage) throws FileNotFoundException
    {
        theStage.setTitle( "Timeline Example" );
     
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
     
        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );
        gc = canvas.getGraphicsContext2D();
        
        red = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\red.png") );
        yellow = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\yellow.png") );
        blue = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\blue.png") );
        green = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\green.png") );
        Circle circle = new Circle();
        circle.setCenterX(100.0f);
        circle.setCenterY(100.0f);
        circle.setRadius(50.0f);
        
       // Image sun   = new Image( "sun.png" );
        //Image space = new Image( "space.png" );
     
        //final long startNanoTime = System.nanoTime();
        
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(keyPressed);
        canvas.setOnKeyReleased(keyReleased);
     
   timer.start();
     
        theStage.show();
    }
    
    private AnimationTimer timer =     new AnimationTimer()
    {
        public void handle(long currentNanoTime)
        {
            double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
 
            double x = 150;// + 128 * Math.cos(t);
           // double x = 232 + 128 * Math.cos(t);
           //  y =  128 * Math.sin(t);
           // double y = 232 + 128 * Math.sin(t);
 y=y+leftPaddleDY+1;
 if (y>450)
	 y=450;
 Random rand = new Random(); 
 if (y>=200 && y<=207) {
	// y=y+10;
	 colour = rand.nextInt(4); 
 }
            // background image clears canvas
           // gc.drawImage( space, 0, 0 );
 gc.clearRect(0, 0,512,512);
 if(colour==0)
            gc.drawImage( red, x, y );
 if(colour==1)
     gc.drawImage( blue, x, y );
 if(colour==2)
     gc.drawImage( yellow, x, y );
 if(colour==3)
     gc.drawImage( green, x, y );
           // gc.drawImage( sun, 196, 196 );
        }
    };//.start();
    
    private EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            // set movement to 0, if the released key was responsible for the paddle
            switch (event.getCode()) {
                case UP:
                	leftPaddleDY = 0;
                	/*
                case S:
                    leftPaddleDY = 0;
                    break;
                case UP:
                case DOWN:
                    rightPaddleDY = 0;
                    break;*/
            }
        }

    };

    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            // start movement according to key pressed
            switch (event.getCode()) {
                case UP:
                    leftPaddleDY = -6;
                    break;
                    
                    /*
                case S:
                    leftPaddleDY = 6;
                    break;
                case UP:
                    rightPaddleDY = -6;
                    break;
                case DOWN:
                    rightPaddleDY = 6;
                    break;*/
            }

        }
    };
}


