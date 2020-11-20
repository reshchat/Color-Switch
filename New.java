package application;

import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.*;

public class Main extends Application {
	
	static int screenWidth = 1200;
	static int screenHeight = 600;
    static AnchorPane pane = new AnchorPane();
    static Scene scene = new Scene(pane, screenWidth, screenHeight);
    static Stage stage;
	StackPane pane2 = new StackPane();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	@Override
	public void start(Stage theStage) throws Exception {
		// TODO Auto-generated method stub
		stage = theStage;
		Homepage homepage = new Homepage();
		homepage.displayMainmenu(theStage, pane, scene);
	}
}	

 class Homepage {
	
	static AnchorPane pane = new AnchorPane();
    static Scene scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
    static Stage stage;
	private Game game;
	private int bestscore;
	
	public Homepage() {
		game = new Game();
		bestscore = 0;
	}
	public void displayMainmenu(Stage theStage, AnchorPane pane, Scene scene){
		stage = theStage;
		VBox vbox = new VBox(5);
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
	private void startNewgame(Stage stage) throws FileNotFoundException{	
		game.start(stage);
	}
	private void resumeGame(Game game){
		
	}
	private void exit(){
		
	}
	public void showSavedgames(Stage stage){
		
	}
	public int getBestscore(){
		return this.bestscore;
	}
	public void setBestscore(int best){
		this.bestscore = best;
	}
	private class buttonHandler implements javafx.event.EventHandler<ActionEvent> {
		Homepage homepage = new Homepage();
        @Override
        public void handle(ActionEvent event) {

        	var src = (Button) event.getSource();
        	if(src.getText().equals("Exit")) {
        		Platform.exit();
        	}
        	else if(src.getText().equals("Start new game")) {
        		
        		pane.getChildren().clear();
        		 stage = new Stage();
        		try {
					startNewgame(stage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else if(src.getText().equals("Resume a saved game")) {
        		pane.getChildren().clear();
        		showSavedgames(stage);
        	}
        }
	}
}

class Game extends Application{
	
	private String name;
	private int level;
	private int distance;
	public Game()  {
		// launch();
		//stage = new Stage(); 
        //start(stage);
    }
    boolean pause=false;
    final long startNanoTime = System.nanoTime();
    GraphicsContext gc;
    private double leftPaddleDY;
    Ball ball;
    Obstacle1 obstacle1;
    private double y=400;
    private int colour=0;
    Button button2;
    Button btn;
    Label l;
    Group root;
    int x3=0;
    int angle=0;
    Canvas canvas;
    Stage stage;
    Scene scene;// = new Scene(pane, 500, 500);
    AnchorPane pane;
    static Timeline timeline;
    StackPane stack = new StackPane();
	@Override
    public void start(Stage theStage) throws FileNotFoundException
    {
    	
    	//stage =theStage;
        theStage.setTitle( "Colour Switch" );
        //Group 
        root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        canvas = new Canvas( Main.screenWidth, Main.screenHeight );
        root.getChildren().add( canvas );
        button2 = new Button("Pause");
        root.getChildren().add( button2 ); 
         btn = new Button();
        btn.setText("Save");
        btn.setLayoutX(250);
        btn.setLayoutY(0);
        root.getChildren().add(btn);
        gc = canvas.getGraphicsContext2D();
        ball=new Ball();
        obstacle1 = new Obstacle1();
        l = new Label("button not selected");
        root.getChildren().add(l);
        //root.getChildren().remove(l);
        final long startNanoTime = System.nanoTime();
        //canvas.setOnMouseClicked(event);
        
        canvas.setFocusTraversable(true);
        // canvas.requestFocus();
        canvas.setOnKeyPressed(keyPressed);
        canvas.setOnKeyReleased(keyReleased);
        button2.setOnAction(event);
        btn.setOnAction(event2);
        
        
        stack.getChildren().addAll(obstacle1.ob1);
        stack.setLayoutX(30);
        stack.setLayoutY(30);
        root.getChildren().add(stack);
        theStage.show();
        // root.getChildren().add( button3 );
	    
        //timer.start(); 
	    
        
        
        timer.start(); 
    }
	
	private AnimationTimer timer = new AnimationTimer()
    {
		   public void handle(long currentNanoTime)
	        {// background clears canvas
	            gc.clearRect(0, 0,512,512);
	        	 
	        	
	        	
	        	
	        	 
	            if (pause) {
	            	//return;
	            }
	            
	            if(pause==false) {
	            	Duration rotateDuration = Duration.millis(3);
	            	
	        	    Rotate rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	        	   // obstacle1.ob1.getTransforms().add(rotate);
	        	    
	        	    
	        	    long t2 = System.nanoTime() - startNanoTime;
	        	    timeline = new Timeline( 
	        	    		new KeyFrame(Duration.ZERO, new KeyValue(obstacle1.ob1.rotateProperty(), angle)), // initial rotate
	        	            new KeyFrame(rotateDuration, new KeyValue(obstacle1.ob1.rotateProperty(), angle+2)),
	        	            new KeyFrame(Duration.ZERO, new KeyValue(obstacle1.ob1.translateYProperty(), angle)),
	        	            new KeyFrame(rotateDuration, new KeyValue(obstacle1.ob1.translateYProperty(), angle+2)) 
	        	          
	        	            );
	        	    angle=angle+2;
	                timeline.setCycleCount(Timeline.INDEFINITE);
	                timeline.setAutoReverse(false);
	                timeline.play();
	            double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
	 
	            double x = 150; //set to middle of page
	 y=y+leftPaddleDY+1;
	 if (y>450)
		 y=450;
	 Random rand = new Random(); 
	 if (y>=200 && y<=207) {
			 ball.change_colour(200, 207);
		 
	 }
	   
	 gc.drawImage(ball.get_ball(), x, y);
	 }
	 
	        }
    };

    private class buttonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	var src = (Button) event.getSource();
        	if(src.getText().equals("Resume")) {
        		canvas.requestFocus();
        		pause=!(pause);
        		stage.close();
        	}
        	else if(src.getText().equals("Save game")) {
        		pane.getChildren().clear();
        		//startNewgame(stage);
        	}
        }
    }

    private EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            // set movement to 0, if the released key was responsible for the paddle
            switch (event.getCode()) {
                case UP:
                	leftPaddleDY = 0;
                	break;
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
                    l.setText("  up   "); 
                    break;
                case P:
                   pause =!(pause);
            }
        }
    };
    
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) 
        { 
	        x3++;
	        l.setText(String.valueOf(x3));
	       
	        pause=!(pause);
	        
        } 
    };
    EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) 
        { 
	        x3++;
	        l.setText(String.valueOf(x3));
	       
	        stage = new Stage(); 
	        showMainmenu(stage);
	        
	        // l.setText("   button   selected    "); 
	        pause=!(pause);
	        //Platform.exit();
        } 
    };
    private void showMainmenu(Stage theStage) {
    	pane = new AnchorPane();
    	Scene scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
		VBox vbox = new VBox(5);
        stage = theStage;
        Text t = new Text();
    	t = new Text (10, 20, "Welcome to Color Switch!\n");
    	t.setFont(Font.font ("Montserrat", 20));
    	t.setFill(Color.BLACK);
    	Button btn1 = new Button("Save game");
    	
    	Button btn3 = new Button("Resume");
    	buttonHandler bh = new buttonHandler();
        btn1.setOnAction(bh);
       
        btn3.setOnAction(bh);
        vbox.getChildren().addAll(t, btn1, btn3);
        AnchorPane.setTopAnchor(vbox, 10d);
        AnchorPane.setLeftAnchor(vbox, 10d);
        pane.getChildren().addAll(vbox);
        theStage.setTitle("Colour Switch");
        theStage.setScene(scene);
        theStage.show();
	}
}
class Ball{
	private int x;
	private int y;
	private int colour;
	Image red;
    Image yellow;
    Image blue;
    Image green;
    ImageView ballimg;
    
