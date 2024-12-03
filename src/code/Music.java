package code;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music
{
    public Music() throws FileNotFoundException, JavaLayerException
    {
        String str = System.getProperty("user.dir")+"/src/music/music.wav";
        BufferedInputStream name = new BufferedInputStream(new FileInputStream(str));
        Player player = new Player(name);
        player.play();

    }
}
