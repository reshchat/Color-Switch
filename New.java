package application;
//package colorswitch;

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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    static GridPane pane = new GridPane();
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
	
	static GridPane pane = new GridPane();
    static Scene scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
    static Stage stage;
	private Game game;
	private int bestscore;
	
	public Homepage() {
		game = new Game();
		bestscore = 0;
	}
	public void displayMainmenu(Stage theStage, GridPane pane, Scene scene){
		stage = theStage;
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
		VBox vbox = new VBox(5);
		Text t = new Text();
		t = new Text (10, 20, "Welcome to Color Switch!\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);

		Button btn1 = new Button("Start new game");
		Button btn2 = new Button("Resume a saved game");
		Button btn3 = new Button("Exit");

		buttonHandler bh = new buttonHandler();

		btn1.setOnAction(bh);
		btn2.setOnAction(bh);
		btn3.setOnAction(bh);

		vbox.getChildren().addAll(t, btn1, btn2, btn3);

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
	public void showSavedgames(Stage theStage){
		stage = theStage;
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
		VBox vbox = new VBox(5);
		Text t = new Text();
		t = new Text (10, 20, "Saved Games\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);
		Text t2 = new Text();
		t2 = new Text (10, 20, "Choose a game to start playing:\n");
		t2.setFont(Font.font ("Montserrat", 15));
		t2.setFill(Color.WHITE);

		Button btn1 = new Button("Game 1");
		Button btn2 = new Button("Game 2");
		Button btn3 = new Button("Game 3");
		
		Button btn4 = new Button("Back");

		buttonHandler bh = new buttonHandler();

		btn1.setOnAction(bh);
		btn2.setOnAction(bh);
		btn3.setOnAction(bh);
		btn4.setOnAction(bh);

		vbox.getChildren().addAll(t, t2, btn1, btn2, btn3, btn4);

		pane.getChildren().addAll(vbox);
		theStage.setTitle("Colour Switch");
		theStage.setScene(scene);
		theStage.show();
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
        		 stage.close();
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
        	else if(src.getText().equals("Back")) {
        		stage.close();
        		pane.getChildren().clear();
        		displayMainmenu(stage, pane, scene);
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
    Obstacle2 obstacle2;
    Star[] star1= new Star[5];
    Colourchanger[] ccr=new Colourchanger[5];
    Player player;
    private double y=299;
    private int colour=0;
    Button button2;
    Button btn;
    Group root;
    int x3=0;
    int angle=0;
    int angle2=0;
    Canvas canvas;
    Stage stage;
    Scene scene;
    GridPane pane;
    static Timeline timeline;
    StackPane stack = new StackPane();
    StackPane stack1 = new StackPane();
    StackPane stack2 = new StackPane();
    StackPane stack3 = new StackPane();
    StackPane stack4 = new StackPane();
    Label l;
	@Override
    public void start(Stage theStage) throws FileNotFoundException
    {
    	theStage.setTitle( "Colour Switch" );
        //Group 
        root = new Group();
        Scene theScene = new Scene( root , Main.screenWidth, Main.screenHeight );
        theStage.setScene( theScene );
        canvas = new Canvas( Main.screenWidth, Main.screenHeight );
        //root.getChildren().add( canvas );
        stack1.setStyle("-fx-background-color: #202020");
        stack1.getChildren().add(canvas);
        root.getChildren().add( stack1 );
        player = new Player();
        Text t = new Text();
		t = new Text (50, 100, "Score:\n\n" + player.getScore());
		t.setFont(Font.font ("Montserrat", 15));
		t.setFill(Color.WHITE);
		Text t2 = new Text();
		t2 = new Text (50, 200, "Lives left:\n\n" + player.getCollectedStars());
		l = new Label(Integer.toString(player.getCollectedStars()));
		l.setTextFill(Color.WHITE);
		root.getChildren().add( l );
		//Label l2= new Label();
		t2.setFont(Font.font ("Montserrat", 15));
		t2.setFill(Color.WHITE);
		root.getChildren().addAll(t, t2);
        
        button2 = new Button("Pause");
        button2.setLayoutX(1000);
        button2.setLayoutY(250);
        root.getChildren().add( button2 ); 
        btn = new Button();
        btn.setText("Save");
        btn.setLayoutX(1003);
        btn.setLayoutY(300);
        root.getChildren().add(btn);
        gc = canvas.getGraphicsContext2D();
        ball=new Ball();
        obstacle1 = new Obstacle1();
//      obstacle1.img.setLayoutX(1000);
        obstacle2 = new Obstacle2();
//      obstacle2.img.setLayoutX(1000);
        //root.getChildren().remove(l);
        final long startNanoTime = System.nanoTime();
        //canvas.setOnMouseClicked(event);
        
        canvas.setFocusTraversable(true);
        // canvas.requestFocus();
        canvas.setOnKeyPressed(keyPressed);
        canvas.setOnKeyReleased(keyReleased);
        button2.setOnAction(event);
        btn.setOnAction(event2);
        
        stack.getChildren().addAll(obstacle1.getImg() );
        stack.setLayoutX(Main.screenWidth/2 - obstacle1.getWidth()/2);
        stack.setLayoutY(0);
        root.getChildren().add(stack);
        
        stack2.getChildren().addAll( obstacle2.getImg());
        stack2.setLayoutX(Main.screenWidth/2 - 3*obstacle2.getWidth()/4);
        stack2.setLayoutY(0);
        root.getChildren().add(stack2);
        
        ccr[1]=new Colourchanger();
        stack3.getChildren().addAll( ccr[1].getImg());
        stack3.setLayoutX(Main.screenWidth/2 - ccr[1].getWidth()/2);
        stack3.setLayoutY(ccr[1].getY());
        root.getChildren().add(stack3);
        
        star1[1]=new Star();
        stack4.getChildren().addAll( star1[1].getImg());
        stack4.setLayoutX(Main.screenWidth/2 - star1[1].getWidth()/2);
        stack4.setLayoutY(star1[1].getY());
        root.getChildren().add(stack4);
        
        theStage.show();
        timer.start(); 
    }
	
	private AnimationTimer timer = new AnimationTimer()
    {
		   public void handle(long currentNanoTime)
	        {
			   // background clears canvas
	            gc.clearRect(0, 0, Main.screenWidth, Main.screenHeight);

	            if (pause) {
	            	//return;
	            }
	            
	            if(pause==false) {
	            	if(angle2>2200)
	            		angle2=0;
	            	Duration rotateDuration = Duration.millis(3);
	            	
	        	    Rotate rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	        	    // obstacle1.img.getTransforms().add(rotate);
	        	    
	        	    int ddd= 500;
	        	    long t2 = System.nanoTime() - startNanoTime;
	        	    timeline = new Timeline( 
	        	    		new KeyFrame(Duration.ZERO, new KeyValue(obstacle1.getImg().rotateProperty(), angle)), // initial rotate
	        	            new KeyFrame(rotateDuration, new KeyValue(obstacle1.getImg().rotateProperty(), angle+2)),
	        	            new KeyFrame(Duration.ZERO, new KeyValue(obstacle1.getImg().translateYProperty(), angle2)),
	        	            new KeyFrame(rotateDuration, new KeyValue(obstacle1.getImg().translateYProperty(), angle2+2)) ,
	        	            
	        	            new KeyFrame(Duration.ZERO, new KeyValue(obstacle2.getImg().rotateProperty(), angle )), // initial rotate
	        	            new KeyFrame(rotateDuration, new KeyValue(obstacle2.getImg().rotateProperty(), angle+2 )),
	        	            new KeyFrame(Duration.ZERO, new KeyValue(obstacle2.getImg().translateYProperty(), angle2 - 3*ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(obstacle2.getImg().translateYProperty(), angle2+2 - 3*ddd)),
	        	            
	        	            new KeyFrame(Duration.ZERO, new KeyValue(ccr[1].getImg().translateYProperty(), angle2 - ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(ccr[1].getImg().translateYProperty(), angle2+2 - ddd)),
	        	         
	        	            new KeyFrame(Duration.ZERO, new KeyValue(star1[1].getImg().translateYProperty(), angle2 - 2*ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(star1[1].getImg().translateYProperty(), angle2+2 - 2*ddd))
	        	            );
	        	    angle=angle+2;
	        	    
	                timeline.setCycleCount(Timeline.INDEFINITE);
	                timeline.setAutoReverse(false);
	                timeline.play();
	                
		            double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
		            double x = ball.getX();
		            //double x = Main.screenWidth/2 - ball.ballimg.getFitWidth(); //set to middle of page
		            Random rand = new Random(); 
					 if (y>=angle2 - ddd-20 && y<=angle2 - ddd-10) {
						 ball.change_colour();
					 }
					 if (y>=angle2 - 2*ddd-20 && y<=angle2 - 2*ddd-10) {
						 player.setCollectedStars(player.getCollectedStars()+1);
						 l.setText(Integer.toString(player.getCollectedStars())); 
					 }
					 
					 /*
					 System.out.println("y");
					 	System.out.println(y);
					 	System.out.println(angle2 -5 + obstacle1.getWidth()/2);
					 	System.out.println(angle2 - ddd-5 + ccr[1].getWidth()/2);
					 	System.out.println(angle2 - 2*ddd-5 + star1[1].getWidth()/2);
					 	System.out.println(angle2 - 3*ddd-5 +obstacle2.getWidth()/2);*/
				 	 if (y<350 && y>150  )
				 		 y=y+2*leftPaddleDY+1;
				 	 else if (y>=250) {
				 		angle2=(int) (angle2-leftPaddleDY -1);
				 		y=y+leftPaddleDY;
				 	 }
				 	 else if (y<250)	{
				 		angle2=(int) (angle2-2*leftPaddleDY );
				 		y=y+1;
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
        		stage.close();
        		saveGame(stage);
        	}
        	else if(src.getText().equals("Save and exit")) {
        		stage.close();
        		Platform.exit(); // or displayMainmenu
        		
        	}
        	else if(src.getText().equals("Back")) {
        		stage.close();
        		showSaveresmenu(stage);
        	}
        	else if(src.getText().equals("Save life and resume game")) {
        		player.resurrect();
        		stage.close();
        	}
        	else if(src.getText().equals("Restart game")) {
        		stage.close();
        		try {
					start(stage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
        	}
        	else if(src.getText().equals("Exit game")) {
        		stage.close();
        		Platform.exit(); // or displayMainmenu
        		
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
                    leftPaddleDY = -2;
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
	        //l.setText(String.valueOf(x3));
	        pause=!(pause);
	        
        } 
    };
    EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) 
        { 
	        x3++;
	        //l.setText(String.valueOf(x3));
	        stage = new Stage(); 
	        showSaveresmenu(stage);
	        // l.setText("   button   selected    "); 
	        pause=!(pause);
	        //Platform.exit();
        } 
    };
    public void saveGame(Stage theStage){
    	pane = new GridPane();
    	pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
    	Scene scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
		VBox vbox = new VBox();
		Text t = new Text();
		t = new Text (10, 20, "Save Game\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);
		
		Text t2 = new Text();
		t2 = new Text (10, 20, "Enter a name for the saved game:\n");
		t2.setFont(Font.font ("Montserrat", 15));
		t2.setFill(Color.WHITE);
		
		TextField tf = new TextField();
		Button btn1 = new Button("Save and exit");
		Button btn2 = new Button("Back");
		buttonHandler bh = new buttonHandler();

		btn1.setOnAction(bh);
		btn2.setOnAction(bh);
		vbox.getChildren().addAll(t, t2, tf, btn1, btn2);
		pane.getChildren().addAll(vbox);
		theStage.setTitle("Colour Switch: Save game");
		theStage.setScene(scene);
		theStage.show();
	}
    private void showSaveresmenu(Stage theStage) {
    	pane = new GridPane();
    	pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
    	Scene scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
		VBox vbox = new VBox(5);
        stage = theStage;
        Text t = new Text();
    	t = new Text (10, 20, "Color Switch\n");
    	t.setFont(Font.font ("Montserrat", 20));
    	t.setFill(Color.WHITE);
    	Button btn1 = new Button("Save game");
    	
    	Button btn3 = new Button("Resume");
    	buttonHandler bh = new buttonHandler();
        btn1.setOnAction(bh);
       
        btn3.setOnAction(bh);
        vbox.getChildren().addAll(t, btn1, btn3);
        pane.getChildren().addAll(vbox);
        theStage.setTitle("Colour Switch");
        theStage.setScene(scene);
        theStage.show();
	}
    public void showResurrectmenu(Stage theStage){
		stage = theStage;
		pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
	    Scene scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
		VBox vbox = new VBox(5);
		Text t = new Text();
		t = new Text (10, 20, "Do you want to use a star and continue playing?\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);

		Button btn1 = new Button("Save life and resume game");
		Button btn2 = new Button("Restart game");
		Button btn3 = new Button("Exit game");

		buttonHandler bh = new buttonHandler();

		btn1.setOnAction(bh);
		btn2.setOnAction(bh);
		btn3.setOnAction(bh);

		vbox.getChildren().addAll(t, btn1, btn2, btn3);

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
    static private int ballwidth;
    
    public Ball() throws FileNotFoundException {
    	red = new Image("file:images/red.png");
    	yellow = new Image("file:images/yellow.png");
    	blue = new Image("file:images/blue.png");
    	green = new Image("file:images/green.png");
    	
        ballimg = new ImageView(this.get_ball());
        ballwidth = 60;
        ballimg.setFitWidth(ballwidth);
        ballimg.setPreserveRatio(true);
        this.x = Main.screenWidth/2 - this.ballwidth/2;
        
    }
//    public void change_colour(int a, int b) {
    public void change_colour() {
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
		this.collectedstars = 0;
		this.score = 0;
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
	static private Image singlecircle;
	static private ImageView img;
	static private int width;
	
	public Star() throws FileNotFoundException{
		this.singlecircle = new Image("file:images/star.png");
		this.img = new ImageView(this.singlecircle);
		width = 100;
		img.setFitWidth(width);
		img.setPreserveRatio(true);
		this.y = 0;
	}
	
	public static ImageView getImg() {
		return img;
	}
	public static int getWidth() {
		return width;
	}
	public int getY() {
		return this.y;
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
	static private Image singlecircle;
	static private ImageView img;
	static private int width;

	public Colourchanger() throws FileNotFoundException{
		this.singlecircle = new Image("file:images/colourchanger.png");
		this.img = new ImageView(this.singlecircle);
		width = 100;
		img.setFitWidth(width);
		img.setPreserveRatio(true);
		this.y = 0;
	}
	
	public static ImageView getImg() {
		return img;
	}
	public static int getWidth() {
		return width;
	}
	public int[] getColours() {
		return this.colours;
	}
	public void setColours(int colour) {
		
	}
	public int getY() {
		return this.y;
	}
	public void setY(int y) {
		
	}
	public boolean changeColour(Ball ball) {
		if(ball.getY() >= this.y-50 && ball.getY() <= this.y+50) {
			return true;
		}
		return false;
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
	
	static private Image singlecircle;
	static private ImageView img;
	static private int width;
	
	public Obstacle1 () throws FileNotFoundException{
		this.singlecircle = new Image("file:images/obstacle1.png");
		this.img = new ImageView(this.singlecircle);
		width = 250;
		img.setFitWidth(width);
		img.setPreserveRatio(true);
	}
	
	public static ImageView getImg() {
		return img;
	}

	public static int getWidth() {
		return width;
	}

	public void setLayoutX(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void movement(float duration, Canvas canvas) {
	
	    Timeline timeline;
	    timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
		
	}
}
class Obstacle2 extends Obstacle {
	
	static private Image singlecircle;
	static private ImageView img;
	static private int width;
	
	public Obstacle2() throws FileNotFoundException{
		this.singlecircle = new Image("file:images/obstacle2.png");
		this.img = new ImageView(this.singlecircle);
		 img.setX(600-Main.screenWidth);
         img.setY(300-Main.screenHeight);
         width = 250;
         img.setFitWidth(getWidth());
         img.setPreserveRatio(true);
	}

	public static ImageView getImg() {
		return img;
	}

	public static int getWidth() {
		return width;
	}

	@Override
	protected void movement(float duration, Canvas canvas) {
	
	    Timeline timeline;
	    timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
		
	}
}
