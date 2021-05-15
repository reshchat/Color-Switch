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

class Ball{
	private int x;
	private int y;
	private int colour;
	Image red;
    Image yellow;
    Image blue;
    Image green;
    public Ball() throws FileNotFoundException {
    	 red = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\red.png") );
         yellow = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\yellow.png") );
         blue = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\blue.png") );
         green = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\green.png") );
         
    }
	/* 0=red
	 * 1=blue
	 * 2=yellow
	 * 3=green
	 */
	public void change_colour(int a, int b) {
		Random rand = new Random(); 
		 this.colour = rand.nextInt(4); 
	}
	
	public Image get_ball() {
		if(this.colour==0)
            return this.red;
 if(this.colour==1)
     return this.blue;
 if(this.colour==2)
     return this.yellow;
 if(this.colour==3)
     return this.green;
 
 return this.red;
		
	}
}
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    final long startNanoTime = System.nanoTime();
     GraphicsContext gc;
    private double leftPaddleDY;
    Ball ball;
    
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
        ball=new Ball();
 
     
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
 
            double x = 150; //set to middle of page
 y=y+leftPaddleDY+1;
 if (y>450)
	 y=450;
 Random rand = new Random(); 
 if (y>=200 && y<=207) {
	// y=y+10;
	 ball.change_colour(200, 207);
	 //colour = rand.nextInt(4); 
 }
            // background clears canvas
 
           
 gc.clearRect(0, 0,512,512);
 gc.drawImage(ball.get_ball(), x, y);
 
        }
    };
    
    private EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            // set movement to 0, if the released key was responsible for the paddle
            switch (event.getCode()) {
                case UP:
                	leftPaddleDY = 0;
                	
            }
        }

    };

    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            // start movement according to key pressed
            switch (event.getCode()) {
                case UP:
                    leftPaddleDY = -5;
                    break;
                   
            }

        }
    };
}


