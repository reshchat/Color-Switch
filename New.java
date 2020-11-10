package application;

import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

public class Main extends Application{
	
    static AnchorPane pane = new AnchorPane();
    static Scene scene = new Scene(pane, 500, 500);
    static Stage stage;
	Image image = new Image("file:Color-Switch-icon.png");
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
    static Scene scene = new Scene(pane, 500, 500);
    static Stage stage;
	private Game game;
	private int bestscore;
	
	public Homepage(){
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
	private void startNewgame(Stage stage){	
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
        		startNewgame(stage);
        	}
        	else if(src.getText().equals("Resume a saved game")) {
        		pane.getChildren().clear();
        		showSavedgames(stage);
        	}
        }
	}
}

class Game{
	private String name;
	private int level;
	private int distance;
	public Game() {
		
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
    public Ball() throws FileNotFoundException {
    	 red = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\red.png") );
         yellow = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\yellow.png") );
         blue = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\blue.png") );
         green = new Image( new FileInputStream("C:\\Users\\RESHMI\\eclipse-workspace\\Test\\src\\application\\green.png") );
         
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
	public Star getCollectedStars() {
		return null;
	}
	public void setCollectedStars(int no) {
		
	}
	public int getScore() {
		return 0;
	}
	public void setScore(int s) {
		
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
	public Star getColours() {
		return null;
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
	
	public Obstacle() {
		
	}
	public int getNoofcolours() {
		return noofcolours;
	}
	public void setNoofcolours(int colours) {
		
	}
	public int getPassposition() {
		return noofcolours;
	}
	public Star getY() {
		return null;
	}
	public void setY(int y) {
		
	}
	public void setPassposition(int pass) {
		
	}
	protected void obstacleHit() {
		
	}
	protected void movement(float duration) {
		
	}
}

