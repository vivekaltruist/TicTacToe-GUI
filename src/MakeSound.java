import java.io.*;
import sun.audio.*;

public class MakeSound {

	  public static void makeSound(String filename) 
	  throws Exception
	  {
	    // open the sound file as a Java input stream
	    String gongFile = filename;
	    InputStream in = new FileInputStream(gongFile);
	 
	    // create an audiostream from the inputstream
	    AudioStream audioStream = new AudioStream(in);
	 
	    // play the audio clip with the audioplayer class
	    AudioPlayer.player.start(audioStream);
	  }
	
}