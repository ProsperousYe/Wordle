package comp1721.cwk1;

import jdk.jfr.Label;

import java.util.ArrayList;
import java.util.Scanner;


public class Guess {
    private final int guessNumber;
    private String chosenWord;
  // Use this to get player input in readFromPlayer()
  private final Scanner input = new Scanner(System.in);

    // TODO: Implement constructor with int parameter
    public Guess(int num){
        if(!(1 <= num && num <= 6)) {
            throw new GameException("the num is invalid!");
        }
        guessNumber = num;
    }
    // TODO: Implement constructor with int and String parameters
    public Guess(int num, String word){
        if(!(1 <= num && num <= 6)) {
            throw new GameException("the num is invalid:(");
        }
        if(word.length()!=5||!word.matches("^[a-zA-Z]+$")) {
            throw new GameException("the word is invalid:(");
        }
        chosenWord = word.toUpperCase();
        guessNumber = num;
    }
    // TODO: Implement getGuessNumber(), returning an int
    public int getGuessNumber(){
        return guessNumber;
    }
    // TODO: Implement getChosenWord(), returning a String
    public String getChosenWord(){
        return chosenWord;
    }
    // TODO: Implement readFromPlayer()
    public void readFromPlayer(){
        String word = input.next().toUpperCase();
        if(word.length()!=5||!word.matches("^[A-Z]+$")) {
            System.out.println("the word is invalid:(");
            System.exit(0);
            //throw new GameException("the word is invalid:(");
        }
        chosenWord = word;
    }
    // TODO: Implement compareWith(), giving it a String parameter and String return type
    public String compareWith(String target){
        StringBuilder ret = new StringBuilder();
        ArrayList<Integer> perfect = new ArrayList<>();
        ArrayList<Integer> correct = new ArrayList<>();
        StringBuilder targetCopyBuilder1 = new StringBuilder();
        for(int i=0;i<chosenWord.length();i++) {
            if (chosenWord.charAt(i) == target.charAt(i)) {
                perfect.add(i);
            }
        }
        for(int i = 0; i <target.length();i++){
            if(!perfect.contains(i)) {
                targetCopyBuilder1.append(target.charAt(i));
            }
        }
        for(int i=0;i<chosenWord.length();i++){
            if(targetCopyBuilder1.toString().contains(String.valueOf(chosenWord.charAt(i)))){
                correct.add(i);
                for(int j = 0;j < targetCopyBuilder1.length();j++) {
                    if(targetCopyBuilder1.charAt(j) == chosenWord.charAt(i)){
                        targetCopyBuilder1.deleteCharAt(j);
                        break;
                    }
                }
            }
        }
        for(int i=0;i<chosenWord.length();i++){
            if(perfect.contains(i)) {
                ret.append("\033[30;102m ").append(chosenWord.charAt(i)).append(" \033[0m");
            }else if(correct.contains(i)) {
                ret.append("\033[30;103m ").append(chosenWord.charAt(i)).append(" \033[0m");
            }else {
                ret.append("\033[30;107m ").append(chosenWord.charAt(i)).append(" \033[0m");
            }
        }
        return ret.toString();
    }

    //the compareWith methods of accessibility
    public String compareWithA(String target){
        StringBuilder ret = new StringBuilder();
        ArrayList<Integer> perfect = new ArrayList<>();
        ArrayList<Integer> correct = new ArrayList<>();
        StringBuilder targetCopyBuilder1 = new StringBuilder();
        for(int i=0;i<chosenWord.length();i++) {
            if (chosenWord.charAt(i) == target.charAt(i)) {
                perfect.add(i);
            }
        }
        for(int i = 0; i <target.length();i++){
            if(!perfect.contains(i)) {
                targetCopyBuilder1.append(target.charAt(i));
            }
        }
        //delete i in correct if it has been exist in perfect
        for(int i=0;i<chosenWord.length();i++){
            if(targetCopyBuilder1.toString().contains(String.valueOf(chosenWord.charAt(i)))){
                for(int j = 0;j < targetCopyBuilder1.length();j++) {
                    if(targetCopyBuilder1.charAt(j) == chosenWord.charAt(i)){
                        targetCopyBuilder1.deleteCharAt(j);
                        break;
                    }
                }
                correct.add(i);
            }
        }
        //delete the unnecessary index in correct
        for(int i =0;i<correct.size();i++){
            if(perfect.contains(correct.get(i))){
                correct.remove(i);
            }
        }
        //form the ret
        if(!correct.isEmpty()) {
            showA(ret, correct);
            ret.append(" correct but in wrong place");
        }
        if(!perfect.isEmpty() && !correct.isEmpty()) {
            ret.append(", ");
        }
        if(!perfect.isEmpty()) {
            showA(ret, perfect);
            ret.append(" perfect");
        }
        return ret.toString();
    }

    //method that form the sentence of return
    private void showA(StringBuilder ret, ArrayList<Integer> list) {
        for (int j = 1; j < list.toArray().length + 1; j++) {
            if (list.toArray().length!=1 && j == list.toArray().length) {
                ret.append(" and ");
            }else if(j > 1) {
                ret.append(", ");
            }if (list.get(j-1) == 0) {
                ret.append("1st");
            }else if (list.get(j-1) == 1) {
                ret.append("2nd");
            }else if (list.get(j-1) == 2) {
                ret.append("3rd");
            }else {
                ret.append(list.get(j-1) + 1).append("th");
            }
        }
        if(ret.isEmpty()){
            ret.append("nothing correct\n");
        }
    }

    // TODO: Implement matches(), giving it a String parameter and boolean return type
    public boolean matches(String target){
        return target.equals(chosenWord);
    }
}
