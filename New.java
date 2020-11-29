package application;
//package colorswitch;

import javafx.util.Duration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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

class SaveGame{
	
	public static String filename;
	public static String savedfiles[];
	
	public static void save(Serializable obj) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public static SaveObject load(String filename) {
		
		if(checkFileExists(filename)) {
			FileInputStream fis = null;
			SaveObject loadedObject = null;
			try {
				fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedObject = (SaveObject)ois.readObject();
				ois.close();
				
//				System.out.println(loadedObject.getGame().getGravity()+" "+loadedObject.getGame().getAngle2());
//				File directoryPath = new File("C:\\Users\\Kirthana\\eclipse-workspace\\colorswitch");
//			    String contents[] = directoryPath.list();
//			    for(int i=0; i<contents.length; i++) {
//			         System.out.println(contents[i]);
//			    }
			}
			catch(ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			return loadedObject;
		}
		return null;
	}
	public static boolean checkFileExists(String filename) {
		return new File(filename).isFile();
	}
	private static boolean deleteSavedFile(String filename) {
		if(!checkFileExists(filename)) {
			System.out.println("File doesn't exist");
			return true;
		}
		File toDelete = new File(filename);
		if(toDelete.canWrite()) {
			return toDelete.delete();
		}
		System.out.println("Cannot delete file");
		return false;
	}
	
}

class SaveObject implements Serializable{
	
	private static final long serialVersionUID = 100L;
	private Game game;
	private Player player;
	private Ball ball;
	private Star star[];
	private Colourchanger cc[];
	private Obstacle1 ob1;
	private Obstacle2 ob2;
	private Obstacle3 ob3;
	private Obstacle4 ob4;
	private Obstacle5 ob5;
	
	public SaveObject(Ball ball, Game game, Player player, Obstacle1 obstacle1, Obstacle2 obstacle2, Obstacle3 obstacle3, Obstacle4 obstacle4, Colourchanger[] ccr, Star[] star1) {
		this.ball = ball;
		this.game = game;
		this.player = player;
		this.ob1 = obstacle1;
		this.ob2 = obstacle2;
		this.ob3 = obstacle3;
		this.ob4 = obstacle4;
		//this.ob5 = obstacle5;
		this.cc = ccr;
		this.star = star;
	}
	public Ball getBall() {
		return this.ball;
	}
	public Player getPlayer() {
		return player;
	}
	public Obstacle1 getOb1() {
		return ob1;
	}
	public Obstacle2 getOb2() {
		return ob2;
	}
	public Obstacle3 getOb3() {
		return ob3;
	}
	public Obstacle4 getOb4() {
		return ob4;
	}
	public Colourchanger[] getCc() {
		return cc;
	}
	public Star[] getStar() {
		return star;
	}
	public Game getGame() {
		return game;
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
	private void resumeGame(Stage theStage, SaveObject data) throws FileNotFoundException{
		game.resume(theStage, data);
	}
	private void exit(){
		Platform.exit();
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

        	else if(src.getText().equals("Game 1")) {
        		SaveObject loadedgame = SaveGame.load("hello.txt");
        		pane.getChildren().clear();
	       		stage.close();
	       		stage = new Stage();
	       		 
	       		try {
						resumeGame(stage, loadedgame);
				} catch (FileNotFoundException e) {
						e.printStackTrace();
				}
        	}
        }
	}
}

class Game extends Application implements Serializable{
	
