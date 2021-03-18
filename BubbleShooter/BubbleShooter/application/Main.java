
package application;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	private static final int HEIGHT = 500;
	private static final int WIDTH = 700;
	private static final int MAX_SIZE = 250;
	private Canvas canvas;
	private int bulletYPosition = HEIGHT-10;
	ArrayList<Circle> CircleArr = new ArrayList();
	ArrayList<Square> SquareArr = new ArrayList();
	Circle c = new Circle();
	Square a = new Square();
	Timer[] timer = new Timer[2];

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("ShapeShooter");

			Button btn1 = new Button("Circle");
			Button btn2 = new Button("Square");
			Button btn3 = new Button("MoveShape");
			Button btn4 = new Button("Start");
			Button btn5 = new Button("Stop");
			Button btn6 = new Button("LoadBullet");
			Button btn7 = new Button("MoveBullet");
			Button btn8 = new Button("ShootBullet");
			

			btn1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					GraphicsContext gc = canvas.getGraphicsContext2D();

					Random r = new Random();
					Circle c = new Circle();
					c.x = r.nextInt(WIDTH);
					c.y = r.nextInt(HEIGHT);
					c.d = r.nextInt(MAX_SIZE);

					gc.setStroke(Color.BLACK);
					gc.strokeOval(c.x, c.y, c.d, c.d);
					CircleArr.add(c);
				}
			});
			btn2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					Square a = new Square();
					GraphicsContext gc = canvas.getGraphicsContext2D();
					Random r = new Random();
					a.x = r.nextInt(WIDTH);
					a.y = r.nextInt(HEIGHT);
					a.s = r.nextInt(MAX_SIZE);

					gc.setStroke(Color.BLACK);
					gc.strokeRect(a.x, a.y, a.s, a.s);
					SquareArr.add(a);
				}
			});
			btn3.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
				move();


					
				}
			});
			
			
			
			btn4.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					 timer[0] = new Timer();
					timer[0].scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							move();
}{
						}
					}, 0, 100);
					
					}
				});
			
			
			btn5.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (timer != null)
					timer[0].cancel();
				}
				});
			
			btn6.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					drawBullet();
				}
				});
			
			/*btn7.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					moveBullet();
				}
				});*/
			btn8.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					 timer[1] = new Timer();
					timer[1].scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							drawBullet();
							moveBullet();
							if ( bulletYPosition < 0){
								timer[1].cancel();
								eraseBullet();
								bulletYPosition = HEIGHT-10;
								drawBullet();
							}
}{
						}
					}, 0, 100);
					
					
					
					}
				});
			

			HBox hb1 = new HBox();
			hb1.setAlignment(Pos.CENTER);
			hb1.getChildren().add(btn1);
			hb1.getChildren().add(btn2);
			hb1.getChildren().add(btn3);
			hb1.getChildren().add(btn4);
			hb1.getChildren().add(btn5);
			hb1.getChildren().add(btn6);
			//hb1.getChildren().add(btn7);
			hb1.getChildren().add(btn8);
			


			canvas = new Canvas(WIDTH, HEIGHT);
			drawBullet();

			BorderPane root = new BorderPane();
			root.setCenter(canvas);
			root.setBottom(hb1);

			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Color pickRandomColor() {
		Color[] colors = new Color[5];
		colors[0] = Color.RED;
		colors[1] = Color.GREEN;
		colors[2] = Color.BLUE;
		colors[3] = Color.YELLOW;
		colors[4] = Color.DARKSALMON;

		Random r = new Random();
		int i = r.nextInt(5);

		return colors[i];
	}
	
	
	
	public void move(){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, WIDTH, HEIGHT);

		for (Circle c : CircleArr) {
			
			if(c.xdirection == 1){
				c.x = c.x + 5;
				if (c.x + c.d >= WIDTH) {
					c.xdirection = -1;
				}
			}
			else{
				c.x = c.x - 5;
				if(c.x <= 0){
					c.xdirection = 1;
				}
			}
				if (c.ydirections == 1) {
					c.y = c.y + 5;
					if (c.y + c.d >= HEIGHT) {
						c.ydirections = -1;
					}
				}
				else{
					c.y = c.y - 5;
					if(c.y <= 0){
						c.ydirections = 1;
					}
				}	
				gc.strokeOval(c.x + 2, c.y + 2, c.d, c.d);
			
		}
			for (Square a : SquareArr) {
				if(a.xdirection == 1){
					a.x = a.x + 5;
					if (a.x + a.s >= WIDTH) {
						a.xdirection = -1;
					}
				}
				else{
					a.x = a.x - 5;
					if(a.x <= 0){
						a.xdirection = 1;
					}
				}
				if (a.ydirections == 1) {
					a.y = a.y + 5;
					if (a.y + a.s >= HEIGHT) {
						a.ydirections = -1;

					}

				} else {
					a.y = a.y - 5;
					if(a.y <= 0){
						a.ydirections = 1;
					}
				}
				gc.strokeRect(a.x + 2, a.y + 2, a.s, a.s);
			}
	}
	
	
	public void drawBullet(){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.fillRect(WIDTH /2, bulletYPosition, 5, 10);
	}
	
	public void eraseBullet(){
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(WIDTH /2, bulletYPosition, 5, 10);
	}
	
	public void moveBullet(){
	eraseBullet();
	bulletYPosition -= 5;
	drawBullet();
	}
	
	
	

	public static void main(String[] args) {
		launch(args);
	}

}