    public Ball() throws FileNotFoundException {
    	red = new Image("file:images/red.png");
    	yellow = new Image("file:images/yellow.png");
    	blue = new Image("file:images/blue.png");
    	green = new Image("file:images/green.png");
    	
         ballimg = new ImageView(this.get_ball());
         ballimg.setX(600-Main.screenWidth);
         ballimg.setY(300-Main.screenHeight);
         ballimg.setFitWidth(30);
         ballimg.setPreserveRatio(true);
    }
    public void change_colour(int a, int b) {
		Random rand = new Random(); 
		 this.colour = rand.nextInt(4); 
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getColour() {
		return this.colour;
	}
	public void setX(int a) {
		this.x=a;
	}
	public void setY(int a) {
		this.y=a;
	}
	public void setColour(int a) {
		this.colour=a;
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
 class Player {
	private int collectedstars;
	private int score;
	public Player() {
	}
	public int getCollectedStars() {
		return this.collectedstars;
	}
	public void setCollectedStars(int no) {
		this.collectedstars = no;
	}
	public int getScore() {
		return this.score;
	}
	public void setScore(int s) {
		this.score = s;
	}
	public void resurrect() {
		
	}
	public void moveBall() {
		
	}
}
 class Star {
	private int y;
	private Player player;
	public Star() {
		
	}
	public Star getY() {
		return null;
	}
	public void setY(int y) {
		
	}
	private void addLife(Player player) {
		
	}
}
 class Colourchanger {
	private int[] colours;
	private int y;
	private Ball ball;
	public Colourchanger() {
		
	}
	public int[] getColours() {
		return this.colours;
	}
	public void setColours(int colour) {
		
	}
	public Star getY() {
		return null;
	}
	public void setY(int y) {
		
	}
	private void changeColour(Ball ball) {
		
	}
}
abstract class Obstacle {
	protected int noofcolours;
	protected int passposition;
	protected int y;
	Image singlecircle;
	public Obstacle() throws FileNotFoundException {
		this.noofcolours = 4;
	}
	public int getNoofcolours() {
		return noofcolours;
	}
	public void setNoofcolours(int colours) {
		this.noofcolours = colours;
	}
	public int getPassposition() {
		return passposition;
	}
	public void setPassposition(int pass) {
		
	}
	public int getY() {
		return this.y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	protected boolean obstacleHit(double y2) {
		if(y2 >= passposition-100 && y2 <= passposition+100) {
			return false;
		}
		else {
			return true;
		}
	}
	protected abstract void movement(float duration, Canvas canvas);
}
class Obstacle1 extends Obstacle {
	
	static Image singlecircle;
	static ImageView ob1;
	
	public Obstacle1 () throws FileNotFoundException{
		this.singlecircle = new Image("file:images/Colour Changer.png");
		this.ob1 = new ImageView(this.singlecircle);
		ob1.setFitWidth(150);
		ob1.setFitHeight(150);
	}
	
	@Override
	protected void movement(float duration, Canvas canvas) {
		
//		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), canvas);
//	    rotateTransition.setFromAngle(0);
//	    rotateTransition.setToAngle(360);
//	    rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
//	    rotateTransition.setInterpolator(Interpolator.LINEAR);
//	    rotateTransition.play();
	    
	    Timeline timeline;
	    timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
		
	}
}
