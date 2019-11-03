package com.kalypso.tools.fileOperations;

import com.kalypso.tools.analytics.AnalyticsEngine;
import com.kalypso.tools.analytics.Pair;
import com.kalypso.tools.dataStructures.Trie;

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
            Trie languageDictionary = new Trie();

            while((line = br.readLine()) != null){
                languageDictionary.insertStringIntoTrie(line.toLowerCase());
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
            Trie languageTrie = null;
            for(Pair dict : aE.dictionary){
                if(dict.getDescriptor().equals(language)){
                    languageTrie = dict.words;
                }
            }

            while((line = br.readLine()) != null){
                languageTrie.insertStringIntoTrie(line.toLowerCase());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
