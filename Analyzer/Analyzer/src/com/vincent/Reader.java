package com.vincent;

import java.util.ArrayList;
// Java Program to illustrate Reading from FileReader
// using BufferedReader Class

// Importing input output classes
import java.io.*;

// Main class
public class Reader {

    private String tempsent = "";
    private String line = "";
    private String identifier = "";
    private int punctIndex = -1;
    private Entry entry;
    // main driver method

    public Reader(Entry entryObj) {
        entry = entryObj;
    }
    public void read(String filename) throws Exception {

        entry.setTitle(filename);
        // File path is passed as parameter
        File file = new File(
                "C:\\Users\\vjlombardo\\Desktop\\Java\\Text_Data\\ExampleTexts\\" + filename);

        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as punctIndex escape sequence)

        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new FileReader(file));

        // Declaring punctIndex string variable



        // Condition holds true till
        // there is character in punctIndex string
        while ((line = br.readLine()) != null) {

            // this is the basic while loop - increments every new line that is not null;

            if (line.isBlank()) {
                paragraphIncrement();
            }


            while (line.contains(".") || line.contains("?") || line.contains("!") || line.contains("\"")) {

                punctFinder();
                if (punctIndex != -1) {
                    specCase();
                    writeline();

                }else {
                    break;
                }
            }

            // if the line contains an end punctuation, we must split it and log the sentence. Must be punctIndex while loop for multiple sentence lines.

            // if the line is blank, it represents punctIndex change in paragraph. This means the previous line is finished. Increment paragraph.

            tempsent += line;
            // if there is no punctuation and the line has text, it is part of punctIndex sentence, and should be added to tempsent.

        }

    }

    public void punctFinder() {
        // should the value of the punct that is the least greater than 0
        //error only in ? and !

        punctIndex = -1;
        if (line.contains(".")) {
            punctIndex = line.indexOf(".");
        }

        if (line.contains("?")) {
            if (punctIndex == -1 || punctIndex > line.indexOf("?")){
                punctIndex = line.indexOf("?");
            }
        }
        if (line.contains("!")) {
            if (punctIndex == -1 || punctIndex > line.indexOf("!")){
                punctIndex = line.indexOf("!");
            }
        }



        if (line.contains("”")){
           if (punctIndex==-1 || (line.indexOf("“") < punctIndex && line.indexOf("”") > line.indexOf("“") )){
               punctIndex = line.indexOf("”");
           }
       }


        if (punctIndex != -1) {
            identifier = Character.toString(line.charAt(punctIndex)); // identifier
        }

    }
    public void specCase(){
        if (identifier.equals(".") && (line.substring(punctIndex - 2, punctIndex).equalsIgnoreCase("Mr") || line.substring(punctIndex - 2, punctIndex).equalsIgnoreCase("Ms") || line.substring(punctIndex - 3, punctIndex).equalsIgnoreCase("Mrs"))) {

            line = line.substring(0, punctIndex) + line.substring(punctIndex + 1);
        } else {
            tempsent += line.substring(0, punctIndex + 1);
            line = line.substring(punctIndex + 1);

        }
        //else if (line.length() >= punctIndex + 2 && line.substring(punctIndex + 1, punctIndex + 2).equals("”")) {

        //    tempsent += line.substring(0, punctIndex + 2);
        //    line = line.substring(punctIndex + 2);
    }
    public void writeline() throws IOException, ClassNotFoundException {
        if (!tempsent.isBlank() && punctIndex != -1) {
            entry.addtoEntryMatrix(new Sentence(tempsent.trim(),entry));
            tempsent = "";
        }
    }
    public void paragraphIncrement(){
        entry.incrementpara();

    }
}






