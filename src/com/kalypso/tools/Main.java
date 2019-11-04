package com.kalypso.tools;

import com.kalypso.tools.analytics.AnalyticsEngine;
import com.kalypso.tools.analytics.Pair;
import com.kalypso.tools.dataStructures.Trie;
import com.kalypso.tools.dataStructures.TrieNode;
import com.kalypso.tools.dataStructures.TrieTraveler;
import com.kalypso.tools.fileOperations.Importer;
import com.kalypso.tools.stringOperations.StringCleaner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        AnalyticsEngine engine = new AnalyticsEngine(1);
        Importer importer = new Importer(engine);
        StringCleaner sC = new StringCleaner(engine);
        importer.readFileIntoDictionary("Resources/words_alpha.txt","English");
       // engine.readFileIntoDictionary("D:\\Coding\\german_words.txt","German");
        importer.amendDictionaryFile("Resources/google-10000-english.txt","English");
        importer.readFileToAnalyze("Resources/moby10b.txt");
        sC.cleanRawLines();
        //sC.cleanDictionaries();
        Float[] languageScore = engine.refactorLanguage();
        System.out.println(languageScore[0] + " " + engine.dictionary.get(0).getDescriptor());
        //System.out.println(languageScore[1] + " " + engine.dictionary.get(1).getDescriptor());
        System.out.println(engine.notFoundWords.size() + " (" + (float)(engine.notFoundWords.size()/(engine.stringsToAnalyze.size()/100.0)) + "%)" + " not found.");
        System.out.println("Took " + (float)((TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-engine.startTime))) + " seconds.");
        System.out.println();

        TrieTraveler traveler = new TrieTraveler(engine.dictionary.get(0).words.findStringsLastNode("the"));

        ArrayList<TrieNode> subwords = traveler.findAllSubWords();

        for(TrieNode subword : subwords){
            System.out.println(subword.toString());
        }

        //printNotFoundWords(engine);
    }

    public static void printNotFoundWords(AnalyticsEngine engine){
        int counter = 0;
        Collections.sort(engine.notFoundWords);
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
