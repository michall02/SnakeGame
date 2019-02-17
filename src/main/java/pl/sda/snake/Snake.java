package pl.sda.snake;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.LinkedList;

@ToString
@EqualsAndHashCode
public class Snake {

    private SnakeDirection direction;
    @Getter
    private LinkedList<GameField> tail = new LinkedList<>();
    private boolean isEating;


    public Snake(SnakeDirection direction, GameField... gameField) {
        this.direction = direction;
        tail.addAll(Arrays.asList(gameField));
    }

    public void move() {
        GameField nextField = getNextField();
        tail.addFirst(nextField);
        if(!isEating){
            tail.removeLast();
        }else{
            isEating = false;
        }
    }

    public GameField getNextField() {
        switch(direction){
            case RIGHT: return getHead().getRight();
            case LEFT: return getHead().getLeft();
            case DOWN: return getHead().getDown();
            case UP: return getHead().getUp();
        }
        throw new IllegalArgumentException("Direction is invalid");
    }

    private GameField getHead() {
        return tail.getFirst();
    }

    public void setDirection(SnakeDirection direction) {
        if(!direction.isOpposite(this.direction)){
            this.direction = direction;
        }
    }

    public void eatApple() {
        isEating = true;
    }
}
