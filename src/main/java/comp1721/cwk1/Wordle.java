// Main program for COMP1721 Coursework 1
// DO NOT CHANGE THIS!

package comp1721.cwk1;

import java.io.IOException;


public class Wordle {
  public static void main(String[] args) throws IOException, InterruptedException {
    Game game;

    if (args.length == 1) {
      if(args[0].equals("-a")){
        //the accessibility medol and not fixed
        //game = new Game(Integer.parseInt(args[0]), "data/words.txt");
        game = new Game("data/words.txt");
        game.playA();
        game.save("build/lastgame_accessible.txt");
        game.saveHistory("build/history.txt");
        game.show("build/history.txt");
      } else {
        //the fixed model
        game = new Game(Integer.parseInt(args[0]), "data/words.txt");
        game.play();
        game.save("build/lastgame.txt");
        game.saveHistory("build/history.txt");
        game.show("build/history.txt");
      }
    } if(args.length == 2) {
      // the fixed and accessibility model
      game = new Game(Integer.parseInt(args[1]), "data/words.txt");
      game.playA();
      game.save("build/lastgame_accessible.txt");
      game.saveHistory("build/history.txt");
      game.show("build/history.txt");
    } if(args.length == 0) {
      //the not fixed model
      game = new Game("data/words.txt");
      game.play();
      game.save("build/lastgame.txt");
      game.saveHistory("build/history.txt");
      game.show("build/history.txt");
    } else {
      System.out.println("invalid command line argument");
    }
    Thread.sleep(10000);
    System.exit(0);
  }
}
