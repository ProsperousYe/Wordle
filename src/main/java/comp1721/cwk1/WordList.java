package comp1721.cwk1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordList {

    private static List<String> words = new ArrayList<>();
    private final int size = words.size();
    // TODO: Implement constructor with a String parameter
    public WordList(String filename) throws IOException {
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String word;
        while((word=bufferedReader.readLine())!=null) {
            if(word.trim().length()==5) {
                words.add(word);
            }
        }
    }
    // TODO: Implement size() method, returning an int
    public int size(){
        return size;
    }
    // TODO: Implement getWord() with an int parameter, returning a String
    public String getWord(int n) throws GameException{
        if(n<0 || n>= words.size()) {
            throw new GameException("invalid number to select word:(");
        }
        return words.get(n);
    }
}
