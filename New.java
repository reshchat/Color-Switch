package application;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage theStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
class Homepage{
	
}
class Game{
	private String name;
	private int level;
	private int distance;
}
class Ball{
	private int x;
	private int y;
	private int colour;
	public void Ball() {
		
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
	
}
class Player{
	
}
class ColourChanger{
	
}
abstract class Obstacle{
	
}

