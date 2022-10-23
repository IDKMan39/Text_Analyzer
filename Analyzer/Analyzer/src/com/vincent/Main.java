package com.vincent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws Exception  {
        //modifier.addEntry();
       input();
    }
    public static void input() throws Exception {
        String filename = "";
        String text = "";
        dbConnect.connect();
        while (!filename.equals("q")) {

            Scanner in = new Scanner(System.in);

            System.out.println("FileName: ");
            System.out.print(">  ");
            filename = in.nextLine();

            if (new File("C:\\Users\\vjlombardo\\Desktop\\Java\\Text_Data\\ExampleTexts\\" + filename).exists() && !filename.equals("q")) {
                Entry entry = new Entry();
                Reader x = new Reader(entry);
                SqlMod modifier = new SqlMod(entry);

                while (!text.equals("q")) {
                    {
                        System.out.print("Read. Test. ClearSql. Done. Spec. >  ");
                        text = in.nextLine().toLowerCase();

                        if (text.equals("read")) {
                            x.read(filename);
                            modifier.scanWords();

                        } else if (text.equals("test")) {
                            x.read(filename);
                            modifier.addEntry();
                            modifier.scanWords();
                            modifier.addMap();
                            modifier.addInfo();

                        } else if (text.equals("clear")) {
                            modifier.cleardb();
                        } else if (text.equals("spec")){
                            ;
                        }

                        if (text.equals("done")) {
                            break;
                        }
                        System.out.println(entry);

                    }
                }
            }
        }
    }
}
// C:\Users\vjlombardo\Desktop\Java\Text_Data\Analyzer\Analyzer