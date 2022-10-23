package com.vincent;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.IOException;
import java.util.Arrays;

public class Tagger {
    private String[] arrOfWords;
    private String[] arrOfPOS;
    private Boolean  isQuestion = false;
    static MaxentTagger tagger;

    static {
        try {
            tagger = new MaxentTagger("res/models/left3words-wsj-0-18.tagger");
        } catch (Exception e) { }
    }

    public Tagger(String statement) throws IOException, ClassNotFoundException {

        String sample = statement;

// The tagged string

        String tagged = tagger.tagString(sample);

        String[] arrOfStr = tagged.split(" ", 0); // Gives an array w two spaces after? (0 discards after last split)
        arrOfWords = new String[arrOfStr.length];
        arrOfPOS = new String[arrOfStr.length];
        int i = 0; //countvar for index
        for (String posAndWord : arrOfStr) {
            String[] arrOfWordAndPos = posAndWord.split("/", -1);
            arrOfWords[i] = arrOfWordAndPos[0];
            arrOfPOS[i] = arrOfWordAndPos[1];
            i++;
        }
    }
    public String[] getArrOfPOS() {
        return arrOfPOS;
        // String returnval = Arrays.toString(array) - For Visually Printing Lists
    }
    public String[] getArrOfWords() {
        return arrOfWords;
        // String returnval = Arrays.toString(array) - For Visually Printing Lists
    }

    public String toString() {
       return Arrays.toString(arrOfPOS)  + " " + Arrays.toString(arrOfWords);
    }
    public int getIndexOf(String word){
        int x = 0;
        for(String i : arrOfWords){
            if(word.equals(i)){ return x; }
            x++;
        }
        return -1;
    }
    public void test() {
        //System.out.println(getIndexOf("how"));
        questionFinder();
        String firstVerb = getFirstVerb();
        String x = getSubject(getIndexOf(firstVerb));

    }
    public String getFirstVerb(){
        int count = 0;
        String firstVerb ="";
        for(String i : arrOfPOS){
            if ((String.valueOf(i.charAt(0))).equals("V")) {

                firstVerb=arrOfWords[count];
                System.out.println("Verb: "+ firstVerb);
            }
            count++;
        }

        return firstVerb;


    }
    public Boolean questionFinder(){
        if(arrOfWords[arrOfWords.length-1].equals("?")){
            isQuestion=true;
        }
        System.out.println(isQuestion);
        return isQuestion;
    }
    public String getSubject(int verbindex){
        String subject ="";

        for(int i = 0; i<verbindex;i++){
            String wordBeforeVerb = arrOfWords[i];
            String pos = arrOfPOS[i];
            if(isQuestion==false){
                if(pos.equals("PRP")) {
                    subject = wordBeforeVerb;
                }else if ((String.valueOf(pos.charAt(0))).equals("N")){
                    subject=wordBeforeVerb + "<<<<------ noun";
                }
            }else if (isQuestion==true){
                if(pos.equals("WP")) {
                    subject = wordBeforeVerb;
                }
            }



        }
        System.out.println("Subject : "+subject);
        return subject;


    }


   
// Output the result



}
