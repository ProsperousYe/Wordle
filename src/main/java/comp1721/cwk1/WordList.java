package comp1721.cwk1;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordList {
    static List<String> words = new ArrayList<>();
  // TODO: Implement constructor with a String parameter
    public WordList(String filename) throws IOException {
        File file = new File(filename);
        if(!file.exists())
            throw new IOException("The file do not exists:(");
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()){
            String word = scanner.nextLine();
            words.add(word);
        }
    }
  // TODO: Implement size() method, returning an int
    public static int size(){
        return words.size();
    }
  // TODO: Implement getWord() with an int parameter, returning a String
    public static String getWord(int n) throws GameException{
        if(n<0 || n>= words.size()) {
            throw new GameException("invalid number to select word:(");
        }
        return words.get(n);
    }
}
