package com.kalypso.tools.fileOperations;

import com.kalypso.tools.analytics.AnalyticsEngine;
import com.kalypso.tools.analytics.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class Importer {

    AnalyticsEngine aE;

    public Importer(AnalyticsEngine analyticsEngine){
        this.aE = analyticsEngine;
    }

    public void readFileToAnalyze(String fileLocation){
        File readFrom = new File(fileLocation);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(readFrom), StandardCharsets.UTF_8));
            String line;

            while((line = br.readLine()) != null){
                aE.stringsToAnalyze.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFileIntoDictionary(String fileLocation, String language){
        File readFrom = new File(fileLocation);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(readFrom), StandardCharsets.UTF_8));
            String line;
            ArrayList<String> languageDictionary = new ArrayList<>();

            while((line = br.readLine()) != null){
                languageDictionary.add(line);
            }

            aE.dictionary.add(new Pair(language,languageDictionary));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void amendDictionaryFile(String fileLocation, String language){
        File readFrom = new File(fileLocation);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(readFrom), StandardCharsets.UTF_8));
            String line;
            ArrayList<String> languageDictionary = new ArrayList<>();

            while((line = br.readLine()) != null){
                languageDictionary.add(line);
            }
            for(Pair pair : aE.dictionary){
                if(pair.getDescriptor().equals(language)){
                    pair.words.addAll(languageDictionary);
                    Collections.sort(pair.words);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
