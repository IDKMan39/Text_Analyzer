package com.vincent;
import java.util.ArrayList;

public class Entry {
    private ArrayList<ArrayList<Sentence>> EntryMatrix = new ArrayList<ArrayList<Sentence>>();
    private String title;
    private int numParagraphs = 1;
    private int numSentences = 0;
    private int numwords = 0;
    private int id;
    public Entry(){
        EntryMatrix.add(new ArrayList<Sentence>());
    }
    public void incrementpara(){
        numParagraphs += 1;

        EntryMatrix.add(new ArrayList<Sentence>());

    }
    public void incrementsent(){
        numSentences += 1;

        EntryMatrix.add(new ArrayList<Sentence>());

    }
    public void addwords(int num){
        numwords = numwords + num;
    }
    public void addtoEntryMatrix(Sentence x){
        EntryMatrix.get(numParagraphs-1).add(x);
        incrementsent();
    }
    public void printSentences(){
        for (ArrayList<Sentence> i : EntryMatrix){
            for (Sentence n : i) {
                System.out.println(n);
            }
        }
    }
    public String toString(){
        return "id : " + id + "   Paragraphs : " + numParagraphs + "       Numwords : " + numwords;
    }
    public void setTitle(String name){
        title = name;
    }
    public String getTitle(){
        return title;
    }
    public void setId(int idparam){
        id = idparam;
    }

    public int getId() {
        return id;
    }

    public int getNumParagraphs() {
        return numParagraphs;
    }

    public int getNumSentences() {
        return numSentences;
    }
    public int getNumwords(){
        return numwords;
    }

    public ArrayList<ArrayList<Sentence>> getEntryMatrix() {
        return EntryMatrix;
    }

}
