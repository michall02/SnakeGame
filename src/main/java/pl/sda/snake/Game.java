package pl.sda.snake;


import lombok.Setter;


public class Game {
    private Snake snake;
    @Setter
    private int areaHeight = 10;
    @Setter
    private int areaWidth = 10;

    public Game(Snake snake) {
        this.snake = snake;
    }


    public void nextTurn() {
        snake.move();
    }

    public void moveDown() {
        snake.setDirection(SnakeDirection.DOWN);
    }
    public void moveUp() {
        snake.setDirection(SnakeDirection.UP);
    }
    public void moveRight() {
        snake.setDirection(SnakeDirection.RIGHT);
    }
    public void moveLeft() {
        snake.setDirection(SnakeDirection.LEFT);
    }

}
