package crawling.src;

import java.io.*;
import java.util.HashSet;

public class StopWord {
    static private HashSet<String> hash = new HashSet<>();

    StopWord() {
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(new FileInputStream("crawling/testStopWord.txt"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String word;
            while((word = br.readLine()) != null) {
                hash.add(word);
            }
            
            br.close();
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static HashSet<String> getHash() {
        return hash;
    }
}
