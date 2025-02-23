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
import javax.swing.JOptionPane;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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
	Homepage homepage;
	
	
	public static void main(String[] args) {
		
		showMainmenu( args);
	}
	private static void showMainmenu(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage theStage) throws Exception {
		stage = theStage;
		homepage = new Homepage();
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
	public static void deleteSavedGames() {
		String path = new File("").getAbsolutePath();
		File directoryPath = new File(path);
		String contents[] = directoryPath.list();
	    for(int i=0; i<contents.length; i++) {
	    	if(contents[i].length()>4 && contents[i].substring(contents[i].length() - 4).equals(".txt")) {
	        	File toDelete = new File(contents[i]);
	        	if(toDelete.canWrite()) {
	    			toDelete.delete();
	    		}
	    	}
	    }
	}
	
}

class SaveObject implements Serializable{
	
	private static final long serialVersionUID = 100L;
	
	private Game game;
	private Player player;
	private Ball ball;
	private Star star_list[];
	private Colourchanger cc[];
	private Obstacle1 ob1;
	private Obstacle2 ob2;
	private Obstacle3 ob3;
	private Obstacle4 ob4;

	private Obstacle6 ob6;
	
	public SaveObject(Ball ball, Game game, Player player, Obstacle1 obstacle1, Obstacle2 obstacle2, Obstacle3 obstacle3, Obstacle4 obstacle4, Obstacle6 obstacle6, Colourchanger[] colourchanger, Star[] star1) {
		this.ball = ball;
		this.game = game;
		this.player = player;
		this.ob1 = obstacle1;
		this.ob2 = obstacle2;
		this.ob3 = obstacle3;
		this.ob4 = obstacle4;
		this.ob6 = obstacle6;
		this.cc = colourchanger;
		this.star_list = star1;
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
		return star_list;
	}
	public Game getGame() {
		return game;
	}
	
}

class Homepage {
	
	static GridPane pane = new GridPane();
    static Scene scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
    static Stage stage;
    static Stage stage2;
	private static Game game;
	private static Game game2;
	private int bestscore;
	private ArrayList<Button> buttons;
	
	public Homepage() {
		game = new Game();
		game2 = new Game();
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
		Shared share= new Shared();
		game.share=share;
		game.start(stage);
	}
	public static void startNewgame2(Stage stage) throws FileNotFoundException{	
		Shared share= new Shared();
		game.share=share;
		game2.share=share;
		game.no=1;
		game2.no=2;
		game.start(stage);
		stage2 = new Stage();
		game2.start(stage2);
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
	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
		VBox vbox = new VBox(5);
		vbox.setSpacing(10);
		Text t = new Text();
		t = new Text (10, 20, "Welcome to Color Switch!\n");
		t.setFont(Font.font ("Montserrat", 25));
		t.setFill(Color.WHITE);

		Button btn1 = new Button("Start new game");
		Button btn2 = new Button("Resume a saved game");
		Button button3 = new Button("Enter multiplayer mode");
		Button button2 = new Button("View Leaderboard");
		Button button = new Button("Settings");
		Button btn3 = new Button("Exit");

		buttonHandler bh = new buttonHandler();

		btn1.setOnAction(bh);
		btn2.setOnAction(bh);
		btn3.setOnAction(bh);
		button.setOnAction(bh);
		button2.setOnAction(bh);
		button3.setOnAction(bh);

		vbox.getChildren().addAll(t, btn1, btn2, button3, button2, button, btn3);
		pane.getChildren().addAll(vbox);
		theStage.setTitle("Colour Switch");
		theStage.setScene(scene);
		theStage.show();
	}
	
	public void showSavedgames(Stage theStage, GridPane pane, Scene scene){
		stage = theStage;
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
	    pane.setStyle("-fx-background-color: #202020");
	    pane.setId("pane");
	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
		VBox vbox = new VBox(5);
		buttons = new ArrayList<Button>();
		buttonHandler bh = new buttonHandler();
		gamebuttonsHandler gh = new gamebuttonsHandler();
		Text t = new Text();
		t = new Text (10, 20, "Saved Games\n");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);
		vbox.getChildren().add(t);
		
		Button btn5 = new Button("Clear saved games");
		btn5.setOnAction(bh);
		vbox.getChildren().add(btn5);
		Button btn4 = new Button("Back");
		btn4.setOnAction(bh);
		vbox.getChildren().add(btn4);
		
		Text t2 = new Text();
		t2 = new Text (10, 20, "Choose a game to start playing:\n");
		t2.setFont(Font.font ("Montserrat", 15));
		t2.setFill(Color.WHITE);
		vbox.getChildren().add(t2);
		