	private static final long serialVersionUID = 120L;
	private String name;
	private int level;
	private int distance;
	public Game()  {
		// launch();
		//stage = new Stage(); 
        //start(stage);
    }
    public int getGravity() {
		return gravity;
	}
    public int getAngle() {
		return angle;
	}
	public int getAngle2() {
		return angle2;
	}
	private boolean pause=false;
    private transient final long startNanoTime = System.nanoTime();
    private transient GraphicsContext gc;
    private int gravity;
    private Ball ball;
    private Obstacle1 obstacle1;
    private Obstacle2 obstacle2;
    private Obstacle3 obstacle3;
    private Obstacle4 obstacle4;
    private Star[] star1;
    private Colourchanger[] ccr;
    private Player player;
    private int y=299;
    private int colour=0;
    private transient Button button2;
    private transient Button btn;
    private transient Button btn1;
    private transient Button btn2;
    private transient Button btn3;
    private transient buttonHandler bh;
    private transient Group root;
    private int angle;
    private int angle2;
    private transient Canvas canvas;
    private transient Stage stage;
    private transient Scene scene;
    private transient Scene theScene;
    private transient GridPane pane;
    private transient VBox vbox;
    private transient Timeline timeline;
    private transient StackPane stack = new StackPane();
    private transient StackPane stack1 = new StackPane();
    private transient StackPane stack2 = new StackPane();
    private transient StackPane stack3 = new StackPane();
    private transient StackPane stack4 = new StackPane();

    transient Label l;
    transient Text t;
    transient Text t2;
    transient TextField tf;
    transient Duration rotateDuration;
    transient Rotate rotate;
    transient Random rand;
   
