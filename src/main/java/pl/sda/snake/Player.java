package pl.sda.snake;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Player {
    private String name;
    private int score;
    private LocalDate date;

    @Override
    public String toString() {
        return name+","+score+","+date;
    }
}
