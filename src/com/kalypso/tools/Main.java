package com.kalypso.tools;

import com.kalypso.tools.analytics.AnalyticsEngine;
import com.kalypso.tools.fileOperations.Importer;
import com.kalypso.tools.stringOperations.StringCleaner;

public class Main {

    public static void main(String[] args) {
        AnalyticsEngine engine = new AnalyticsEngine(1);
        Importer importer = new Importer(engine);
        StringCleaner sC = new StringCleaner(engine);
        importer.readFileIntoDictionary("D:\\Coding\\words_alpha.txt","English");
       // engine.readFileIntoDictionary("D:\\Coding\\german_words.txt","German");
        importer.amendDictionaryFile("D:\\Coding\\google-10000-english.txt","English");
        importer.readFileToAnalyze("D:\\Coding\\moby10b.txt");
        sC.cleanRawLines();
        sC.cleanDictionaries();
        Float[] languageScore = engine.analyzeLanguageScore();
        System.out.println(languageScore[0] + " " + engine.dictionary.get(0).getDescriptor());
        //System.out.println(languageScore[1] + " " + engine.dictionary.get(1).getDescriptor());
        System.out.println(engine.notFoundWords.size() + " (" + engine.notFoundWords.size()/(engine.stringsToAnalyze.size()/100) + "%)" + " not found.");
        System.out.println();
        int counter = 0;
        for(String notFoundWord : engine.notFoundWords){
            System.out.print(notFoundWord + ", ");
            counter++;
            if(counter>10){
                System.out.println();
                counter=0;
            }
        }
    }
}
