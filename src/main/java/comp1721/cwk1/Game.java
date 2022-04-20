package comp1721.cwk1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
   private int gameNumber;
   private final String target;
   private String history = "";
   private String historyDetail="";
   // TODO: Implement constructor with String parameter
   public Game(String filename) throws IOException {
       WordList wordlist = new WordList(filename);
       gameNumber = Period.between(LocalDate.of(2022, 4, 17), LocalDate.now()).getDays();
       target = wordlist.getWord(gameNumber);
       historyDetail += "Gamenumber:" + gameNumber + "\n" + "Target:" + target + "\n";
       System.out.println("Wordle "+gameNumber+"\n");
   }
   // TODO: Implement constructor with int and String parameters
   public Game(int num, String filename) throws IOException {
       WordList wordlist = new WordList(filename);
       gameNumber = num;
       target = wordlist.getWord(gameNumber);
       historyDetail += "Gamenumber:" + gameNumber + "\n" + "Target:" + target + "\n";
       System.out.println("Wordle:"+gameNumber+"\n");
   }
   // TODO: Implement play() method
   public void play(){
       Guess guessInit = new Guess(1);
       System.out.print("please input the word(1/6):");
       guessInit.readFromPlayer();
       System.out.println(guessInit.compareWith(target));
       history += guessInit.compareWith(target) + "\n";
       historyDetail += guessInit.getChosenWord() + "\n";
       if(guessInit.matches(target)){
            System.out.println("Superb - Got it in one!");
            historyDetail += """
                    Successfully
                    Times:1
                    """;
       } else {
           for(int i = 2; i <= 6; i++){
               Guess guess = new Guess(i);
               System.out.print("please input the word(" + i + "/6):");
               guess.readFromPlayer();
               System.out.println(guess.compareWith(target));
               history += guess.compareWith(target) + "\n";
               historyDetail += guess.getChosenWord() + "\n";
               if (out(i, guess)) {return;}
           }
           System.out.println("Nope - Better luck next time!");
           historyDetail += """
                   False
                   Times:6
                   """;
       }
   }
    //the method to play the game in accessibility model
    public void playA(){
        Guess guessInit = new Guess(1);
        System.out.print("please input the word(1/6):");
        guessInit.readFromPlayer();
        System.out.println(guessInit.compareWithA(target));
        history += guessInit.compareWithA(target) + "\n";
        historyDetail += guessInit.getChosenWord() + "\n";
        if(guessInit.matches(target)){
            System.out.println("Superb - Got it in one!");
            historyDetail += """
                    Successfully
                    Times:1
                    """;
        } else {
            for(int i = 2; i <= 6; i++){
                Guess guess = new Guess(i);
                System.out.print("please input the word(" + i + "/6):");
                guess.readFromPlayer();
                System.out.println(guess.compareWithA(target));
                history += guess.compareWithA(target) + "\n";
                historyDetail += guess.getChosenWord() + "\n";
                if (out(i, guess)) {return;}
            }
            System.out.println("Nope - Better luck next time!");
            historyDetail += """
                    False
                    Times:6
                    """;
        }
    }
    //method to from the info according to the guess number
    private boolean out(int i, Guess guess) {
        if(guess.matches(target) && (i < 6)){
            System.out.println("Well done!");
            historyDetail += "Successfully\n" + "Times:" + i + "\n";
            return true;
        } else if(guess.matches(target) && (i == 6)){
            System.out.println("That was a close call!");
            historyDetail += "Successfully\n" + "Times:" + i + "\n";
            return true;
        }
        return false;
    }

    //the method that shows the statistic of game
    public void show(String filename) throws IOException {
       int numberofGamePlayed = 0;
       int numberofWin = 0;
       String percentageofGamesWins;
       int lengthofCurrentWinSteak = 0;
       int longestofWinStreak;
       String line;
       FileReader fileReader = new FileReader(filename);
       BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<Integer> winList = new ArrayList<>();
        ArrayList<Integer> streakList = new ArrayList<>();
        while((line = bufferedReader.readLine()) != null) {
            if (line.equals("Successfully")) {
                winList.add(1);
                numberofWin++;
            } else if (line.equals("False")) {
                winList.add(0);
            } else if (line.contains("Gamenumber")) {
                numberofGamePlayed++;
            }
        }
        for(int i = 0; i<winList.toArray().length;i++){
            if(winList.get(i) == 1){
                lengthofCurrentWinSteak++;
            } else {
                streakList.add(lengthofCurrentWinSteak);
                lengthofCurrentWinSteak = 0;
            }
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        percentageofGamesWins =
                numberFormat.format((float)numberofWin/(float)numberofGamePlayed*100);
        longestofWinStreak = Collections.max(streakList);
        if(lengthofCurrentWinSteak>longestofWinStreak){
            longestofWinStreak = lengthofCurrentWinSteak;
        }
        System.out.println("Number of games played:"+numberofGamePlayed);
        System.out.println("Percentage of games that were wins:"+percentageofGamesWins+"%");
        System.out.println("Length of the current winning streak:"+lengthofCurrentWinSteak);
        System.out.println("Longest winning streak:"+longestofWinStreak);

        //show the diagram
        JFrame frame=new JFrame("Histogram of guess distribution");
        frame.setLayout(new GridLayout(2,2,10,10));
        frame.add(barChart());
        frame.setBounds(0, 0, 900, 800);
        frame.setVisible(true);
    }

    //methods to create a barchart
    public ChartPanel barChart(){
        CategoryDataset dataset = getDataSet();
        JFreeChart chart = ChartFactory.createBarChart3D(
                "Histogram of guess distribution",
                "Chosen Word",
                "Times",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        CategoryPlot plot=chart.getCategoryPlot();
        CategoryAxis domainAxis=plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));
        domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));
        ValueAxis rangeAxis=plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        chart.getTitle().setFont(new Font("黑体", Font.BOLD,20));
        return new ChartPanel(chart, true);

    }

    //method to form the DataSet by reading the history.txt
    private CategoryDataset getDataSet() {
        int t;
        String[] chosenWordsList = historyDetail.split("\n");
        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        for(int i = 2;i<chosenWordsList.length-2;i++){
            if(!words.contains(chosenWordsList[i])){
                words.add(chosenWordsList[i]);
                times.add(1);
            } else {
                for (int j=0;j< words.size();j++){
                    if(chosenWordsList[i].equals(words.get(j))) {
                        t = times.get(j) + 1;
                        times.remove(j);
                        times.add(j, t);
                    }
                }
            }
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int j=0;j<words.size();j++){
            dataset.addValue(times.get(j), words.get(j), words.get(j));
        }
        return dataset;
    }
    // TODO: Implement save() method, with a String parameter
    public void save(String filename) throws IOException {
       FileWriter fileWriter = new FileWriter(filename);
       BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
       bufferedWriter.write(history);
       bufferedWriter.close();
       fileWriter.close();
    }

    //method to save history info
    public void saveHistory(String filename) throws IOException {
       FileWriter fileWriter = new FileWriter(filename,true);
       BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
       bufferedWriter.write(historyDetail);
       bufferedWriter.close();
       fileWriter.close();
    }
}