	@Override
    public void start(Stage theStage) throws FileNotFoundException
    {
    	theStage.setTitle( "Colour Switch" );
        angle=0;
        angle2=0;
        //Group 
        root = new Group();
        theScene = new Scene( root , Main.screenWidth, Main.screenHeight );
        theStage.setScene( theScene );
        canvas = new Canvas( Main.screenWidth, Main.screenHeight );
        //root.getChildren().add( canvas );
        stack1.setStyle("-fx-background-color: #202020");
        stack1.getChildren().add(canvas);
        root.getChildren().add( stack1 );
        player = new Player();
        t = new Text();
		t = new Text (50, 100, "Score:\n\n" + player.getScore());
		t.setFont(Font.font ("Montserrat", 15));
		t.setFill(Color.WHITE);
		t2 = new Text();
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
        ball = new Ball();
        obstacle1 = new Obstacle1();
//      obstacle1.img.setLayoutX(1000);
        obstacle2 = new Obstacle2();
        obstacle3 = new Obstacle3();
        obstacle4 = new Obstacle4();
    	star1= new Star[5];
    	ccr=new Colourchanger[5];
    	for(int i = 0; i<5; i++) {
        	ccr[i] = new Colourchanger();
        	star1[i] = new Star();
        }
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
        
        stack.getChildren().addAll(obstacle1.getImg(), obstacle3.getImg(), obstacle4.getImg() );
        stack.setLayoutX(Main.screenWidth/2 - obstacle1.getWidth()/2 );
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
	public void resume(Stage theStage, SaveObject data) throws FileNotFoundException
    {
    	theStage.setTitle( "Colour Switch" );
    	player = data.getPlayer();
        ball = data.getBall();
        obstacle1 = data.getOb1();
        obstacle2 = data.getOb2();
        obstacle3 = data.getOb3();
        obstacle4 = data.getOb4();
        angle = data.getGame().getAngle();
        angle2 = data.getGame().getAngle2();
        
        obstacle1.getImg().setFitWidth(obstacle1.getWidth());
        obstacle1.getImg().setPreserveRatio(true);
        obstacle1.getImg().setX(600-Main.screenWidth);
        obstacle1.getImg().setY(300-Main.screenHeight);
        
        obstacle2.getImg().setFitWidth(obstacle2.getWidth());
        obstacle2.getImg().setPreserveRatio(true);
        obstacle2.getImg().setX(600-Main.screenWidth);
        obstacle2.getImg().setY(300-Main.screenHeight);
        
        obstacle3.getImg().setFitWidth(obstacle3.getWidth());
        obstacle3.getImg().setPreserveRatio(true);
        obstacle3.getImg().setX(600-Main.screenWidth);
        obstacle3.getImg().setY(300-Main.screenHeight);
        
        obstacle4.getImg().setFitWidth(obstacle4.getWidth());
        obstacle4.getImg().setPreserveRatio(true);
        obstacle4.getImg().setX(600-Main.screenWidth);
        obstacle4.getImg().setY(300-Main.screenHeight);
        
//        ccr = data.getCc();
//        star1 = data.getStar();
        ccr = new Colourchanger[5];
        star1 = new Star[5];
//        for(int i = 0; i<5; i++) {
//        	ccr[i] = data.getCc()[i];
//        	star1[i] = data.getStar()[i];
//        }
        for(int i = 0; i<5; i++) {
        	ccr[i] = new Colourchanger();
        	star1[i] = new Star();
        }
        //Group 
        root = new Group();
        theScene = new Scene( root , Main.screenWidth, Main.screenHeight );
        theStage.setScene( theScene );
        canvas = new Canvas( Main.screenWidth, Main.screenHeight );
        //root.getChildren().add( canvas );
        stack1.setStyle("-fx-background-color: #202020");
        stack1.getChildren().add(canvas);
        root.getChildren().add( stack1 );
        t = new Text();
		t = new Text (50, 100, "Score:\n\n" + player.getScore());
		t.setFont(Font.font ("Montserrat", 15));
		t.setFill(Color.WHITE);
		t2 = new Text();
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
        
        stack.getChildren().addAll(obstacle1.getImg(), obstacle3.getImg(), obstacle4.getImg() );
        stack.setLayoutX(Main.screenWidth/2 - obstacle1.getWidth()/2 );
        stack.setLayoutY(0);
        root.getChildren().add(stack);
        
        stack2.getChildren().addAll( obstacle2.getImg());
        stack2.setLayoutX(Main.screenWidth/2 - 3*obstacle2.getWidth()/4);
        stack2.setLayoutY(0);
        root.getChildren().add(stack2);
        
        stack3.getChildren().addAll( ccr[1].getImg());
        stack3.setLayoutX(Main.screenWidth/2 - ccr[1].getWidth()/2);
        stack3.setLayoutY(ccr[1].getY());
        root.getChildren().add(stack3);
        
        stack4.getChildren().addAll( star1[1].getImg());
        stack4.setLayoutX(Main.screenWidth/2 - star1[1].getWidth()/2);
        stack4.setLayoutY(star1[1].getY());
        root.getChildren().add(stack4);
        
        theStage.show();
        timer.start(); 
    }
	private transient AnimationTimer timer = new AnimationTimer()
    {
		   public void handle(long currentNanoTime)
	        {
			   // background clears canvas
	            gc.clearRect(0, 0, Main.screenWidth, Main.screenHeight);
	            if (pause) {
	            	//return;
	            }
	            y = ball.getY();
	            
	            if(pause==false) {
	            	if(getAngle2()>3200)
	            		angle2=0;
	            	if(getAngle2()<-100)
	            		angle2=3200;
	            	rotateDuration = Duration.millis(3);
	            	
	        	    rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	        	    // obstacle1.img.getTransforms().add(rotate);
	        	    
	        	    int ddd= 500;
	        	    long t2 = System.nanoTime() - startNanoTime;
	        	    timeline = new Timeline( 
	        	    	       	            
	        	            new KeyFrame(Duration.ZERO, new KeyValue(ccr[1].getImg().translateYProperty(), getAngle2() - ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(ccr[1].getImg().translateYProperty(), getAngle2()+2 - ddd)),
	        	         
	        	            new KeyFrame(Duration.ZERO, new KeyValue(star1[1].getImg().translateYProperty(), getAngle2() - 2*ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(star1[1].getImg().translateYProperty(), getAngle2()+2 - 2*ddd))
	        	            );
	        	    angle=angle+2;
	        	    
	                timeline.setCycleCount(Timeline.INDEFINITE);
	                timeline.setAutoReverse(false);
	                timeline.play();
	                obstacle1.movement(angle, getAngle2(), canvas);
	                obstacle2.movement(angle, getAngle2() - 3*ddd, canvas);
	                obstacle3.movement(angle, getAngle2() - 4*ddd, canvas);
	                obstacle4.movement(angle, getAngle2() - 5*ddd, canvas);
		            double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
		            double x = ball.getX();
		            //double x = Main.screenWidth/2 - ball.ballimg.getFitWidth(); //set to middle of page
		            rand = new Random(); 
					 if (y>=getAngle2() - ddd-20 && y<=getAngle2() - ddd-10) {
						 ball.change_colour();
					 }
					 if (y>=getAngle2() - 2*ddd-20 && y<=getAngle2() - 2*ddd-10) {
						
						 player.setCollectedStars(player.getCollectedStars()+1);
						 l.setText(Integer.toString(player.getCollectedStars())); 
						
					 }
					 //obstacle 1
					 if (y>=getAngle2() -20 && y<=getAngle2() -10) {
						
						 System.out.println("collup");
						 System.out.println(angle%360);
						
						 int col=-1;
						 if(angle%360 > 0 && angle%360 <90)
							 {System.out.println("red");
							 col =0; }
						 if(angle%360 > 90 && angle%360 <180)
						 { System.out.println("blue");
						 col =1;}
						 if(angle%360 > 180 && angle%360 <270)
						 { System.out.println("yellow");
						 col =2;}
						 if(angle%360 > 270 && angle%360 <360)
						 { System.out.println("green");
						 col =3; }
						 if (col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 showResurrectmenu();
						 }
					 }
					 if (y>=getAngle2() +obstacle1.getWidth() -50 && y<=getAngle2() +obstacle1.getWidth()-40) {
						 
						 System.out.println("colld");
						 System.out.println(angle%360);
						 int col=-1;
						 if(angle%360 > 0 && angle%360 <90)
							 {System.out.println("yellow");
							 col =2; }
						 if(angle%360 > 90 && angle%360 <180)
						 { System.out.println("green");
						 col =3;}
						 if(angle%360 > 180 && angle%360 <270)
						 { System.out.println("red");
						 col =0;}
						 if(angle%360 > 270 && angle%360 <360)
						 { System.out.println("blue");
						 col =1; }
						 if (col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 showResurrectmenu();
						 }
							
					 }
					 //obstacle 3
					 if (y>=angle2- 4*ddd -20 && y<=angle2 - 4*ddd-10) {
							
						 System.out.println("collup");
						 System.out.println(angle%360);
						int abc =(angle-45)%360;
						 int col=-1;
						 if(abc > 0 && abc <90)
							 {System.out.println("red");
							 col =0; }
						 if(abc > 90 && abc <180)
						 { System.out.println("blue");
						 col =1;}
						 if(abc > 180 && abc <270)
						 { System.out.println("yellow");
						 col =2;}
						 if(abc > 270 && abc <360)
						 { System.out.println("green");
						 col =3; }
						 if (col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 showResurrectmenu();
						 }
					 }
					 if (y>=angle2 +obstacle3.getWidth() - 4*ddd-50 && y<=angle2 +obstacle3.getWidth()-40- 4*ddd) {
						 
						 System.out.println("colld");
						 System.out.println(angle%360);
						 int col=-1;
						 int abc =(angle-45)%360;
						 if(abc > 0 &&abc <90)
							 {System.out.println("yellow");
							 col =2; }
						 if(abc > 90 && abc <180)
						 { System.out.println("green");
						 col =3;}
						 if(abc > 180 && abc <270)
						 { System.out.println("red");
						 col =0;}
						 if(abc > 270 && abc <360)
						 { System.out.println("blue");
						 col =1; }
						 if (col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 showResurrectmenu();
						 }
							
					 }
					 
					 /*
					 System.out.println("y");
					 	System.out.println(y);
					 	*/
				 	 if (y<350 && y>150  ) {
				 		 y=y+2*getGravity()+1;
				 		 ball.setY(y);
				 	 }
				 	 else if (y>=250) {
				 		angle2=(int) (getAngle2()-getGravity() -1);
				 		y=y+getGravity();
				 		ball.setY(y);
				 		System.out.println("up");
				 	 }
				 	 else if (y<250)	{
				 		angle2=(int) (getAngle2()-2*getGravity() );
				 		y=y+1;
				 		ball.setY(y);
				 		System.out.println("down");
				 	 }
				 	 
					 gc.drawImage(ball.get_ball(), x, y, 30, 30);
					 
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
        		saveGameMenu(stage);
        	}
        	else if(src.getText().equals("Save and exit")) {
        		
        		SaveGame.filename = tf.getText()+".txt"; 
        		saveGame();
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
        		pause = !pause;
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

    private transient EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            // set movement to 0, if the released key was responsible for the paddle
            switch (event.getCode()) {
                case UP:
                	gravity = 0;
                	break;
            }
        }
    };
    private transient EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            // start movement according to key pressed
            switch (event.getCode()) {
                case UP:
                    gravity = -2;
                    break;
                case P:
                   pause =!(pause);
            }
        }
    };
    
    private transient EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) { 
	        pause=!(pause);	        
        } 
    };
    private transient EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) { 
	        stage = new Stage(); 
	        showSaveresmenu(stage);
	        pause=!(pause);
        } 
    };
    
    public void saveGame() {
    	SaveObject gametosave = new SaveObject(this.ball, this, this.player, this.obstacle1, this.obstacle2, this.obstacle3, this.obstacle4, this.ccr, this.star1);
    	SaveGame.save(gametosave);
    }
    public void saveGameMenu(Stage theStage){
    	pane = new GridPane();
    	pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
    	scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
		vbox = new VBox(5);
		t = new Text();
		t = new Text (10, 20, "Save Game\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);
		
		t2 = new Text();
		t2 = new Text (10, 20, "Enter a name for the saved game:\n");
		t2.setFont(Font.font ("Montserrat", 15));
		t2.setFill(Color.WHITE);
		
		tf = new TextField();
		btn1 = new Button("Save and exit");
		btn2 = new Button("Back");
		bh = new buttonHandler();

		btn1.setOnAction(bh);
		btn2.setOnAction(bh);
		tf.setOnAction(bh);
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
    	scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
		vbox = new VBox(5);
        stage = theStage;
        t = new Text();
    	t = new Text (10, 20, "Color Switch\n");
    	t.setFont(Font.font ("Montserrat", 20));
    	t.setFill(Color.WHITE);
    	btn1 = new Button("Save game");
    	
    	btn3 = new Button("Resume");
    	bh = new buttonHandler();
        btn1.setOnAction(bh);
        btn3.setOnAction(bh);
        vbox.getChildren().addAll(t, btn1, btn3);
        pane.getChildren().addAll(vbox);
        theStage.setTitle("Colour Switch");
        theStage.setScene(scene);
        theStage.show();
	}
    public void showResurrectmenu(){
		stage = new Stage();
		pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
	    scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
		vbox = new VBox(5);
		t = new Text();
		t = new Text (10, 20, "Do you want to use a star and continue playing?\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);

		btn1 = new Button("Save life and resume game");
		btn2 = new Button("Restart game");
		btn3 = new Button("Exit game");

		bh = new buttonHandler();

		btn1.setOnAction(bh);
		btn2.setOnAction(bh);
		btn3.setOnAction(bh);

		vbox.getChildren().addAll(t, btn1, btn2, btn3);

		pane.getChildren().addAll(vbox);
		stage.setTitle("Colour Switch");
		stage.setScene(scene);
		stage.show();
	}

}
class Ball implements Serializable{
	
	private static final long serialVersionUID = 140L;
	private static int x = Main.screenWidth/2 - 15;
	private int y;
	private int colour;
	private static Image red = new Image("file:images/red.png");
	private static Image yellow = new Image("file:images/yellow.png");
	private static Image blue = new Image("file:images/blue.png");
	private static Image green = new Image("file:images/green.png");
    private static int ballwidth = 30;
    private transient Random rand;
    
    public Ball() throws FileNotFoundException {
    	
        //this.x = Main.screenWidth/2 - 10;
        this.y = 299;
    }
    public void change_colour() {
		rand = new Random(); 
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
	public static int getBallwidth() {
		return ballwidth;
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
		if(this.colour==0) {
	        return this.red;
		}
		if(this.colour==1) {
			return this.blue;
		}
		if(this.colour==2) {
			return this.yellow;
		}
		if(this.colour==3) {
			return this.green;
		}
		return this.red;
	}
	
}
 class Player implements Serializable{
	 
	private static final long serialVersionUID = 160L;
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
		this.collectedstars -= 4;
	}
	public void moveBall() {
		
	}
}
 class Star implements Serializable{
	 
	private static final long serialVersionUID = 180L;
	private int y;
	private Player player;
	private static transient Image pic = new Image("file:images/star.png");
	private static transient ImageView img = new ImageView(pic);
	private static int width = 80;
	
	public Star() throws FileNotFoundException{
		
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
 class Colourchanger implements Serializable{
	
	private static final long serialVersionUID = 200L;
	private transient int[] colours;
	private int y;
	private Ball ball;
	private static Image pic = new Image("file:images/colourchanger.png");
	private static ImageView img = new ImageView(pic);
	private static int width = 100;

	public Colourchanger() throws FileNotFoundException{
		
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
abstract class Obstacle implements Serializable{
	
	protected int noofcolours;
	protected int passposition;
	
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
	protected boolean obstacleHit(double y2) {
		if(y2 >= passposition-100 && y2 <= passposition+100) {
			return false;
		}
		else {
			return true;
		}
	}
	protected abstract void movement(float duration, int a, Canvas canvas);
}
class Obstacle1 extends Obstacle{
	
	private static final long serialVersionUID = 220L;
	private static transient Image pic = new Image("file:images/obstacle1.png");
	private static ImageView img = new ImageView(pic);
	private static int width = 250;
	
	public Obstacle1 () throws FileNotFoundException{
		
		img.setFitWidth(width);
		img.setPreserveRatio(true);
		img.setX(600-Main.screenWidth);
        img.setY(300-Main.screenHeight);
	}
	
	public static ImageView getImg() {
		return img;
	}

	public static int getWidth() {
		return width;
	}

	@Override
	protected void movement(float duration, int angle2, Canvas canvas) {
	
	    Timeline timeline;
	    timeline = new Timeline();
	    Duration rotateDuration = Duration.millis(3);
    	
	    Rotate rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	    // obstacle1.img.getTransforms().add(rotate);
	    
	    int ddd= 500;
	    float angle =duration;
	   
	    timeline = new Timeline( 
	    		new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().rotateProperty(), angle)), // initial rotate
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().rotateProperty(), angle+2)),
	            new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().translateYProperty(), angle2)),
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().translateYProperty(), angle2+2)) 

	            );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
	   // return timeline;
		
	}
}
class Obstacle2 extends Obstacle {
	