		String path = new File("").getAbsolutePath();
		File directoryPath = new File(path);
		String contents[] = directoryPath.list();
	    for(int i=0; i<contents.length; i++) {
	    	if(contents[i].length()>4 && contents[i].substring(contents[i].length() - 4).equals(".txt")) {
	    		buttons.add(new Button(contents[i].substring(0, contents[i].length() - 4)));
	    	}
	    }
	    for (Button b : buttons) {
	    	b.setOnAction(gh);
			vbox.getChildren().add(b);
	    }

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
	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
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
	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
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
		
	    String path = new File("").getAbsolutePath();
		File directoryPath = new File(path);
		String contents[] = directoryPath.list();
		int s;
		ObservableList<Row> data = FXCollections.observableArrayList();
	    for(int i=0; i<contents.length; i++) {
	    	if(contents[i].length()>4) {
	    	String txt = contents[i].substring(contents[i].length() - 4);
        	String filename = contents[i].substring(0, contents[i].length() - 4);
	    	if(txt.equals(".txt")) {
        		SaveObject loadedgame = SaveGame.load(contents[i]);
        		s = loadedgame.getPlayer().getScore();
        		data.add(new Row(filename, s));}
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
        		 
        		try {
					startNewgame(stage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
        		
        	}
        	else if(src.getText().equals("Resume a saved game")) {
        		pane.getChildren().clear();
        		stage.close();
        		showSavedgames(stage, pane, scene);
        	}
        	else if(src.getText().equals("Back")) {
        		pane.getChildren().clear();
        		stage.close();
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
        	else if(src.getText().equals("Clear saved games")) {
        		SaveGame.deleteSavedGames();
        		pane.getChildren().clear();
        		stage.close();
        		showSavedgames(stage, pane, scene);
        	}
        	else if(src.getText().equals("Enter multiplayer mode")) {
        		pane.getChildren().clear();
       		 	stage.close();
       		 try {
					startNewgame2(stage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
        	}
        }
	}
}
class Shared{
	public int b=0;
	//scores
	public int s1=0;
	public int s2=0;
	public static int winner=0;
}
class Game extends Application implements Serializable{
	
	private static final long serialVersionUID = 120L;
	public transient Shared share;
	public transient int no=0;
	//n=1 up
	//n=2=w
	private String name;
	private int level;
	private int distance;
	private static boolean playmusic = false;
	private static boolean playsounds = false;
	private static Homepage homepage = new Homepage();
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
    private Obstacle6 obstacle6;
    private Star[] star1;
    private Colourchanger[] colourchanger;
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
    private transient Stage stage2;
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

    transient Label l;
    transient Label l2;
    transient Text t;
    transient Text t2;
    transient TextField tf;
    transient Text ptext;
    transient Duration rotateDuration;
    transient Rotate rotate;
    transient Random rand;
    
    private static MediaPlayer mediaPlayer;
    private static Media media;
    public Game()  {
    }
    private void pause() {
    	pause=false;
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
	public int getXpos() {
		return xpos;
	}
	public int getXshift() {
		return xshift;
	}
	@Override
    public void start(Stage theStage) throws FileNotFoundException
    { 
		stage2 = theStage;
		if(this.no == 0) {
	        theStage.setTitle( "Colour Switch" );
		}
		else if(this.no == 1) {
	        theStage.setTitle( "Game Screen for Player 1: Use UP key" );
		}
		else if(this.no == 2) {
	        theStage.setTitle( "Game Screen for Player 2: Use w key" );
		}
    	
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
        angle=0;
        angle2=0;
        xpos = -40;
        xshift = 3;
        //Group 
        root = new Group();
        ball = new Ball();
        obstacle1 = new Obstacle1();
        obstacle2 = new Obstacle2();
        obstacle3 = new Obstacle3();
        obstacle4 = new Obstacle4();
        obstacle6 = new Obstacle6();
    	star1= new Star[5];
    	colourchanger=new Colourchanger[5];
    	for(int i = 0; i<5; i++) {
        	colourchanger[i] = new Colourchanger();
        	star1[i] = new Star();
        }
        int screenw = Main.screenWidth;
        if(no != 0) {
        	screenw = Main.screenWidth/2;
        	ball.setX(screenw/2 - 15);
        	obstacle1.getImg().setX(600-Main.screenWidth);
        	obstacle2.getImg().setX(600-Main.screenWidth);
        	obstacle3.getImg().setX(600-Main.screenWidth);
        	obstacle4.getImg().setX(600-Main.screenWidth);
        	obstacle6.getImg().setX(50);
        }
        theScene = new Scene( root , screenw, Main.screenHeight );
        theStage.setScene( theScene );
        canvas = new Canvas( screenw, Main.screenHeight );
        stack1.setStyle("-fx-background-color: #202020");
        stack1.setId("panegame");
        stack1.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stack1.getChildren().add(canvas);
        root.getChildren().add( stack1 );
        player = new Player();
        
        if(no == 0) {
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
			t2.setFont(Font.font ("Montserrat", 15));
			t2.setFill(Color.WHITE);
			root.getChildren().addAll(t, t2);
        
	        button2 = new Button("Pause");
	        button2.setLayoutX(1000);
	        button2.setLayoutY(250);
	        root.getChildren().add( button2 ); 
	        button2.setOnAction(event);
	        btn = new Button();
	        btn.setText("Save");
	        btn.setLayoutX(1003);
	        btn.setLayoutY(300);
	        root.getChildren().add(btn);
	        btn.setOnAction(event2);
		}
        gc = canvas.getGraphicsContext2D();
        final long startNanoTime = System.nanoTime();
        
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(keyPressed);
        canvas.setOnKeyReleased(keyReleased);
        
        stack.getChildren().addAll(obstacle1.getImg(), obstacle3.getImg(), obstacle4.getImg(), obstacle6.getImg() );
        stack.setLayoutX(screenw/2 - Obstacle1.getWidth()/2 );
        stack.setLayoutY(0);
        root.getChildren().add(stack);
        
        stack2.getChildren().addAll( obstacle2.getImg());
        stack2.setLayoutX(screenw/2);
        stack2.setLayoutY(0);
        root.getChildren().add(stack2);

        stack5.getChildren().addAll( obstacle6.getImg());
        stack5.setLayoutX(100);
        stack5.setLayoutY(0);
        root.getChildren().add(stack5);
        
        colourchanger[1]=new Colourchanger();
        stack3.getChildren().addAll( colourchanger[1].getImg());
        stack3.setLayoutX(screenw/2 - Colourchanger.getWidth()/2);
        stack3.setLayoutY(colourchanger[1].getY());
        root.getChildren().add(stack3);
        
        star1[1]=new Star();
        stack4.getChildren().addAll( star1[1].getImg());
        stack4.setLayoutX(screenw/2 - Star.getWidth()/2);
        stack4.setLayoutY(star1[1].getY());
        root.getChildren().add(stack4);
        
        theStage.show();
        timer.start(); 
    }
	public void resume(Stage theStage, SaveObject data) throws FileNotFoundException
    {stage2=theStage;
    	theStage.setTitle( "Colour Switch" );
    	stack = new StackPane();
        stack1 = new StackPane();
        stack2 = new StackPane();
        stack3 = new StackPane();
        stack4 = new StackPane();
        stack5 = new StackPane();
    	player = data.getPlayer();
    	share=new Shared();
        ball = data.getBall();
        obstacle1 = data.getOb1();
        obstacle2 = data.getOb2();
        obstacle3 = data.getOb3();
        obstacle4 = data.getOb4();
        obstacle6 = data.getOb6();
        angle = data.getGame().getAngle();
        angle2 = data.getGame().getAngle2();
        xpos = data.getGame().getXpos();
        xshift = data.getGame().getXshift();
        obstacle1=new Obstacle1();
        obstacle2=new Obstacle2();
        obstacle3=new Obstacle3();
        obstacle4=new Obstacle4();
        obstacle6=new Obstacle6();
        
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
        
        colourchanger = new Colourchanger[5];
        star1 = new Star[5];
        for(int i = 0; i<5; i++) {
        	colourchanger[i] = new Colourchanger();
        	star1[i] = new Star();
        }
        //Group 
        
        root = new Group();
        theScene = new Scene( root , Main.screenWidth, Main.screenHeight );
        theStage.setScene( theScene );
        canvas = new Canvas( Main.screenWidth, Main.screenHeight );
        stack1.setStyle("-fx-background-color: #202020");
        stack1.setId("panegame");
	    stack1.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stack1.getChildren().add(canvas);
        root.getChildren().add( stack1 );
        
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
        final long startNanoTime = System.nanoTime();
        
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(keyPressed);
        canvas.setOnKeyReleased(keyReleased);
        button2.setOnAction(event);
        btn.setOnAction(event2);
        
        stack.getChildren().addAll(obstacle1.getImg(), obstacle3.getImg(), obstacle4.getImg(), obstacle6.getImg() );
        stack.setLayoutX(Main.screenWidth/2 - Obstacle1.getWidth()/2 );
        stack.setLayoutY(0);
        root.getChildren().add(stack);
        
        stack2.getChildren().addAll( obstacle2.getImg());
        stack2.setLayoutX(Main.screenWidth/2 );
        stack2.setLayoutY(0);
        root.getChildren().add(stack2);

        stack5.getChildren().addAll( obstacle6.getImg());
        stack5.setLayoutX(100);
        stack5.setLayoutY(0);
        root.getChildren().add(stack5);
        
        colourchanger[1]=new Colourchanger();
        stack3.getChildren().addAll( colourchanger[1].getImg());
        stack3.setLayoutX(Main.screenWidth/2 - Colourchanger.getWidth()/2);
        stack3.setLayoutY(colourchanger[1].getY());
        root.getChildren().add(stack3);
        
        star1[1]=new Star();
        stack4.getChildren().addAll( star1[1].getImg());
        stack4.setLayoutX(Main.screenWidth/2 - Star.getWidth()/2);
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
	            	if (share.winner!=0) {
	            		multiplayerEnd();
	            		pause=true;
	            		
	            		try {
		        		    Robot r = new Robot();
		        		    r.keyPress(java.awt.event.KeyEvent.VK_UP);
		        		    r.keyRelease(java.awt.event.KeyEvent.VK_UP);
		        		 } catch (AWTException e) {
		        		 	}
	            		//return;
	            	}
	            	if(angle2>4700)
	            	{
	            		angle2=-200;
	            		stack4.getChildren().get(0).setVisible(true);
	            	}
	            	if(angle2<-200)
	            	{
	            		angle2=4700;
	            	}
	            	int kk;
	            	int cc;
	            	if (angle2>2000 && angle2<3000) {
	            		stack4.getChildren().get(0).setVisible(true);}
	            	if (angle2>2000) {
	            		kk=7;
	            		cc=8;
	            	}
	            	else {
	            		kk=1;
	            		cc=2;
	            	}
	            	
	            	rotateDuration = Duration.millis(3);
	        	    rotate = new Rotate(0, 100, 100, 0, Rotate.Y_AXIS);
	        	    
	        	    int ddd= 500;
	        	    long t2 = System.nanoTime() - startNanoTime;
	        	    timeline = new Timeline( 
	        	    	       	            
	        	            new KeyFrame(Duration.ZERO, new KeyValue(colourchanger[1].getImg().translateYProperty(), getAngle2() - ddd*kk)),
	        	            new KeyFrame(rotateDuration, new KeyValue(colourchanger[1].getImg().translateYProperty(), getAngle2()+2 - ddd*kk)),
	        	         
	        	            new KeyFrame(Duration.ZERO, new KeyValue(star1[1].getImg().translateYProperty(), getAngle2() - cc*ddd)),
	        	            new KeyFrame(rotateDuration, new KeyValue(star1[1].getImg().translateYProperty(), getAngle2()+2 - cc*ddd))
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
	                obstacle1.setPassposition(ball.getColour());
	                obstacle2.setPassposition(ball.getColour());
	                obstacle3.setPassposition(ball.getColour());
	                obstacle4.setPassposition(ball.getColour());
	                obstacle6.setPassposition(ball.getColour());
		            double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
		            double x = ball.getX();
		            rand = new Random(); 
					 if (y>=angle2 - ddd-20 && y<=angle2 - ddd-10) {
						 Colourchanger.changeColour(ball);
						 
						 Colourchanger.setColours(ball.getColour());
						 if(playsounds == true) {
	                    		String s = "colourchangesound.wav";
	                    		media = new Media(new File(s).toURI().toString()); 
	                    		mediaPlayer = new MediaPlayer(media); 
	                    		mediaPlayer.setVolume(2);
	                    		mediaPlayer.setAutoPlay(true);  
	                     } 
					 }
					 if (y>=angle2 - 7*ddd-20 && y<=angle2 - 7*ddd-10) {
						 Colourchanger.changeColour(ball);
						 Colourchanger.setColours(ball.getColour());
						 if(playsounds == true) {
	                    		String s = "colourchangesound.wav";
	                    		media = new Media(new File(s).toURI().toString()); 
	                    		mediaPlayer = new MediaPlayer(media); 
	                    		mediaPlayer.setVolume(2);
	                    		mediaPlayer.setAutoPlay(true);  
	                     } 
					 }
					 if (y>=getAngle2() - 2*ddd-20 && y<=getAngle2() - 2*ddd-10 && stack4.getChildren().get(0).isVisible()) {
						 stack4.getChildren().get(0).setVisible(false);
						 Star.addLife(player);
						 if(no == 0) {
							 l.setText(Integer.toString(player.getCollectedStars())); 
						 }
						 player.setScore(player.getScore()+1);
						 if(no == 0) {
							 l2.setText(Integer.toString(player.getScore()));
						 }
						 if(playsounds == true) {
	                    		String s = "starsound.wav";
	                    		media = new Media(new File(s).toURI().toString()); 
	                    		mediaPlayer = new MediaPlayer(media); 
	                    		mediaPlayer.setVolume(5);
	                    		mediaPlayer.setAutoPlay(true);  
	                     }
					 }
					 if (y>=getAngle2() - 8*ddd-20 && y<=getAngle2() - 8*ddd-10 && stack4.getChildren().get(0).isVisible()) {
						 stack4.getChildren().get(0).setVisible(false);
						 Star.addLife(player);
						 if(no == 0) {
							 l.setText(Integer.toString(player.getCollectedStars())); 
						 }
						 player.setScore(player.getScore()+1);
						 if(no == 0) {
							 l2.setText(Integer.toString(player.getScore()));
						 }
						 if(playsounds == true) {
	                    		String s = "starsound.wav";
	                    		media = new Media(new File(s).toURI().toString()); 
	                    		mediaPlayer = new MediaPlayer(media); 
	                    		mediaPlayer.setVolume(5);
	                    		mediaPlayer.setAutoPlay(true);  
	                     }
					 }
					 if (obstacle1.obstacleHit( y)) {
						 pause=!pause;
						 if(no==1) {
							 share.winner=2;
						 }
						 else if(no==2) {
							 share.winner=1;
						 }
						 y = getAngle2() -25;
						 
						 if(playsounds == true) {
	                    		String s = "hitobstaclesound.wav";
	                    		media = new Media(new File(s).toURI().toString()); 
	                    		mediaPlayer = new MediaPlayer(media); 
	                    		mediaPlayer.setVolume(5);
	                    		mediaPlayer.setAutoPlay(true);  
	                     }
						 if(no==0) {
							 showResurrectmenu(); 
						 }
						 
					 }
					 if (obstacle3.obstacleHit( y)) {
						 pause=!pause;
						 if(no==1) {
							 share.winner=2;
						 }
						 else if(no==2) {
							 share.winner=1;
						 }
						 y = angle2 +obstacle3.getWidth() - 4*ddd-55;
						 if(playsounds == true) {
					    		String s = "hitobstaclesound.wav";
					    		media = new Media(new File(s).toURI().toString()); 
					    		mediaPlayer = new MediaPlayer(media); 
					    		mediaPlayer.setVolume(5);
					    		mediaPlayer.setAutoPlay(true);  
					     }
						 if(no==0) {
							 showResurrectmenu(); 
						 }
					 }
					 if (obstacle4.obstacleHit(y)) {
							 pause=!pause;
							 if(no==1) {
								 share.winner=2;
							 }
							 else if(no==2) {
								 share.winner=1;
							 }
							 y = angle2- 5*ddd+obstacle4.getWidth() -55;
							 if(playsounds == true) {
						   		String s = "hitobstaclesound.wav";
						   		media = new Media(new File(s).toURI().toString()); 
						   		mediaPlayer = new MediaPlayer(media); 
						   		mediaPlayer.setVolume(5);
						   		mediaPlayer.setAutoPlay(true);  
						    }
							if(no==0) {
								showResurrectmenu(); 
							}
						}
					 
					 if (obstacle2.obstacleHit( y)) {
							 pause=!pause;
							 y = angle2- 3*ddd -25;
							 if(no==1) {
								 share.winner=2;
							 }
							 else if(no==2) {
								 share.winner=1;
							 }
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
							 if(no==0) {
								 showResurrectmenu(); 
							 }
					 }


					 if (obstacle6.obstacleHit(y)) {
							 pause=!pause;
							 if(no==1) {
								 share.winner=2;
							 }
							 else if(no==2) {
								 share.winner=1;
							 }
							 y = angle2- 6*ddd -25;
							 if(playsounds == true) {
		                    		String s = "hitobstaclesound.wav";
		                    		media = new Media(new File(s).toURI().toString()); 
		                    		mediaPlayer = new MediaPlayer(media); 
		                    		mediaPlayer.setVolume(5);
		                    		mediaPlayer.setAutoPlay(true);  
		                     }
							 if(no==0) {
								 showResurrectmenu(); 
							 }
					 }
					 
					if (no==2) {
						gravity=share.b;
					}
				 	 if (y<350 && y>150  ) {
				 		 y=y+2*getGravity()+1;
				 		 ball.setY(y);
				 	 }
				 	 else if (y>=250) {
				 		angle2=(int) (getAngle2()-getGravity() -1);
				 		y=y+getGravity();
				 		ball.setY(y);
				 	 }
				 	 else if (y<250)	{
				 		angle2=(int) (getAngle2()-2*getGravity() );
				 		y=y+1;
				 		ball.setY(y);
				 	 }
				 	 
				 	 if(xpos > 450) {
				 		xshift *= -1;
	            	 }
	            	 if(xpos < -450)
	            	 {
	            		 xshift *= -1;
	            	 }
	            	 xpos += xshift;
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
        		stage2.close();
        		stage.close();
        		homepage.displayMainmenu(stage, pane, scene);
        	}
        	else if(src.getText().equals("Save life and resume game")) {
        		if(player.getCollectedStars()<=0) {
        			JOptionPane.showMessageDialog(null, "Not enough stars. Game over!");
        			pane.getChildren().clear();
        			stage2.close();
        			stage.close();
        			homepage.displayMainmenu(stage, pane, scene);
        		}
        		else {
        			y=y+0;
	        		ball.setY(ball.getY()+70);
	        		player.resurrect();
	        		l.setText(Integer.toString(player.getCollectedStars()));
	        		canvas.requestFocus();
	        		stage.close();
	        		canvas.requestFocus();
	        		canvas.setVisible(true);
	        		try {
	        		    Robot r = new Robot();
	        		    r.keyPress(java.awt.event.KeyEvent.VK_UP);
	        		    r.keyRelease(java.awt.event.KeyEvent.VK_UP);
	        		 } catch (AWTException e) {
	        		 	}
	        		pause = !pause;
	        	}
        	}
        	else if(src.getText().equals("Restart game")) {
        		pane.getChildren().clear();
       		 	stage.close();
       		 	stage2.close();
        		try {
					Homepage.startNewgame(stage);
					canvas.requestFocus();
	        		try {
	        		    Robot r = new Robot();
	        		    r.keyPress(java.awt.event.KeyEvent.VK_UP);
	        		    r.keyRelease(java.awt.event.KeyEvent.VK_UP);
	        		 } catch (AWTException e) {
	        		 }
	        		pause = !pause;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
        	}
        	else if(src.getText().equals("Exit game")) {
        		pane.getChildren().clear();
        		stage.close();
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
                case W:
                	share.b =0;
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
                    	if (share.winner!=0) {
                    		stage2.close();
                    		
                    	}
                    }
                    break;
                case W:
                	share.b =-2;
                	break;
                case P:
                   pause =!(pause);
                
            }
        }
    };
    
    private transient EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) { 	
	        canvas.requestFocus();
    		try {
    		    Robot r = new Robot();
    		    r.keyPress(java.awt.event.KeyEvent.VK_UP);
    		    r.keyRelease(java.awt.event.KeyEvent.VK_UP);
    		 } catch (AWTException e1) {
    		 }
    		pause = !pause;
        } 
    };
    private transient EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
        public void handle(ActionEvent e) { 
	        stage = new Stage(); 
	        saveGameMenu(stage);
	        pause=!(pause);
        } 
    };
    
    public void saveGame() {
    	SaveObject gametosave = new SaveObject(this.ball, this, this.player, this.obstacle1, this.obstacle2, this.obstacle3, this.obstacle4, this.obstacle6, this.colourchanger, this.star1);
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
	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
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
	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
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
    public void multiplayerEnd(){
		stage = new Stage();
		pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 250, 25, 25));
	    
	    scene = new Scene(pane, Main.screenWidth, Main.screenHeight);
	    pane.setStyle("-fx-background-color: #202020");
	    pane.setId("multiend");
	    pane.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
		vbox = new VBox(5);
		t = new Text();
		t = new Text (10, 20, "Player "+ Shared.winner + " has won the game!");
		t.setFont(Font.font ("Montserrat", 20));
		t.setFill(Color.WHITE);
		btn1 = new Button("Exit game");
		btn1.setLayoutX(980);
		btn1.setLayoutY(500);
		bh = new buttonHandler();
		btn1.setOnAction(bh);

		vbox.getChildren().addAll(t, btn1);
		vbox.setAlignment(Pos.CENTER_LEFT);
		pane.getChildren().addAll(vbox);
		pane.setAlignment(Pos.BOTTOM_CENTER);
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
    
    
    public Ball() throws FileNotFoundException {
    	
        this.y = 299;
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
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public void setColour(int colour) {
		this.colour=colour;
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
	private  transient Image pic = new Image("file:images/star.png");
	private  transient ImageView img = new ImageView(pic);
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
		this.y=y;
	}
	public static void addLife(Player player) {
		player.setCollectedStars(player.getCollectedStars()+1);
	}
}
 class Colourchanger implements Serializable{
	
	private static final long serialVersionUID = 200L;
	private transient static int[] colours;
	private int y;
	private Ball ball;
	private  transient   Image pic = new Image("file:images/colourchanger.png");
	private  transient ImageView img = new ImageView(pic);
	private static int width = 60;

	public Colourchanger() throws FileNotFoundException{
		
		img.setFitWidth(width);
		img.setPreserveRatio(true);
		this.y = 0;
		colours=new int[5];
	}
	
	public  ImageView getImg() {
		return img;
	}
	public static int getWidth() {
		return width;
	}
	public int[] getColours() {
		return this.colours;
	}
	public static void setColours(int colour) {
		for(int i=0; i<5;i++) {
			colours[i]=colour;
		}
	}
	public int getY() {
		return this.y;
	}
	public void setY(int y) {
		this.y=y;
	}
	private transient static Random rand;
	public static void changeColour(Ball ball) {
		
		 rand = new Random(); 
		int colour = rand.nextInt(4); 
		ball.setColour(colour);
	}
}
abstract class Obstacle implements Serializable{

	private static final long serialVersionUID = 1000L;
	protected int noofcolours;
	protected int passposition;
	protected boolean hit;
	protected int y;
	protected int angle;
	
	public Obstacle() throws FileNotFoundException {
		setNoofcolours(4);
		this.hit = false;
	}
	public int getNoofcolours() {
		return noofcolours;
	}
	public void setNoofcolours(int colours) {
		this.noofcolours = colours;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return this.y ;
	}
	public int getPassposition() {
		return passposition;
	}
	public void setPassposition(int pass) {
		this.passposition=pass;
	}	
	protected boolean obstacleHit(int y2) {
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
	private   transient Image pic = new Image("file:images/obstacle1.png");
	private   transient  ImageView img = new ImageView(pic);
	private static int width = 250;
	
	public Obstacle1 () throws FileNotFoundException{
		
		img.setFitWidth(width);
		img.setPreserveRatio(true);
		img.setX(600-Main.screenWidth);
        img.setY(300-Main.screenHeight);
	}
	
	public  ImageView getImg() {
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
	    setY(angle2);
	    int ddd= 500;
	    this.angle =(int) duration;
	    
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
	@Override
	protected boolean obstacleHit(int y) {
		if (y>=getY() -20 && y<=getY() -10) {
			
			 int col=-1;
			 if(angle%360 > 0 && angle%360 <90) {
				 col =0; 
			 }
			 if(angle%360 > 90 && angle%360 <180) { 
				 col =1;
			 }
			 if(angle%360 > 180 && angle%360 <270) { 
				 col =2;
			 }
			 if(angle%360 > 270 && angle%360 <360) { 
				 col =3; 
			 }
			 if (col!=this.getPassposition()) {
				return true;
			 }
		 }
		 if (y>=getY() +getWidth() -50 && y<=getY() +getWidth()-40) {
			 
			 int col=-1;
			 if(angle%360 > 0 && angle%360 <90) {
				 col =2; 
		     }
			 if(angle%360 > 90 && angle%360 <180) { 
				 col =3;
			 }
			 if(angle%360 > 180 && angle%360 <270) { 
				 col =0;
			 }
			 if(angle%360 > 270 && angle%360 <360) { 
				 col =1; 
			 }
			 if (col!=this.getPassposition()) {
			return true;
			 }
		 }
		 return false;
	}
}
class Obstacle2 extends Obstacle {
	
	private static final long serialVersionUID = 240L;
	private  transient Image pic = new Image("file:images/obstacle2.png");
	private  transient ImageView img = new ImageView(pic);
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

	public  ImageView getImg() {
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
	    
	    int ddd= 500;
	    this.angle =(int) duration;
	    setY(angle2);
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
	@Override
	protected boolean obstacleHit(int y) {
		 if (y>=getY()+(getWidth()/2) -50 && y<=getY() +(getWidth()/2)-10) {
			 int col=this.getPassposition();
			 if((angle%360 > 0 && angle%360 <20)||(angle%360 > 340 && angle%360 <360)) {
				 col =1; 
			 }
			 if(angle%360 > 70 && angle%360 <110) { 
				 col =2;
			 }
			 if(angle%360 > 160 && angle%360 <200) { 
				 col =3;
			 }
			 if(angle%360 > 250 && angle%360 <290) { 
				 col =0; 
			 }
			 if (col!=this.getPassposition()) {
				 return true;
			 }
		 }
	return false;	
	}
}
class Obstacle3 extends Obstacle {
	
	private static final long serialVersionUID = 260L;
	private  transient Image pic = new Image("file:images/obstacle3.png");
	private  transient ImageView img = new ImageView(pic);
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

	public  ImageView getImg() {
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
	    
	    int ddd= 500;
	    this.angle =(int) duration;
	    setY(angle2);
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
	@Override
	protected boolean obstacleHit(int y) {
		int ddd=500;
		if (y>=getY() -20 && y<=getY()-10) {
			
			 int abc =(angle-45)%360;
			 int col=-1;
			 if(abc > 0 && abc <90) {
				 col =0; 
			 }
			 if(abc > 90 && abc <180) {
				 col =1;
			 }
			 if(abc > 180 && abc <270) { 
				 col =2;
			 }
			 if(abc > 270 && abc <360) { 
				 col =3; 
			 }
			 if (col!=this.getPassposition()) {
				return true;
			 }
		 }
		 if (y>=getY() +getWidth() -50 && y<=getY() +getWidth()-40) {
			 int col=-1;
			 int abc =(angle-45)%360;
			 if(abc > 0 &&abc <90) {
				 col =2; 
			 }
			 if(abc > 90 && abc <180) { 
				 col =3;
			 }
			 if(abc > 180 && abc <270) {
				 col =0;
			 }
			 if(abc > 270 && abc <360) { 
				 col =1; 
			 }
			 if (col!=this.getPassposition()) {
				 return true;
			 }
				
		 }
		 return false;
	}
}
class Obstacle4 extends Obstacle {
	
	private static final long serialVersionUID = 280L;
	private  transient Image pic = new Image("file:images/obstacle4.png");
	private  transient ImageView img = new ImageView(pic);
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

	public  ImageView getImg() {
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
	    
	    int ddd= 500;
	    this.angle =(int) duration;
	    setY(angle2);
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
	@Override
	protected boolean obstacleHit(int y) {

		 if (y>=this.getY() +40 && y<=this.getY()+50) {
				
			 int abc =(angle)%360;
			 int col=-1;
			 if(abc > 0 && abc <120) {
				 col =0; 
			 }
			 if(abc > 120 && abc <240) { 
				 col =1;
			 }
			 if(abc > 240 && abc <360) {
				 col =3; 
			 }
			  if (this.getPassposition()!=2 && col!=this.getPassposition()) {
				return true;
			 }
		 }
		 if (y>=this.getY() +getWidth()-50 && y<=this.getY() +getWidth()-40) {
			 int col=-1;
			 int abc =(angle-45)%360;
			 if(abc > 0 && abc <120) {
				 col =3;
			 }
			 if(abc > 120 && abc <240) { 
				 col =0;
			 }
			 if(abc > 240 && abc <360) { 
				 col =1; 
			 }
			 if (this.getPassposition()!=2 && col!=this.getPassposition()) {
				 return true;
			 }
		
	}
		 return false;
}
}
class Obstacle6 extends Obstacle {
	
	private static final long serialVersionUID = 320L;
	private  transient Image pic = new Image("file:images/obstacle6a.png");
	private  transient ImageView img = new ImageView(pic);
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

	public  ImageView getImg() {
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
    	
	    translate = new Translate();
	    
	    int ddd= 500;
	    this.angle =xpos;
	    setY(angle2);
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
	@Override
	protected boolean obstacleHit(int y) {
		int xpos=this.angle;
		//obstacle 6
		 if (y>=this.getY() -20 && y<=this.getY()-10) {
			
			 int col=-1;
			 if((xpos > -270 && xpos <-180)||(xpos > 90 && xpos <180)||(xpos > 360 && xpos <450)) {
				 col =0; 
			 }
			 if((xpos > -450 && xpos <-360)||(xpos > -180 && xpos <-90)||(xpos > 180 && xpos <270)) { 
				 col =1;
			 }
			 if((xpos > -90 && xpos <0)||(xpos > 270 && xpos <360)) { 
				 col =2;
			 }
			 if((xpos > -360 && xpos <-270)||(xpos > 0 && xpos <90)) { 
				 col =3; 
			 }
			 if (col!=this.getPassposition()) {
				 return true;
			 }
		 }
	return false;	
	}
	
}
