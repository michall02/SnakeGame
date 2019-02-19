package pl.sda.snake;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {

    public void saveWriter(List<Player> list,File file){
        try(FileWriter writer = new FileWriter(file)) {
            for (Player player : list) {
                writer.write(player+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
