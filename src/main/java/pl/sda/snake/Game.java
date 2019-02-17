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
        GameField nextField = snake.getNextField();
        if(isXOutOfArea(nextField) || isYOutOfArea(nextField)){
            throw new GameOverException();
        }
        snake.move();
    }

    private boolean isYOutOfArea(GameField nextField) {
        return nextField.getY()<0 || nextField.getY() > areaHeight;
    }

    private boolean isXOutOfArea(GameField nextField) {
        return nextField.getX()<0 || nextField.getX() > areaWidth;
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
