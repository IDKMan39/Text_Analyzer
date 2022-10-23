package com.vincent;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
public class SqlMod {
    private static Connection conn;

    private Entry entry;
    private HashMap<String, Integer> map;
    public SqlMod(Entry entryparam){
        entry = entryparam;
        map =  new HashMap<>();
    }
    public void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close(PreparedStatement stat) {
        try {
            if (stat != null) {
                stat.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close(Statement stat) {
        try {
            if (stat != null) {
                stat.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addEntry() throws SQLException {
        PreparedStatement prep = conn.prepareStatement("insert into entries (title) values (?);");
        prep.setString(1,entry.getTitle());
        prep.executeUpdate();

        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT last_insert_rowid();");
        while (rs.next()){
            entry.setId(rs.getInt(1));
        }
        close(stat);
        close(prep);

    }
    public void scanWords() throws SQLException{
        ArrayList<ArrayList<Sentence>> matrix = entry.getEntryMatrix();
        for (ArrayList<Sentence> i : matrix) {
            for (Sentence x : i){
                for (Word word : x.getWords())

                    // logic for hashmap
                    if (!word.getPunct()) {
                        String element = word.getElement();
                        if (map.containsKey(element)) {
                            map.replace(element, (map.get(element) + 1));
                        } else {
                            map.put(element, 1);
                        }
                    }
            }
        }
        printmap();

        // will scan all words into a word table ---
        // Requirements : 1. Will iterate through each word 2. Will check for duplicates 3. will associate with id
    }
    public void addMap() throws  SQLException {

        int wordid = -1;
        for (String key : map.keySet()){

            boolean found  = false;
            PreparedStatement prep = conn.prepareStatement("select word_id from words where word = ?;");
            prep.setString(1,key);


            ResultSet rs = prep.executeQuery();


            while (rs.next()){
                wordid = rs.getInt(1);
                found = true;


            }
            close(prep);
            if (!found || wordid<0){
                // add the word and get id using last insert
                PreparedStatement prep2 = conn.prepareStatement("insert into Words(word) values (?);");
                prep2.setString(1,key);
                prep2.executeUpdate();
                close(prep2);
                Statement stat = conn.createStatement();
                ResultSet rs2 = stat.executeQuery("SELECT last_insert_rowid();");
                while (rs2.next()){
                    wordid = rs2.getInt(1);
                }
                close(stat);
            }


            // take the id and use it in the table
            //System.out.println(key + "  <---Found"  +  "\t" + "id=" + wordid + "\t Count : " + map.get(key));
            PreparedStatement prep2 = conn.prepareStatement("insert into Word_Count values (?,?,?);");

            prep2.setInt(1, wordid);
            prep2.setInt(2,entry.getId());
            prep2.setInt(3,map.get(key));
            prep2.executeUpdate();

            close(prep2);

            }
    }
    public void cleardb() throws  SQLException {
        String[] tablelist = {"Entries", "Words","Word_Count"};
        Statement stat = conn.createStatement();
        stat.executeUpdate("Delete from Entries;");
        stat.executeUpdate("Delete from Words;");
        stat.executeUpdate("Delete from Word_Count;");
        close(stat);
    }
    public void addInfo() throws SQLException {

        PreparedStatement prep = conn.prepareStatement("UPDATE Entries SET numwords = ?, avwordpsentence=? where id = ?;");
        prep.setInt(1,entry.getNumwords());

        double wordspsent = (double) ((Math.round((float)entry.getNumwords()/entry.getNumSentences()*100.0))/100.0);
        prep.setDouble(2,(wordspsent));
        prep.setInt(3,entry.getId());
        prep.executeUpdate();
        prep.close();

    }
    public void printmap() throws SQLException{
        for (String key : map.keySet()) {
            // the 10 is the sensitivity. The highest frequency words and any words that have a frequency within 10 are printed
            if (map.get(key) > Collections.max(map.values()) - 10){
                System.out.println(key + " = " + map.get(key));
            }
        }
    }
    public static void setConn(Connection connparam) {
        conn = connparam;
    }
}
