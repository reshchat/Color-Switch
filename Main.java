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
	
	Image image = new Image("file:Color-Switch-icon.png");
	AnchorPane pane = new AnchorPane();
	StackPane pane2 = new StackPane();
	Scene scene = new Scene(pane, 500, 500);
	Stage stage = new Stage();
	
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
	showMainmenu(theStage);
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
    private void showMainmenu(Stage theStage) {
		
		VBox vbox = new VBox(5);
        stage = theStage;
        Text t = new Text();
    	t = new Text (10, 20, "Welcome to Color Switch!\n");
    	t.setFont(Font.font ("Montserrat", 20));
    	t.setFill(Color.BLACK);
    	
    	Button btn1 = new Button("Start new game");
    	Button btn2 = new Button("Resume a saved game");
    	Button btn3 = new Button("Exit");
       
    	buttonHandler bh = new buttonHandler();

        btn1.setOnAction(bh);
        btn2.setOnAction(bh);
        btn3.setOnAction(bh);

        vbox.getChildren().addAll(t, btn1, btn2, btn3);

        AnchorPane.setTopAnchor(vbox, 10d);
        AnchorPane.setLeftAnchor(vbox, 10d);

        pane.getChildren().addAll(vbox);
        theStage.setTitle("Colour Switch");
        theStage.setScene(scene);
        theStage.show();

	}
	private void startNewgame(Stage theStage) {
        	singleRotatingCircle(theStage);
	}
	private void singleRotatingCircle(Stage theStage) {
        
		ImageView iv = new ImageView();
		iv.setImage(image);
		//iv.setX(-100); 
		//iv.setY(25);
		iv.setFitHeight(100); 
		iv.setFitWidth(100);
		iv.setPreserveRatio(true);

		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), pane2);
		rotateTransition.setFromAngle(0);
		rotateTransition.setToAngle(360);
		rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
		//rotateTransition.setCycleCount(2);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
		rotateTransition.play();

		VBox vbox = new VBox(50, pane2);
		vbox.setStyle("-fx-background-color: black");

		pane2.getChildren().add(iv);
		Scene scene = new Scene(vbox, 500, 500);
		theStage.setTitle("Colour Switch");
		theStage.setScene(scene);
		theStage.show();
		
	}
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
	
     private class buttonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        	var src = (Button) event.getSource();
        	if(src.getText().equals("Exit")) {
        		Platform.exit();
        	}
        	else if(src.getText().equals("Start new game")) {
        		pane.getChildren().clear();
        		startNewgame(stage);
        	}
        	else if(src.getText().equals("Resume a saved game")) {
        		
        	}
        }
    }
}
//translate and path transition sample code
////        Duration duration = Duration.millis(2500);
////        TranslateTransition transition = new TranslateTransition(duration, pane);
////        transition.setByX(500);
////        transition.setByY(0);
////        transition.setAutoReverse(true);
////        transition.setCycleCount(TranslateTransition.INDEFINITE);
////        transition.setInterpolator(Interpolator.LINEAR);
////        transition.play();
//        
//        Polygon polygon = new Polygon();  
//        polygon.getPoints().addAll(new Double[]{ 50.0, 200.0, 800.0, 200.0});  
//        
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(3000));
//        pathTransition.setNode(pane);
////        pathTransition.setPath(new Circle(200, 200, 50));
////        pathTransition.play();
//        pathTransition.setPath(polygon);
//        pathTransition.setInterpolator(Interpolator.LINEAR);
//        pathTransition.setCycleCount(TranslateTransition.INDEFINITE);
//        pathTransition.play();

