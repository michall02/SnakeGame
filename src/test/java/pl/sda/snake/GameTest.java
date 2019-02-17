package pl.sda.snake;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GameTest {

    //to zasada któa wykonuje sie dla każdego testu, taka ogólna zasada, tu mówimy że nie oczekujemy żadnego wyjątku
    @Rule
    private ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldMoveForwardWhenTheIsNoAction(){

        //given
        Snake snake = new Snake(SnakeDirection.RIGHT,
                field(2, 1),
                field(1, 1),
                field(0, 1)
        );
        Game game = new Game(snake);

        //when
        game.nextTurn();

        //then
        Snake expectedSnake = new Snake(SnakeDirection.RIGHT,
                field(3, 1),
                field(2, 1),
                field(1, 1)
        );
        Assert.assertEquals(expectedSnake, snake);
    }

    @Test
    public void shouldMoveDownWhenThereIsDownAction(){

        //given
        Snake snake = new Snake(SnakeDirection.RIGHT,
                field(2, 1),
                field(1, 1),
                field(0, 1)
        );
        Game game = new Game(snake);

        //when
        game.moveDown();
        game.nextTurn();

        //then
        Snake expectedSnake = new Snake(SnakeDirection.DOWN,
                field(2, 2),
                field(2, 1),
                field(1, 1)
        );
        Assert.assertEquals(expectedSnake, snake);
    }

    @Test
    public void shouldMoveUpWhenThereIsUpAction(){

        //given
        Snake snake = new Snake(SnakeDirection.RIGHT,
                field(2, 1),
                field(1, 1),
                field(0, 1)
        );
        Game game = new Game(snake);

        //when
        game.moveUp();
        game.nextTurn();

        //then
        Snake expectedSnake = new Snake(SnakeDirection.UP,
                field(2, 0),
                field(2, 1),
                field(1, 1)
        );
        Assert.assertEquals(expectedSnake, snake);
    }

    @Test
    public void shouldMoveLeftWhenThereIsLeftAction(){

        //given
        Snake snake = new Snake(SnakeDirection.DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0)
        );
        Game game = new Game(snake);

        //when
        game.moveLeft();
        game.nextTurn();

        //then
        Snake expectedSnake = new Snake(SnakeDirection.LEFT,
                field(1, 2),
                field(2, 2),
                field(2, 1)
        );
        Assert.assertEquals(expectedSnake, snake);
    }

    @Test
    public void shouldMoveRightWhenThereIsRightAction(){

        //given
        Snake snake = new Snake(SnakeDirection.DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0)
        );
        Game game = new Game(snake);

        //when
        game.moveRight();
        game.nextTurn();

        //then
        Snake expectedSnake = new Snake(SnakeDirection.RIGHT,
                field(3, 2),
                field(2, 2),
                field(2, 1)
        );
        Assert.assertEquals(expectedSnake, snake);
    }

    @Test
    public void shouldMoveForwardWhenThereIsOppositeDirectionAction(){

        //given
        Snake snake = new Snake(SnakeDirection.DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0)
        );
        Game game = new Game(snake);

        //when
        game.moveUp();
        game.nextTurn();

        //then
        Snake expectedSnake = new Snake(SnakeDirection.DOWN,
                field(2, 3),
                field(2, 2),
                field(2, 1)
        );
        Assert.assertEquals(expectedSnake, snake);
    }

    @Test
    public void shouldGameBeOverWhenTottomBorderIsReached(){

        //given
        Snake snake = new Snake(SnakeDirection.DOWN,
                field(2, 2),
                field(2, 1),
                field(2, 0)
        );
        Game game = new Game(snake);
        game.setAreaHeight(2);

        //expect
        expectedException.expect(GameOverException.class);
        game.nextTurn();

        //then

    }













    private GameField field(int x, int y) {
        return new GameField(x, y);
    }


}