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
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        AnalyticsEngine engine = new AnalyticsEngine(1);
        StringCleaner sC = new StringCleaner(engine);
        Importer importer = new Importer(engine);
        importer.readFileIntoDictionary("Resources/words_alpha.txt","English");
        importer.readFileIntoDictionary("Resources/german_words.txt","German");
        importer.amendDictionaryFile("Resources/google-10000-english.txt","English");
        importer.readFileToAnalyze("Resources/bieneMaja.txt");
        sC.cleanRawLines();
        //sC.cleanDictionaries();
        Float[] languageScore = engine.refactorLanguage();
        System.out.println(languageScore[0] + " " + engine.dictionary.get(0).getDescriptor());
        //System.out.println(languageScore[1] + " " + engine.dictionary.get(1).getDescriptor());
        System.out.println(engine.notFoundWords.size() + " (" + (float)(engine.notFoundWords.size()/(engine.stringsToAnalyze.size()/100.0)) + "%)" + " not found.");
        System.out.println("Took " + (float)((TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-engine.startTime))) + " seconds.");
        System.out.println();

        /*TrieTraveler traveler = new TrieTraveler(engine.dictionary.get(0).words.findStringsLastNode("the"));

        ArrayList<TrieNode> subwords = traveler.findAllSubWords();

        for(TrieNode subword : subwords){
            System.out.println(subword.toString());
        }*/

        printPredictedWords(engine);
        printNotFoundWords(engine);
    }

    public static String getUserInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static void printPredictedWords(AnalyticsEngine engine){
        StringBuilder inputSoFar = new StringBuilder();
        while(!((inputSoFar.append(getUserInput()).toString().equals("#")))){
            TrieTraveler traveler = new TrieTraveler(engine.dictionary.get(0).words.findStringsLastNode(inputSoFar.toString()));

        ArrayList<TrieNode> subwords = traveler.findAllSubWords();

        for(int i = 0; (i<10&&i<subwords.size()); i++){
            System.out.println(subwords.get(i).toString());
        }
        }
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
