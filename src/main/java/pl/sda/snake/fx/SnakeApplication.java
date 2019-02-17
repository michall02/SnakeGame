package pl.sda.snake.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pl.sda.snake.*;

import java.io.File;

public class SnakeApplication extends Application {

    private Game game;
    private static final double FIELD_HEIGHT = 64;
    private static final double FIELD_WIDTH = 64;
    private Canvas canvas;
    private Image appleImage;
    private Image grassImage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        File appleFille = new File("C:\\Users\\micha\\IdeaProjects\\snake\\src\\main\\resources\\apple.png");
        File grassFille = new File("C:\\Users\\micha\\IdeaProjects\\snake\\src\\main\\resources\\grass.jpg");
        grassImage = new Image(grassFille.toURI().toString());
        appleImage = new Image(appleFille.toURI().toString());

        newGame();

        double height = (game.getAreaHeight() + 1) * FIELD_HEIGHT;
        double width = (game.getAreaWidth() + 1) * FIELD_WIDTH;

        canvas = new Canvas(height, width);
        paint();

        new Thread(() -> {
            boolean isOver = false;
            while (!isOver) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    game.nextTurn();
                    paint();
                } catch (GameOverException e) {
                    isOver = true;
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.setStroke(Color.BLACK);
                    gc.setFill(Color.WHITE);
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.setFont(Font.font(80));
                    gc.strokeText("GAME OVER", canvas.getWidth() / 2, canvas.getHeight() / 2);
                    gc.fillText("GAME OVER", canvas.getWidth() / 2, canvas.getHeight() / 2);
//                    gc.setStroke(Color.BLACK);
//                    gc.setFill(Color.WHITE);
//                    gc.setTextAlign(TextAlignment.CENTER);
//                    gc.setFont(Font.font(40));
//                    gc.strokeText("PRESS SPACE TO START NEW GAME", canvas.getWidth() / 2, canvas.getHeight() / 2);
//                    gc.fillText("PRESS SPACE TO START NEW GAME", canvas.getWidth() / 2, canvas.getHeight() / 2);
                }
            }
        }).start();

        Pane pane = new Pane(canvas);
        Scene scene = new Scene(pane);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DOWN:
                    game.moveDown();
                    break;
                case UP:
                    game.moveUp();
                    break;
                case RIGHT:
                    game.moveRight();
                    break;
                case LEFT:
                    game.moveLeft();
                    break;
                case SPACE:
                    newGame();
                    break;

            }
        });
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle("SNAKE");
        primaryStage.show();


    }

    private void newGame(){
        game = new Game(
                new Snake(SnakeDirection.RIGHT,
                        new GameField(2, 0),
                        new GameField(1, 0),
                        new GameField(0, 0)
                ));
        game.setApple(new GameField(6, 6));

        new Thread(() -> {
            boolean isOver = false;
            while (!isOver) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    game.nextTurn();
                    paint();
                } catch (GameOverException e) {
                    isOver = true;
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.setStroke(Color.BLACK);
                    gc.setFill(Color.WHITE);
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.setFont(Font.font(80));
                    gc.strokeText("GAME OVER", canvas.getWidth() / 2, canvas.getHeight() / 2);
                    gc.fillText("GAME OVER", canvas.getWidth() / 2, canvas.getHeight() / 2);
//                    gc.setStroke(Color.BLACK);
//                    gc.setFill(Color.WHITE);
//                    gc.setTextAlign(TextAlignment.CENTER);
//                    gc.setFont(Font.font(40));
//                    gc.strokeText("PRESS SPACE TO START NEW GAME", canvas.getWidth() / 2, canvas.getHeight() / 2);
//                    gc.fillText("PRESS SPACE TO START NEW GAME", canvas.getWidth() / 2, canvas.getHeight() / 2);
                }
            }
        }).start();
    }

    private void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(grassImage, 0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.LIGHTGRAY);
        for (int x = 0; x <= game.getAreaWidth(); x++) {
            for (int y = 0; y <= game.getAreaHeight(); y++) {
                gc.strokeRect(x * FIELD_WIDTH, y * FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
            }
        }

        gc.setFill(Color.LIMEGREEN);
        game.getSnake().getTail().forEach(field -> {
            gc.fillRect(field.getX() * FIELD_WIDTH, field.getY() * FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
        });
//        GameField head = game.getSnake().getTail().getFirst();
//        SnakeDirection direction = game.getSnake().getDirection();
//        double ax=0, ay=0, bx=0, by=0, cx=0, cy=0;
//        switch (direction) {
//            case UP:
//                ax = head.getX() * FIELD_WIDTH;
//                ay = head.getY() * FIELD_HEIGHT+FIELD_HEIGHT;
//                bx = head.getX() * FIELD_WIDTH+FIELD_WIDTH;
//                by = head.getY() * FIELD_HEIGHT+FIELD_HEIGHT;
//                cx = head.getX() * FIELD_WIDTH+FIELD_WIDTH/2;
//                cy = head.getY() * FIELD_HEIGHT+FIELD_HEIGHT;
//        }
//        gc.setFill(Color.RED);
//        gc.fillPolygon(new double[]{ax,bx,cx},new double[]{ay,by,cy},3);

        GameField apple = game.getApple();
        gc.drawImage(appleImage, apple.getX() * FIELD_WIDTH, apple.getY() * FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);


    }
}
