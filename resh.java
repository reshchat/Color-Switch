/*
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
	// 0=red
	 // 1=blue
	 //2=yellow
	 //3=green
	 
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
    boolean pause=false;
    final long startNanoTime = System.nanoTime();
     GraphicsContext gc;
    private double leftPaddleDY;
    Ball ball;
    
    private double y=400;
    private int colour=0;
    Button button2;
    Button button3;
    Label l;
    Group root;
    int x3=0;
    Canvas canvas;
    Stage stage;
    Scene scene;// = new Scene(pane, 500, 500);
    AnchorPane pane;
    @Override
    public void start(Stage theStage) throws FileNotFoundException
    {
    	
    	//stage =theStage;
        theStage.setTitle( "Timeline Example" );
     
        //Group 
        root = new Group();
        Scene theScene = new Scene( root );
        
        theStage.setScene( theScene );
     
       canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );
        button2 = new Button("Pause");
        root.getChildren().add( button2 ); 
        
       
        
        
        gc = canvas.getGraphicsContext2D();
        ball=new Ball();
        l = new Label("button not selected");
        root.getChildren().add( l);
        //root.getChildren().remove(l);
        
     
        //final long startNanoTime = System.nanoTime();
       
        //canvas.setOnMouseClicked(event);
        
        
        canvas.setFocusTraversable(true);
       // canvas.requestFocus();
        canvas.setOnKeyPressed(keyPressed);
        canvas.setOnKeyReleased(keyReleased);
        button2.setOnAction(event);
     
   timer.start();

  // root.getChildren().add( button3 );
        theStage.show();
    }
    
    
    private void showMainmenu(Stage theStage) {
    	 pane = new AnchorPane();
    	Scene scene = new Scene(pane, 500, 500);
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

    
    private AnimationTimer timer =     new AnimationTimer()
    {
        public void handle(long currentNanoTime)
        {// background clears canvas
            gc.clearRect(0, 0,512,512);
        	 
        	
        	
        	
        	 
            if (pause) {
            	//return;
            }
            
            if(pause==false) {
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
        { x3++;
        l.setText(String.valueOf(x3));
       // if(pause==true)
        //canvas.requestFocus();
        //BorderPane borderPane = new BorderPane(); 		  
       // Scene scene = new Scene(borderPane, 600, 600); 		  
        //Stage 
        stage = new Stage(); 
        showMainmenu(stage);
        
           // l.setText("   button   selected    "); 
            pause=!(pause);
            //Platform.exit();
           
        } 
    }; 
    
}
*/
