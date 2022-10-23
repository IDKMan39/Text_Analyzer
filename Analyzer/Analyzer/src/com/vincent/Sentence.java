package com.vincent;

import javafx.util.Pair;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Sentence {
    private int wordCount = 0 ;
    private boolean endSent;
    private String rawsent;
    private List<String> wordsFromPOS;
    private List<String> partsOfSpeech;
    private String[] words;
    private Entry entry;
    private boolean isQuote;
    private final List<String> punct = Arrays.asList(",","“","”","\"","—","--",".","!","?","(",")",";","''","``");
    private List<Word> word_Pos_Map;


    public Sentence(String rawsent, Entry entryparam)  throws IOException, ClassNotFoundException {
        entry = entryparam;
        this.rawsent = rawsent;
        Tagger tagger = new Tagger(rawsent);


        partsOfSpeech =  new LinkedList<String>(Arrays.asList(tagger.getArrOfPOS()));
        wordsFromPOS =  new LinkedList<String>(Arrays.asList(tagger.getArrOfWords()));


        cleanSent();
        //System.out.println(toString());
        cleanall();

    }

    public String toString() {
        //Arrays.toString(partsOfSpeech) + "\n" + Arrays.toString(wordsFromPOS)
        return partsOfSpeech.toString() + "          " + wordsFromPOS.toString();
    }

    public void cleanall(){
        word_Pos_Map = new ArrayList<Word>();
        int index = 0;

        while (index < wordsFromPOS.size()){
           String element = wordsFromPOS.get(index);
           Word wordObj = new Word();
           if (element.equals("-LRB-")){
               element = "(";
           }
           if (element.equals("-RRB-")){
               element = ")";
           }

           if (punct.contains(element)){
               wordObj.setElement(element);
               wordObj.setPunct(true);
               wordObj.setPos(partsOfSpeech.get(index));
               if (element.equals("--") || element.equals("—")){
                   wordObj.setInfo("Replaces_Space");
               } else if (element.equals("“")){
                   wordObj.setInfo("Start_Quote");
               }else if (element.equals("”")){
                   wordObj.setInfo("Start_Quote");
               } else if (element.equals("!") || element.equals("?")|| element.equals(".")){
                   wordObj.setInfo("End_Punct");
               }

           } else {
               wordCount += 1;
               entry.addwords(1);
               if (index != wordsFromPOS.size()-1 && (wordsFromPOS.get(index+1).contains("'") && wordsFromPOS.get(index+1).length() >= 2)){
                   wordObj.setElement(element + wordsFromPOS.get(index+1));
                   wordObj.setPos(partsOfSpeech.get(index) + "_" + partsOfSpeech.get(index+1));
                   if (wordsFromPOS.get(index+1).equals("n't")){
                       wordObj.setInfo("Contraction");
                   }
                   if (wordsFromPOS.get(index+1).equals("'s")){
                       wordObj.setInfo("Possessive");
                   }
                   if (wordsFromPOS.get(index+1).equals("'ll")){
                       wordObj.setInfo("Apostraphe LL");
                   }
                   wordsFromPOS.remove(index+1);
                   partsOfSpeech.remove(index+1);

               } else {
                   wordObj.setElement(element);
                   wordObj.setPos(partsOfSpeech.get(index));
               }
           }


           word_Pos_Map.add(wordObj); /// errrors here/?????
           index +=1;

        }

    }
    public void cleanSent(){
        String[] takeEmOut = {",","“","”","\"","—",".","!","?","(",")",";","  "};
        String strNew = rawsent.toLowerCase();

        String repstr = "";
        // rawsent = “Hold your noise!” cried a terrible voice, as a man started up from among the graves at the side of the church porch. “Keep still, you little devil, or I’ll cut your throat!”
        for (String x : takeEmOut){
            if (x.equals("—") || x.equals("  ")){repstr = " ";} else {repstr = "";}

            strNew = strNew.replace(x, repstr);
        }

        words = strNew.split(" ");

    }
    public List<Word> getWords(){
        return word_Pos_Map;
    }
}