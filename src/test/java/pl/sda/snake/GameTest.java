package pl.sda.snake;

import org.junit.Test;

import java.util.ArrayList;


public class GameTest {


    @Test
    public void shouldMoveForwardWhenTheIsNoAction(){
        new Snake(SnakeDirection.RIGHT,
                new GameField(2,1),
                new GameField(1,1),
                new GameField(0,1)
                );
    }

}