	private static final long serialVersionUID = 240L;
	private static transient Image pic = new Image("file:images/obstacle2.png");
	private static transient ImageView img = new ImageView(pic);
	private static int width = 250;
	transient Timeline timeline;
	transient Duration rotateDuration;
	transient Rotate rotate;
	
	public Obstacle2() throws FileNotFoundException{
		
		 img.setX(600-Main.screenWidth);
         img.setY(300-Main.screenHeight);
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
	protected void movement(float duration,int angle2, Canvas canvas) {
		  
	    timeline = new Timeline();
	    rotateDuration = Duration.millis(3);
    	
	    rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	    // obstacle1.img.getTransforms().add(rotate);
	    
	    int ddd= 500;
	    float angle =duration;
	   
	    timeline = new Timeline( 
	    		new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().rotateProperty(), angle)), // initial rotate
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().rotateProperty(), angle+2)),
	            new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().translateYProperty(), angle2)),
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().translateYProperty(), angle2+2)) 

	            );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
	}
}
class Obstacle3 extends Obstacle {
	
	private static final long serialVersionUID = 260L;
	private static transient Image pic = new Image("file:images/obstacle3.png");
	private static transient ImageView img = new ImageView(pic);
	private static int width = 250;
	transient Timeline timeline;
	transient Duration rotateDuration;
	transient Rotate rotate;
	
