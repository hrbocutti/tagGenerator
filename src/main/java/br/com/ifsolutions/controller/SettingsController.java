package br.com.ifsolutions.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsController {
    public HashMap<String, String> readSettings(){
       String file = "C:\\tagGenerator\\settings.txt";

        HashMap<String, String> settings = new HashMap<>();
        String line;
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(file));
            line = in.readLine();

            while (line != null){
                String[] splited = line.split("=");
                settings.put(splited[0], splited[1]);
                line = in.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return settings;
    }
}
