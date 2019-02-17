package pl.sda.snake.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.sda.snake.Game;
import pl.sda.snake.GameField;
import pl.sda.snake.Snake;
import pl.sda.snake.SnakeDirection;

public class SnakeApplication extends Application {

    private Game game;
    private static final double FIELD_HEIGHT = 50;
    private static final double FIELD_WIDTH = 50;
    private Canvas canvas;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game(
                new Snake(SnakeDirection.RIGHT,
                new GameField(2,0),
                new GameField(1,0),
                new GameField(0,0)
        ));
        game.setApple(new GameField(6,6));
        double height = game.getAreaHeight() * FIELD_HEIGHT;
        double width = game.getAreaWidth() * FIELD_WIDTH;

        canvas = new Canvas(height,width);
        paint();

        Pane pane = new Pane(canvas);
        primaryStage.setScene(new Scene(pane));
        primaryStage.setHeight(height);
        primaryStage.setWidth(width);
        primaryStage.setResizable(false);
        primaryStage.setTitle("SNAKE");
        primaryStage.show();


    }

    private void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.LIGHTGRAY);
        for (int x = 0; x <= game.getAreaWidth(); x++) {
            for (int y = 0; y <= game.getAreaHeight(); y++) {
                gc.strokeRect(x*FIELD_WIDTH,y*FIELD_HEIGHT,FIELD_WIDTH, FIELD_HEIGHT);
            }
        }

        gc.setFill(Color.LIMEGREEN);
        game.getSnake().getTail().forEach(field->{
            gc.fillRect(field.getX()*FIELD_WIDTH,field.getY()*FIELD_HEIGHT,FIELD_WIDTH,FIELD_HEIGHT);
        });


        GameField apple = game.getApple();
        gc.setFill(Color.RED);
        gc.fillRect(apple.getX()*FIELD_WIDTH,apple.getY()*FIELD_HEIGHT,FIELD_WIDTH,FIELD_HEIGHT);

    }
}