	public Obstacle3() throws FileNotFoundException{
		
		 img.setX(600-Main.screenWidth);
         img.setY(300-Main.screenHeight);
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
	protected void movement(float duration,int angle2, Canvas canvas) {
		  
	    timeline = new Timeline();
	    rotateDuration = Duration.millis(3);
    	
	    rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	    // obstacle1.img.getTransforms().add(rotate);
	    
	    int ddd= 500;
	    float angle =duration;
	   
	    timeline = new Timeline( 
	    		new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().rotateProperty(), angle)), // initial rotate
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().rotateProperty(), angle+2)),
	            new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().translateYProperty(), angle2)),
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().translateYProperty(), angle2+2)) 

	            );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
	}
}
class Obstacle4 extends Obstacle {
	
	private static final long serialVersionUID = 280L;
	private static transient Image pic = new Image("file:images/obstacle4.png");
	private static transient ImageView img = new ImageView(pic);
	private static int width = 250;
	transient Timeline timeline;
	transient Duration rotateDuration;
	transient Rotate rotate;
	
	public Obstacle4() throws FileNotFoundException{
		
		 img.setX(600-Main.screenWidth);
         img.setY(300-Main.screenHeight);
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
	protected void movement(float duration,int angle2, Canvas canvas) {
		  
	    timeline = new Timeline();
	    rotateDuration = Duration.millis(3);
    	
	    rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	    // obstacle1.img.getTransforms().add(rotate);
	    
	    int ddd= 500;
	    float angle =duration;
	   
	    timeline = new Timeline( 
	    		new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().rotateProperty(), angle)), // initial rotate
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().rotateProperty(), angle+2)),
	            new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().translateYProperty(), angle2)),
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().translateYProperty(), angle2+2)) 

	            );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
	}
}
class Obstacle5 extends Obstacle {
	
	private static final long serialVersionUID = 300L;
	private static transient Image pic = new Image("file:images/obstacle2.png");
	private static transient ImageView img = new ImageView(pic);
	private static int width = 250;
	private transient Timeline timeline;
	private transient Duration rotateDuration;
	private transient Rotate rotate;
	
	public Obstacle5() throws FileNotFoundException{
		
		img.setX(600-Main.screenWidth);
        img.setY(300-Main.screenHeight);
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
	protected void movement(float duration,int angle2, Canvas canvas) {
		  
	    timeline = new Timeline();
	    rotateDuration = Duration.millis(3);
    	
	    rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	    // obstacle1.img.getTransforms().add(rotate);
	    
	    int ddd= 500;
	    float angle =duration;
	   
	    timeline = new Timeline( 
	    		new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().rotateProperty(), angle)), // initial rotate
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().rotateProperty(), angle+2)),
	            new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().translateYProperty(), angle2)),
	            new KeyFrame(rotateDuration, new KeyValue(this.getImg().translateYProperty(), angle2+2)) 

	            );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
	}
}
