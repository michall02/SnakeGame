package pl.sda.snake.fx;


import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pl.sda.snake.*;

import java.io.File;
import java.time.LocalDate;

public class SnakeApplication extends Application {


    private static final double FIELD_HEIGHT = 64;
    private static final double FIELD_WIDTH = 64;
    private Game game;
    private Canvas canvas;
    private Image appleImage;
    private Image grassImage;
    private Player player;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        File appleFile = new File("C:\\Users\\micha\\IdeaProjects\\snake\\src\\main\\resources\\apple.png");
        File grassFile = new File("C:\\Users\\micha\\IdeaProjects\\snake\\src\\main\\resources\\grass.jpg");
        grassImage = new Image(grassFile.toURI().toString());
        appleImage = new Image(appleFile.toURI().toString());


        game = new Game(
                new Snake(SnakeDirection.RIGHT,
                        new GameField(2, 0),
                        new GameField(1, 0),
                        new GameField(0, 0)
                ));


        double height = (game.getAreaHeight() + 1) * FIELD_HEIGHT;
        double width = (game.getAreaWidth() + 1) * FIELD_WIDTH;

        canvas = new Canvas(width, height);
        Pane pane = new Pane(canvas);
        Scene gameScene = new Scene(pane);

        /**
         * menu
         */
        Button newGame = new Button("NEW GAME");
        Button records = new Button("SHOW RECORDS");
        Button exit = new Button("EXIT");
        VBox startingPane = new VBox(newGame, records, exit);
        startingPane.setAlignment(Pos.CENTER);
        startingPane.setSpacing(20);
        startingPane.setPadding(new Insets(10));
        Scene startingScene = new Scene(startingPane);


        /**
         * levels menu
         */
        CheckBox easy = new CheckBox("EASY");
        CheckBox medium = new CheckBox("MEDIUM");
        CheckBox hard = new CheckBox("HARD");

        easy.setOnMouseClicked(event -> {
            easy.setSelected(true);
            medium.setSelected(false);
            hard.setSelected(false);
        });
        medium.setOnMouseClicked(event -> {
            easy.setSelected(false);
            medium.setSelected(true);
            hard.setSelected(false);
        });
        hard.setOnMouseClicked(event -> {
            easy.setSelected(false);
            medium.setSelected(false);
            hard.setSelected(true);
        });
        Button back = new Button("BACK");
        Button play = new Button("PLAY");
        HBox levelButtons = new HBox(back, play);
        levelButtons.setAlignment(Pos.CENTER);
        levelButtons.setSpacing(20);
        levelButtons.setPadding(new Insets(10));

        VBox chooseLevel = new VBox(easy, medium, hard, levelButtons);
        chooseLevel.setAlignment(Pos.CENTER);
        chooseLevel.setSpacing(20);
        chooseLevel.setPadding(new Insets(10));
        Scene chooseLevelScene = new Scene(chooseLevel);

        Stage chooseLevelStage = new Stage();
        chooseLevelStage.setScene(chooseLevelScene);
        chooseLevelStage.setWidth(250);
        chooseLevelStage.setHeight(200);
        chooseLevelStage.setResizable(false);
        chooseLevelStage.setTitle("CHOOSE LEVEL");

        /**
         * records list
         */
        TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Player, Integer> scoreColumn = new TableColumn<>("Score");
        TableColumn<Player, LocalDate> dateColumn = new TableColumn<>("Time");

