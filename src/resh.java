package application;
//package colorswitch;

import javafx.util.Duration;

import java.awt.AWTException;
import java.awt.Robot;
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
import java.util.concurrent.TimeUnit;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.*;

public class Main extends Application {
	
	static int screenWidth = 1200;
	static int screenHeight = 600;
    static GridPane pane = new GridPane();
    static Scene scene = new Scene(pane, screenWidth, screenHeight);
    static Stage stage;
	StackPane pane2 = new StackPane();
	
	
	public static void main(String[] args) {
		
		launch(args);
	}
	@Override
	public void start(Stage theStage) throws Exception {
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
	private Obstacle6 ob6;
	
	public SaveObject(Ball ball, Game game, Player player, Obstacle1 obstacle1, Obstacle2 obstacle2, Obstacle3 obstacle3, Obstacle4 obstacle4, Obstacle6 obstacle6, Colourchanger[] ccr, Star[] star1) {
		this.ball = ball;
		this.game = game;
		this.player = player;
		this.ob1 = obstacle1;
		this.ob2 = obstacle2;
		this.ob3 = obstacle3;
		this.ob4 = obstacle4;
		this.ob6 = obstacle6;
		//this.ob5 = obstacle5;
		this.cc = ccr;
		this.star = star1;
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
	public Obstacle6 getOb6() {
		return ob6;
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
	private static Game game;
	private int bestscore;
	private ArrayList<Button> buttons;
	
	public Homepage() {
		game = new Game();
		buttons = new ArrayList<Button>();
		bestscore = 0;
	}
	public int getBestscore(){
		return this.bestscore;
	}
	public void setBestscore(int best){
		this.bestscore = best;
	}
	public static void startNewgame(Stage stage) throws FileNotFoundException{	
		game.start(stage);
	}
	private void resumeGame(Stage theStage, SaveObject data) throws FileNotFoundException{
		game.resume(theStage, data);
	}
	private void exit(){
		Platform.exit();
	}
	public void displayMainmenu(Stage theStage, GridPane pane, Scene scene){
		stage = theStage;
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(150);
	    pane.setVgap(150);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
	    pane.setId("pane");
//	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
		VBox vbox = new VBox(5);
		vbox.setSpacing(10);
		Text t = new Text();
		t = new Text (10, 20, "Welcome to Color Switch!\n");
		t.setFont(Font.font ("Montserrat", 25));
		t.setFill(Color.WHITE);
		
//		Image img = new Image("file:images/settings.png");
//      ImageView view = new ImageView(img);
//      view.setFitHeight(50);
//      view.setPreserveRatio(true);

		Button btn1 = new Button("Start new game");
		Button btn2 = new Button("Resume a saved game");
		Button button2 = new Button("View Leaderboard");
		Button button = new Button("Settings");
		Button btn3 = new Button("Exit");

		buttonHandler bh = new buttonHandler();

		btn1.setOnAction(bh);
		btn2.setOnAction(bh);
		btn3.setOnAction(bh);
		button.setOnAction(bh);
		button2.setOnAction(bh);

		vbox.getChildren().addAll(t, btn1, btn2, button2, button, btn3);
		pane.getChildren().addAll(vbox);
		theStage.setTitle("Colour Switch");
		theStage.setScene(scene);
		theStage.show();
	}
	
	public void showSavedgames(Stage theStage){
		stage = theStage;
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
	    pane.setId("pane");
//	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
		VBox vbox = new VBox(5);
		Text t = new Text();
		t = new Text (10, 20, "Saved Games\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);
		Text t2 = new Text();
		t2 = new Text (10, 20, "Choose a game to start playing:\n");
		t2.setFont(Font.font ("Montserrat", 15));
		t2.setFill(Color.WHITE);
		vbox.getChildren().addAll(t, t2);

		buttonHandler bh = new buttonHandler();
		gamebuttonsHandler gh = new gamebuttonsHandler();
		
//		File directoryPath = new File("C:\\Users\\Kirthana\\eclipse-workspace\\colorswitch");
		String path = new File("").getAbsolutePath();
		File directoryPath = new File(path);
		String contents[] = directoryPath.list();
	    for(int i=0; i<contents.length; i++) {
	    	if(contents[i].substring(contents[i].length() - 4).equals(".txt")) {
	    		buttons.add(new Button(contents[i].substring(0, contents[i].length() - 4)));
	    	}
	    }
	    for (Button b : buttons) {
	    	b.setOnAction(gh);
			vbox.getChildren().add(b);
	    }
		
		Button btn4 = new Button("Back");
		btn4.setOnAction(bh);

		pane.getChildren().addAll(vbox);
		theStage.setTitle("Colour Switch");
		theStage.setScene(scene);
		theStage.show();
	}
	public void displaySettings(Stage theStage, GridPane pane, Scene scene){
		stage = theStage;
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
	    pane.setId("pane");
//	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
		VBox vbox = new VBox(5);
		Text t = new Text();
		t = new Text (10, 20, "Settings\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);
		
		Button btn1 = new Button("Play music");
		Button btn2 = new Button("Play sound effects");
		Button btn3 = new Button("Back");

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
	public static class Row {
		 
        private final String gamename;
        private final int score;
 
        private Row(String filename, int score) {
            this.gamename = filename;
            this.score = score;
        }

		public String getGamename() {
			return gamename;
		}

		public int getScore() {
			return score;
		}
    }
	public void displayLeaderboard(Stage theStage, GridPane pane, Scene scene){
		stage = theStage;
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(20);
	    pane.setVgap(20);
	    pane.setPadding(new Insets(100, 80, 100, 80));
	    pane.setStyle("-fx-background-color: #202020");
	    pane.setId("pane");
//	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
	    VBox vbox = new VBox(5);
		Text t = new Text();
		t = new Text (10, 20, "Leaderboard\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);
		
		TableView<Row> table = new TableView();
		table.setMinWidth(300);
        TableColumn<Row, String> gamename = new TableColumn("Game Name");
        TableColumn<Row, Integer> score = new TableColumn("Score");
        
        gamename.setMinWidth(150);
        score.setMinWidth(150);
        score.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().addAll(score, gamename);
        table.sort();
		
//		File directoryPath = new File("C:\\Users\\Kirthana\\eclipse-workspace\\colorswitch");
	    String path = new File("").getAbsolutePath();
		File directoryPath = new File(path);
		String contents[] = directoryPath.list();
		int s;
		ObservableList<Row> data = FXCollections.observableArrayList();
	    for(int i=0; i<contents.length; i++) {
	    	String txt = contents[i].substring(contents[i].length() - 4);
        	String filename = contents[i].substring(0, contents[i].length() - 4);
	    	if(txt.equals(".txt")) {
        		SaveObject loadedgame = SaveGame.load(contents[i]);
        		s = loadedgame.getPlayer().getScore();
        		data.add(new Row(filename, s));
	    	}
	    }
	    gamename.setCellValueFactory(new PropertyValueFactory<Row, String>("gamename"));
	    score.setCellValueFactory(new PropertyValueFactory<Row, Integer>("score"));
	    table.setItems(data);
	    table.getColumns().addAll(gamename, score);
	    
		Button btn1 = new Button("Back");
		Button btn2 = new Button("Exit");

		buttonHandler bh = new buttonHandler();

		btn1.setOnAction(bh);
		btn2.setOnAction(bh);

//		pane.getChildren().add(t);
//		pane.getChildren().add(table);
//		pane.getChildren().add(btn1);
//		pane.getChildren().add(btn2);
		
		vbox.getChildren().addAll(t, table, btn1, btn2);
		pane.getChildren().add(vbox);
		
		theStage.setTitle("Colour Switch");
		theStage.setScene(scene);
		theStage.show();
	}
	private class gamebuttonsHandler implements javafx.event.EventHandler<ActionEvent>{

		@Override
        public void handle(ActionEvent event) {

			var src = (Button) event.getSource();
        	SaveObject loadedgame = SaveGame.load(src.getText()+".txt");
    		pane.getChildren().clear();
       		stage.close();
       		//stage = new Stage();
       		 
       		try {
					resumeGame(stage, loadedgame);
			} catch (FileNotFoundException e) {
					e.printStackTrace();
			}
		}
	}
	private class buttonHandler implements javafx.event.EventHandler<ActionEvent> {
		Homepage homepage = new Homepage();
        @Override
        public void handle(ActionEvent event) {

        	var src = (Button) event.getSource();
        	if(src.getText().equals("Exit")) {
        		exit();
        	}
        	else if(src.getText().equals("Start new game")) {
        		
        		 pane.getChildren().clear();
        		 stage.close();
        		 //stage = new Stage();
        		 
        		try {
					startNewgame(stage);
				} catch (FileNotFoundException e) {
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
        	else if(src.getText().equals("Settings")) {
        		pane.getChildren().clear();
       		 	stage.close();
        		displaySettings(stage, pane, scene);
        	}
        	else if(src.getText().equals("View Leaderboard")) {
        		pane.getChildren().clear();
       		 	stage.close();
        		displayLeaderboard(stage, pane, scene);
        	}
        	else if(src.getText().equals("Play music")) {
        		Game.setPlaymusic(true);
        	}
        	else if(src.getText().equals("Play sound effects")) {
        		Game.setPlaysounds(true);
        	}
        }
	}
}

class Game extends Application implements Serializable{
	
	private static final long serialVersionUID = 120L;
	private String name;
	private int level;
	private int distance;
	private static boolean playmusic = false;
	private static boolean playsounds = false;
	private static Homepage homepage = new Homepage();
	
	public Game()  {
		// launch();
		//stage = new Stage(); 
        //start(stage);
    }
    public boolean getPlaymusic() {
		return playmusic;
	}
	public static void setPlaymusic(boolean pm) {
		playmusic = pm;
	}
	public boolean getPlaysounds() {
		return playsounds;
	}
	public static void setPlaysounds(boolean ps) {
		playsounds = ps;
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
	private boolean pause = false;
	private boolean obhit = false;
    private transient final long startNanoTime = System.nanoTime();
    private transient GraphicsContext gc;
    private int gravity;
    private Ball ball;
    private Obstacle1 obstacle1;
    private Obstacle2 obstacle2;
    private Obstacle3 obstacle3;
    private Obstacle4 obstacle4;
    private Obstacle5 obstacle5;
    private Obstacle6 obstacle6;
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
    private int xpos;
    private int xshift;
    private transient Canvas canvas;
    private transient Stage stage;
    private transient Scene scene;
    private transient Scene theScene;
    private transient GridPane pane;
    private transient VBox vbox;
    private transient Timeline timeline;
    private transient StackPane stack;
    private transient StackPane stack1;
    private transient StackPane stack2;
    private transient StackPane stack3;
    private transient StackPane stack4;
    private transient StackPane stack5;
    private transient StackPane stack6;
    private transient StackPane stack7;

    transient Label l;
    transient Label l2;
    transient Text t;
    transient Text t2;
    transient TextField tf;
    transient Duration rotateDuration;
    transient Rotate rotate;
    transient Random rand;
    
    private static MediaPlayer mediaPlayer;
    private static Media media;
   
	@Override
    public void start(Stage theStage) throws FileNotFoundException
    {
    	theStage.setTitle( "Colour Switch" );
    	
    	if(playmusic == true) {
    		String s = "colourswitchmusic.mp3";
    		media = new Media(new File(s).toURI().toString()); 
    		mediaPlayer = new MediaPlayer(media); 
    		mediaPlayer.setVolume(0.5);
    		mediaPlayer.setAutoPlay(true);  
    	}
    	stack = new StackPane();
        stack1 = new StackPane();
        stack2 = new StackPane();
        stack3 = new StackPane();
        stack4 = new StackPane();
        stack5 = new StackPane();
        stack6 = new StackPane();
        stack7 = new StackPane();
        angle=0;
        angle2=0;
        xpos = -40;
        xshift = 3;
        //Group 
        root = new Group();
        theScene = new Scene( root , Main.screenWidth, Main.screenHeight );
        theStage.setScene( theScene );
        canvas = new Canvas( Main.screenWidth, Main.screenHeight );
        //root.getChildren().add( canvas );
        stack1.setStyle("-fx-background-color: #202020");
        stack1.setId("panegame");
//      stack1.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
        stack1.getChildren().add(canvas);
        root.getChildren().add( stack1 );
        player = new Player();
        t = new Text();
		t = new Text (50, 100, "Score:\n\n" );
		t.setFont(Font.font ("Montserrat", 15));
		l2 = new Label(Integer.toString(player.getScore()));
		l2.setTextFill(Color.WHITE);
		l2.setLayoutX(60);
		l2.setLayoutY(120);
		root.getChildren().add( l2 );
		t.setFill(Color.WHITE);
		t2 = new Text();
		t2 = new Text (50, 200, "Lives left:\n\n" );
		l = new Label(Integer.toString(player.getCollectedStars()));
		l.setTextFill(Color.WHITE);
		l.setLayoutX(60);
		l.setLayoutY(220);
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
        obstacle2 = new Obstacle2();
        obstacle3 = new Obstacle3();
        obstacle4 = new Obstacle4();
        obstacle6 = new Obstacle6();
    	star1= new Star[5];
    	ccr=new Colourchanger[5];
    	for(int i = 0; i<5; i++) {
        	ccr[i] = new Colourchanger();
        	star1[i] = new Star();
        }
      //obstacle2.getImg().setLayoutX(1000);
        //root.getChildren().remove(l);
        final long startNanoTime = System.nanoTime();
        //canvas.setOnMouseClicked(event);
        
        canvas.setFocusTraversable(true);
        // canvas.requestFocus();
        canvas.setOnKeyPressed(keyPressed);
        canvas.setOnKeyReleased(keyReleased);
        button2.setOnAction(event);
        btn.setOnAction(event2);
        
        stack.getChildren().addAll(Obstacle1.getImg(), Obstacle3.getImg(), Obstacle4.getImg(), Obstacle6.getImg() );
        stack.setLayoutX(Main.screenWidth/2 - Obstacle1.getWidth()/2 );
        stack.setLayoutY(0);
        root.getChildren().add(stack);
        
        stack2.getChildren().addAll( Obstacle2.getImg());
        stack2.setLayoutX(Main.screenWidth/2);// + Obstacle2.getWidth()/2);
        stack2.setLayoutY(0);
        root.getChildren().add(stack2);

        stack5.getChildren().addAll( Obstacle6.getImg());
        stack5.setLayoutX(100);
        stack5.setLayoutY(0);
        root.getChildren().add(stack5);
        
        ccr[1]=new Colourchanger();
        stack3.getChildren().addAll( ccr[1].getImg());
        stack3.setLayoutX(Main.screenWidth/2 - Colourchanger.getWidth()/2);
        stack3.setLayoutY(ccr[1].getY());
        root.getChildren().add(stack3);
        
        star1[1]=new Star();
        stack4.getChildren().addAll( star1[1].getImg());
        stack4.setLayoutX(Main.screenWidth/2 - Star.getWidth()/2);
        stack4.setLayoutY(star1[1].getY());
        root.getChildren().add(stack4);
        
        ccr[2]=new Colourchanger();
        stack6.getChildren().addAll( ccr[2].getImg());
        stack6.setLayoutX(Main.screenWidth/2 - Colourchanger.getWidth()/2);
        stack6.setLayoutY(ccr[2].getY());
        root.getChildren().add(stack6);
        
        star1[2]=new Star();
        stack7.getChildren().addAll( star1[1].getImg());
        stack7.setLayoutX(Main.screenWidth/2 - Star.getWidth()/2);
        stack7.setLayoutY(star1[2].getY());
        root.getChildren().add(stack7);
        theStage.show();
        timer.start(); 
    }
	public void resume(Stage theStage, SaveObject data) throws FileNotFoundException
    {
    	theStage.setTitle( "Colour Switch" );
    	stack = new StackPane();
        stack1 = new StackPane();
        stack2 = new StackPane();
        stack3 = new StackPane();
        stack4 = new StackPane();
        stack5 = new StackPane();
        stack6 = new StackPane();
        stack7 = new StackPane();
    	player = data.getPlayer();
        ball = data.getBall();
        obstacle1 = data.getOb1();
        obstacle2 = data.getOb2();
        obstacle3 = data.getOb3();
        obstacle4 = data.getOb4();
        obstacle6 = data.getOb6();
        angle = data.getGame().getAngle();
        angle2 = data.getGame().getAngle2();
        
        obstacle1.getImg().setFitWidth(Obstacle1.getWidth());
        obstacle1.getImg().setPreserveRatio(true);
        obstacle1.getImg().setX(600-Main.screenWidth);
        obstacle1.getImg().setY(300-Main.screenHeight);
        
        obstacle2.getImg().setFitWidth(Obstacle2.getWidth());
        obstacle2.getImg().setPreserveRatio(true);
        obstacle2.getImg().setX(600-Main.screenWidth);
        obstacle2.getImg().setY(300-Main.screenHeight);
        
        obstacle3.getImg().setFitWidth(Obstacle3.getWidth());
        obstacle3.getImg().setPreserveRatio(true);
        obstacle3.getImg().setX(600-Main.screenWidth);
        obstacle3.getImg().setY(300-Main.screenHeight);
        
        obstacle4.getImg().setFitWidth(Obstacle4.getWidth());
        obstacle4.getImg().setPreserveRatio(true);
        obstacle4.getImg().setX(600-Main.screenWidth);
        obstacle4.getImg().setY(300-Main.screenHeight);
        
        obstacle6.getImg().setFitWidth(Obstacle6.getWidth());
        obstacle6.getImg().setPreserveRatio(true);
        obstacle6.getImg().setX(50);
        obstacle6.getImg().setY(300-Main.screenHeight);
        
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
		l2 = new Label(Integer.toString(player.getScore()));
		l2.setTextFill(Color.WHITE);
		l2.setLayoutX(60);
		l2.setLayoutY(120);
		root.getChildren().add( l2 );
		t.setFont(Font.font ("Montserrat", 15));
		t.setFill(Color.WHITE);
		t2 = new Text();
		t2 = new Text (50, 200, "Lives left:\n\n" );
		l = new Label(Integer.toString(player.getCollectedStars()));
		l.setTextFill(Color.WHITE);
		l.setLayoutX(60);
		l.setLayoutY(220);
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
        
        stack.getChildren().addAll(Obstacle1.getImg(), Obstacle3.getImg(), Obstacle4.getImg(), Obstacle6.getImg() );
        stack.setLayoutX(Main.screenWidth/2 - Obstacle1.getWidth()/2 );
        stack.setLayoutY(0);
        root.getChildren().add(stack);
        
        stack2.getChildren().addAll( Obstacle2.getImg());
        stack2.setLayoutX(Main.screenWidth/2 );
        stack2.setLayoutY(0);
        root.getChildren().add(stack2);

        stack5.getChildren().addAll( Obstacle6.getImg());
        stack5.setLayoutX(100);
        stack5.setLayoutY(0);
        root.getChildren().add(stack5);
        
        ccr[1]=new Colourchanger();
        stack3.getChildren().addAll( ccr[1].getImg());
        stack3.setLayoutX(Main.screenWidth/2 - Colourchanger.getWidth()/2);
        stack3.setLayoutY(ccr[1].getY());
        root.getChildren().add(stack3);
        
        star1[1]=new Star();
        stack4.getChildren().addAll( star1[1].getImg());
        stack4.setLayoutX(Main.screenWidth/2 - Star.getWidth()/2);
        stack4.setLayoutY(star1[1].getY());
        root.getChildren().add(stack4);
        
        ccr[2]=new Colourchanger();
        stack6.getChildren().addAll( ccr[2].getImg());
        stack6.setLayoutX(Main.screenWidth/2 - Colourchanger.getWidth()/2);
        stack6.setLayoutY(ccr[2].getY());
        root.getChildren().add(stack6);
        
        star1[2]=new Star();
        stack7.getChildren().addAll( star1[2].getImg());
        stack7.setLayoutX(Main.screenWidth/2 - Star.getWidth()/2);
        stack7.setLayoutY(star1[2].getY());
        root.getChildren().add(stack7);
        
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
	            	if(angle2>4700)
	            	{
	            		angle2=0;
	            		stack4.getChildren().get(0).setVisible(true);
	            	}
	            	if(angle2<-100)
	            	{
	            		angle2=4700;
	            	}
	            	
	            	rotateDuration = Duration.millis(3);
	        	    rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	        	    
	        	    int ddd= 500;
	        	    long t2 = System.nanoTime() - startNanoTime;
	        	    timeline = new Timeline( 
	        	    	       	            
	        	            new KeyFrame(Duration.ZERO, new KeyValue(ccr[1].getImg().translateYProperty(), getAngle2() - ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(ccr[1].getImg().translateYProperty(), getAngle2()+2 - ddd)),
	        	         
	        	            new KeyFrame(Duration.ZERO, new KeyValue(star1[1].getImg().translateYProperty(), getAngle2() - 2*ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(star1[1].getImg().translateYProperty(), getAngle2()+2 - 2*ddd)),
	        	            new KeyFrame(Duration.ZERO, new KeyValue(ccr[2].getImg().translateYProperty(), getAngle2() - 7*ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(ccr[2].getImg().translateYProperty(), getAngle2()+2 - 7*ddd)),
	        	         
	        	            new KeyFrame(Duration.ZERO, new KeyValue(star1[2].getImg().translateYProperty(), getAngle2() - 8*ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(star1[2].getImg().translateYProperty(), getAngle2()+2 - 8*ddd))
	        	    );
	        	    angle=angle+2;
	        	    
	                timeline.setCycleCount(Timeline.INDEFINITE);
	                timeline.setAutoReverse(false);
	                timeline.play();
	                obstacle1.movement(angle, angle2, 0, pane);
	                obstacle2.movement(angle, angle2 - 3*ddd, 0, pane);
	                obstacle3.movement(angle, angle2 - 4*ddd, 0, pane);
	                obstacle4.movement(angle, angle2 - 5*ddd, 0, pane);
	                obstacle6.movement(angle, angle2 - 6*ddd, xpos, pane);
		            double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
		            double x = ball.getX();
		            rand = new Random(); 
					 if (y>=angle2 - ddd-20 && y<=angle2 - ddd-10) {
						 ball.change_colour();
						 if(playsounds == true) {
	                    		String s = "colourchangesound.wav";
	                    		media = new Media(new File(s).toURI().toString()); 
	                    		mediaPlayer = new MediaPlayer(media); 
	                    		mediaPlayer.setVolume(2);
	                    		mediaPlayer.setAutoPlay(true);  
	                     } 
					 }
					 if (y>=getAngle2() - 2*ddd-20 && y<=getAngle2() - 2*ddd-10  ) {// && stack4.getChildren().get(0).isVisible()) {
						// stack4.getChildren().get(0).setVisible(false);
						 player.setCollectedStars(player.getCollectedStars()+1);
						 l.setText(Integer.toString(player.getCollectedStars())); 
						 player.setScore(player.getScore()+1);
						 l2.setText(Integer.toString(player.getScore()));
						 if(playsounds == true) {
	                    		//String s = "scorepointsound.wav";
	                    		String s = "starsound.wav";
	                    		media = new Media(new File(s).toURI().toString()); 
	                    		mediaPlayer = new MediaPlayer(media); 
	                    		mediaPlayer.setVolume(5);
	                    		mediaPlayer.setAutoPlay(true);  
	                     }
					 }
					 //obstacle 1
					 if (y>=getAngle2() -20 && y<=getAngle2() -10) {
						
						 System.out.println("collup");
						 System.out.println(angle%360);
						
						 int col=-1;
						 if(angle%360 > 0 && angle%360 <90) {
							 System.out.println("red");
							 col =0; 
						 }
						 if(angle%360 > 90 && angle%360 <180) { 
							 System.out.println("blue");
							 col =1;
						 }
						 if(angle%360 > 180 && angle%360 <270) { 
							 System.out.println("yellow");
							 col =2;
						 }
						 if(angle%360 > 270 && angle%360 <360) { 
							 System.out.println("green");
							 col =3; 
						 }
						 if (col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 y = getAngle2() -25;
							 
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
							 
							 showResurrectmenu();
						 }
					 }
					 if (y>=getAngle2() +obstacle1.getWidth() -50 && y<=getAngle2() +obstacle1.getWidth()-40) {
						 
						 System.out.println("colld");
						 System.out.println(angle%360);
						 int col=-1;
						 if(angle%360 > 0 && angle%360 <90) {
							 System.out.println("yellow");
							 col =2; 
					     }
						 if(angle%360 > 90 && angle%360 <180) { 
							 System.out.println("green");
							 col =3;
						 }
						 if(angle%360 > 180 && angle%360 <270) { 
							 System.out.println("red");
							 col =0;
						 }
						 if(angle%360 > 270 && angle%360 <360) { 
							 System.out.println("blue");
							 col =1; 
						 }
						 
						 if (col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 y = getAngle2() +obstacle1.getWidth() -55;
							 
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
							 
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
							 y = angle2- 4*ddd -25;
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
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
							 y = angle2 +obstacle3.getWidth() - 4*ddd-55;
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
							 showResurrectmenu();
						 }
							
					 }
					 
					 //obstacle 4
					 if (y>=angle2- 5*ddd +40 && y<=angle2 - 5*ddd+50) {
							
						 System.out.println("collup");
						 System.out.println(angle%360);
						 int abc =(angle)%360;
						 int col=-1;
						 if(abc > 0 && abc <120)
							 {System.out.println("red");
							 col =0; }
						 if(abc > 120 && abc <240)
						 { System.out.println("blue");
						 col =1;}
						  if(abc > 240 && abc <360)
						 { System.out.println("green");
						 col =3; }
						 if (ball.getColour()!=3 && col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 y = angle2- 5*ddd -25;
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
							 showResurrectmenu();
						 }
					 }
					 if (y>=angle2 +obstacle4.getWidth() - 5*ddd-50 && y<=angle2 +obstacle4.getWidth()-40- 5*ddd) {
						 
						 System.out.println("colld");
						 System.out.println(angle%360);
						 int col=-1;
						 int abc =(angle-45)%360;
						 if(abc > 0 && abc <120)
						 { System.out.println("green");
						 col =3;}
						 if(abc > 120 && abc <240)
						 { System.out.println("red");
						 col =0;}
						 if(abc > 240 && abc <360)
						 { System.out.println("blue");
						 col =1; }
						 if (ball.getColour()!=2 && col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 y = angle2 +obstacle3.getWidth() - 5*ddd-55;
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
							 showResurrectmenu();
						 }
							
					 }
					 //obstacle 6
					 if (y>=angle2- 6*ddd -20 && y<=angle2 - 6*ddd-10) {
						
						 System.out.println("coll6");
						// System.out.println(xpos);
						
						 int col=-1;
						 if((xpos > -270 && xpos <-180)||(xpos > 90 && xpos <180)||(xpos > 360 && xpos <450)) {
							 System.out.println("red");
							 col =0; 
						 }
						 if((xpos > -450 && xpos <-360)||(xpos > -180 && xpos <-90)||(xpos > 180 && xpos <270)) { 
							 System.out.println("blue");
							 col =1;
						 }
						 if((xpos > -90 && xpos <0)||(xpos > 270 && xpos <360)) { 
							 System.out.println("yellow");
							 col =2;
						 }
						 if((xpos > -360 && xpos <-270)||(xpos > 0 && xpos <90)) { 
							 System.out.println("green");
							 col =3; 
						 }
						 if (col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 y = angle2- 6*ddd -25;
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
							 showResurrectmenu();
						 }
					 }
					 System.out.println("angle");
					 System.out.println(angle%360);
					//obstacle 2
					 if (y>=angle2+(+obstacle2.getWidth()/2) -3*ddd -50 && y<=angle2 +(+obstacle2.getWidth()/2)- 3*ddd-10) {
						 System.out.println("coll2");
						 System.out.println(angle%360);
						 int col=ball.getColour();
						 if((angle%360 > 0 && angle%360 <20)||(angle%360 > 340 && angle%360 <360)) {
							 System.out.println("blue");
							 col =1; 
						 }
						 if(angle%360 > 70 && angle%360 <110) { 
							 System.out.println("yellow");
							 col =2;
						 }
						 if(angle%360 > 160 && angle%360 <200) { 
							 System.out.println("green");
							 col =3;
						 }
						 if(angle%360 > 250 && angle%360 <290) { 
							 System.out.println("red");
							 col =0; 
						 }
						 if (col!=ball.getColour()) {
							 System.out.println("coll");
							 pause=!pause;
							 y = angle2- 3*ddd -25;
							 
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
							 
							 showResurrectmenu();
						 }
					 }
					 
				 	 if (y<350 && y>150  ) {
				 		 y=y+2*getGravity()+1;
				 		 ball.setY(y);
				 		System.out.println("y "+ y);
				 	 }
				 	 else if (y>=250) {
				 		angle2=(int) (getAngle2()-getGravity() -1);
				 		y=y+getGravity();
				 		ball.setY(y);
				 		System.out.println("up "+ y);
				 	 }
				 	 else if (y<250)	{
				 		angle2=(int) (getAngle2()-2*getGravity() );
				 		y=y+1;
				 		ball.setY(y);
				 		System.out.println("down "+ y);
				 	 }
				 	 
				 	 if(xpos > 450) {
				 		xshift *= -1;
	            	 }
	            	 if(xpos < -450)
	            	 {
	            		 xshift *= -1;
	            	 }
	            	 xpos += xshift;
	            	 System.out.println("xpos "+ xpos);
					 gc.drawImage(ball.get_ball(), x, y, 30, 30);
					 
				 }
	        }
    };

    private class buttonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        	var src = (Button) event.getSource();
        	if(src.getText().equals("Return to game")) {
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
        		pane.getChildren().clear();
        		stage.close();
        		//Platform.exit(); // or displayMainmenu
        		homepage.displayMainmenu(stage, pane, scene);
        	}
//        	else if(src.getText().equals("Back")) {
//        		stage.close();
//        		showSaveresmenu(stage);
//        	}
        	else if(src.getText().equals("Save life and resume game")) {
        		/*if(player.getCollectedStars()<=0) {
        			System.out.println("Not enough stars");
        		}
        		else {*/
        			//y=y+0;
        		ball.setY(ball.getY()+70);
        		player.resurrect();
        		l.setText(Integer.toString(player.getCollectedStars()));
        		canvas.requestFocus();
        		//pause=!(pause);
        		stage.close();
        		canvas.requestFocus();
        		canvas.setVisible(true);
        		try {
        		    Robot r = new Robot();
        		    //there are other methods such as positioning mouse and mouseclicks etc.
        		    r.keyPress(java.awt.event.KeyEvent.VK_UP);
        		    r.keyRelease(java.awt.event.KeyEvent.VK_UP);
        		 } catch (AWTException e) {
        		    //Teleport penguins  
        		 }
        		pause = !pause;
        		//}
        	}
        	else if(src.getText().equals("Restart game")) {
        		pane.getChildren().clear();
       		 	stage.close();
       		 	// canvas.requestFocus();
       		 	//pause=!(pause);
       		 	//stage = new Stage();
        		try {
					Homepage.startNewgame(stage);
					canvas.requestFocus();
	        		try {
	        		    Robot r = new Robot();
	        		    //there are other methods such as positioning mouse and mouseclicks etc.
	        		    r.keyPress(java.awt.event.KeyEvent.VK_UP);
	        		    r.keyRelease(java.awt.event.KeyEvent.VK_UP);
	        		 } catch (AWTException e) {
	        		    //Teleport penguins  
	        		 }
	        		pause = !pause;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
        	}
        	else if(src.getText().equals("Exit game")) {
        		pane.getChildren().clear();
        		stage.close();
        		//Platform.exit(); // or displayMainmenu
        		homepage.displayMainmenu(stage, pane, scene);
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
                    {
                    	gravity = -2;
//                    	if(playsounds == true) {
//                    		String s = "ballup.wav";
//                    		media = new Media(new File(s).toURI().toString()); 
//                    		mediaPlayer = new MediaPlayer(media); 
//                    		mediaPlayer.setVolume(2);
//                    		mediaPlayer.setAutoPlay(true);  
//                    	}
                    }
                    break;
                case P:
                   pause =!(pause);
            }
        }
    };
    
    private transient EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) { 
	       // pause=!(pause);	
	        canvas.requestFocus();
    		try {
    		    Robot r = new Robot();
    		    //there are other methods such as positioning mouse and mouseclicks etc.
    		    r.keyPress(java.awt.event.KeyEvent.VK_UP);
    		    r.keyRelease(java.awt.event.KeyEvent.VK_UP);
    		 } catch (AWTException e1) {
    		    //Teleport penguins  
    		 }
    		pause = !pause;
        } 
    };
    private transient EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) { 
	        stage = new Stage(); 
	        //showSaveresmenu(stage);
	        saveGameMenu(stage);
	        pause=!(pause);
        } 
    };
    
    public void saveGame() {
    	SaveObject gametosave = new SaveObject(this.ball, this, this.player, this.obstacle1, this.obstacle2, this.obstacle3, this.obstacle4, this.obstacle6, this.ccr, this.star1);
    	SaveGame.save(gametosave);
    }
    public void saveGameMenu(Stage theStage){
    	pane = new GridPane();
    	pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
    	scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
    	pane.setStyle("-fx-background-color: #202020");
	    pane.setId("pane");
//	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
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
		btn2 = new Button("Return to game");
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
//    private void showSaveresmenu(Stage theStage) {
//    	pane = new GridPane();
//    	pane.setAlignment(Pos.CENTER);
//	    pane.setHgap(10);
//	    pane.setVgap(10);
//	    pane.setPadding(new Insets(25, 25, 25, 25));
//	    
//    	scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
//    	
//    	pane.setStyle("-fx-background-color: #202020");
//	    pane.setId("pane");
////	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
//        
//		vbox = new VBox(5);
//        stage = theStage;
//        t = new Text();
//    	t = new Text (10, 20, "Color Switch\n");
//    	t.setFont(Font.font ("Montserrat", 20));
//    	t.setFill(Color.WHITE);
//    	btn1 = new Button("Save game");
//    	
//    	btn3 = new Button("Resume");
//    	bh = new buttonHandler();
//        btn1.setOnAction(bh);
//        btn3.setOnAction(bh);
//        vbox.getChildren().addAll(t, btn1, btn3);
//        pane.getChildren().addAll(vbox);
//        theStage.setTitle("Colour Switch");
//        theStage.setScene(scene);
//        theStage.show();
//	}
    public void showResurrectmenu(){
		stage = new Stage();
		pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    
	    scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
	    pane.setStyle("-fx-background-color: #202020");
	    pane.setId("pane");
//	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
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
		this.collectedstars -= 1;
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
	
	public  ImageView getImg() {
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
	private static int width = 60;

	public Colourchanger() throws FileNotFoundException{
		
		img.setFitWidth(width);
		img.setPreserveRatio(true);
		this.y = 0;
	}
	
	public ImageView getImg() {
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
	protected boolean hit;
	
	public Obstacle() throws FileNotFoundException {
		this.noofcolours = 4;
		this.hit = false;
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
	protected abstract void movement(float duration, int a, int b, GridPane pane);
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
	protected void movement(float duration, int angle2, int xpos, GridPane pane) {
	
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
		
		 img.setX(600-Main.screenWidth );
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
	protected void movement(float duration,int angle2, int xpos, GridPane pane) {
		  
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
	protected void movement(float duration,int angle2, int xpos, GridPane pane) {
		  
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
	protected void movement(float duration,int angle2, int xpos, GridPane pane) {
		  
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
	protected void movement(float duration,int angle2, int xpos, GridPane pane) {
		  
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
class Obstacle6 extends Obstacle {
	
	private static final long serialVersionUID = 320L;
	private static transient Image pic = new Image("file:images/obstacle6a.png");
	private static transient ImageView img = new ImageView(pic);
	private static int width = 1000;
	private transient Timeline timeline;
	private transient Duration animateDuration;
	private transient Duration xanimateDuration;
	private transient Translate translate;
	
	public Obstacle6() throws FileNotFoundException{
		
		img.setX(50);
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
	protected void movement(float duration,int angle2, int xpos, GridPane pane) {
		  
	    timeline = new Timeline();
	    animateDuration = Duration.millis(3);
	    xanimateDuration = Duration.millis(1);
    	
	    //rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	    // obstacle1.img.getTransforms().add(rotate);
	    translate = new Translate();
	    
	    int ddd= 500;
	    float angle =duration;
	   
	    timeline = new Timeline( 
	    		new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().translateXProperty(), xpos)),
	            new KeyFrame(xanimateDuration, new KeyValue(this.getImg().translateXProperty(), xpos+2)),
	            new KeyFrame(Duration.ZERO, new KeyValue(this.getImg().translateYProperty(), angle2)),
	            new KeyFrame(animateDuration, new KeyValue(this.getImg().translateYProperty(), angle2+2)) 
	            );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        
        TranslateTransition transition = new TranslateTransition();
	    transition.setByX(500);
	    transition.setByY(0);
	    transition.setAutoReverse(true);
	    transition.setCycleCount(TranslateTransition.INDEFINITE);
	    transition.setInterpolator(Interpolator.LINEAR);
	    transition.play();
        
        timeline.play();
	}
}