        ObservableList<Player> data = FXCollections.observableArrayList();
        TableView<Player> tableView = new TableView<>(data);
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        scoreColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getScore()));
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate()));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(scoreColumn);
        tableView.getColumns().add(dateColumn);


        Scene recordsListScene = new Scene(tableView);
        Stage recordsListStage = new Stage();
        recordsListStage.setScene(recordsListScene);

        /**
         * save record
         */
        Button backToGame = new Button("BACK");
        Button save = new Button("SAVE");
        HBox saveButtons = new HBox(backToGame, save);
        saveButtons.setAlignment(Pos.CENTER);
        saveButtons.setSpacing(20);
        saveButtons.setPadding(new Insets(10));


        Label score = new Label("SCORE: " + game.getScore());
        Label time = new Label("DATE: " + LocalDate.now());
        TextField name = new TextField("ENTER NAME");
        HBox saveFields = new HBox(name, score, time);
        saveFields.setAlignment(Pos.CENTER);
        saveFields.setSpacing(20);
        saveFields.setPadding(new Insets(10));

        VBox saveRecord = new VBox(saveFields, saveButtons);
        saveRecord.setAlignment(Pos.CENTER);
        saveRecord.setSpacing(20);
        saveRecord.setPadding(new Insets(10));
        Scene saveRecordScene = new Scene(saveRecord);
        Stage saveStage = new Stage();
        saveStage.setScene(saveRecordScene);

        /**
         * game
         */
        Stage gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.sizeToScene();
        gameStage.setResizable(false);
        gameStage.setTitle("SNAKE");




        newGame.setOnAction(event -> {
            chooseLevelStage.show();
            primaryStage.close();
        });
        records.setOnAction(event -> {
            primaryStage.close();
            recordsListStage.show();
        });
        exit.setOnAction(event -> primaryStage.close());

        back.setOnAction(event -> {
            chooseLevelStage.close();
            primaryStage.show();
        });

        play.setOnAction(event -> {
            if (easy.isSelected()) {
                newGame(600, 1);
            } else if (medium.isSelected()) {
                newGame(400, 2);
            } else if (hard.isSelected()) {
                newGame(200, 4);
            }
            gameStage.show();
            chooseLevelStage.close();
        });


        gameScene.setOnKeyPressed(e -> {
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
                    chooseLevelStage.show();
                    break;
                case ESCAPE:
                    gameStage.close();
                    primaryStage.show();
                    break;
                case S:
                    saveStage.show();
            }
        });


        primaryStage.setScene(startingScene);
        primaryStage.setWidth(250);
        primaryStage.setHeight(200);
        primaryStage.setResizable(false);
        primaryStage.setTitle("SNAKE");
        primaryStage.show();


    }


    private void paint(int multiplier) {
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


        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.strokeText("SCORE: " + game.getScore() * multiplier, 50, 30);
        gc.fillText("SCORE: " + game.getScore() * multiplier, 50, 30);


    }

    private void newGame(int speed, int multiplier) {
        game = new Game(
                new Snake(SnakeDirection.RIGHT,
                        new GameField(2, 0),
                        new GameField(1, 0),
                        new GameField(0, 0)
                ));
        game.setApple(new GameField(6, 6));

        newThread(speed, multiplier);
    }

    private void newThread(int speed, int multiplier) {
        new Thread(() -> {
            boolean isOver = false;
            while (!isOver) {
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    game.nextTurn();
                    paint(multiplier);


                } catch (GameOverException e) {
                    isOver = true;
                    gameOverAnnouncement();
                }
            }
        }).start();
    }


    private void gameOverAnnouncement() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(80));
        gc.strokeText("GAME OVER", canvas.getWidth() / 2, (canvas.getHeight() / 2) - 40);
        gc.fillText("GAME OVER", canvas.getWidth() / 2, (canvas.getHeight() / 2) - 40);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(40));
        gc.strokeText("PRESS SPACE TO START NEW GAME", canvas.getWidth() / 2, (canvas.getHeight() / 2) + 20);
        gc.fillText("PRESS SPACE TO START NEW GAME", canvas.getWidth() / 2, (canvas.getHeight() / 2) + 20);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.strokeText("PRESS ESC TO BACK TO MENU", canvas.getWidth() / 2, (canvas.getHeight() / 2) + 60);
        gc.fillText("PRESS ESC TO BACK TO MENU", canvas.getWidth() / 2, (canvas.getHeight() / 2) + 60);

    }
}
