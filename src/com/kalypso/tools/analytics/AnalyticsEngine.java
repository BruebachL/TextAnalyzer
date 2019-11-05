package com.kalypso.tools.analytics;

import com.kalypso.tools.dataStructures.Trie;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnalyticsEngine {

    public ArrayList<Pair> dictionary = new ArrayList<>();
    public ArrayList<String> stringsToAnalyze = new ArrayList<>();
    public CopyOnWriteArrayList<String> notFoundWords = new CopyOnWriteArrayList<>();
    private int verbosityLevel;
    public long startTime;
    private Float[] languageScores;


    public AnalyticsEngine(int verbosity){
        this.verbosityLevel = verbosity;
    }

    public Float[] refactorLanguage(){

        //initialize the Array

        languageScores = new Float[dictionary.size()];
        for(int i = 0; i<dictionary.size();i++){
            languageScores[i] = 0.0f;
        }

        // initialize the start time

        startTime = System.nanoTime()+1;

        // keep track of how many Strings we've analyzed so far
        int i = 0;

        for(String current : stringsToAnalyze){

        searchStringInAllDictionaries(current);

            if(verbosityLevel>0){
                i++;
                printProgress(i);
            }
        }

        tryAgainWithTweaks();

        return languageScores;
    }

    private void searchStringInAllDictionaries(String toSearch){
        int i = 0;
        for(Pair dict : dictionary){
            Trie toUse = dict.words;
            if(toUse.findStringInTrie(toSearch).equals("notFound")){
                if(!notFoundWords.contains(toSearch)){
                    notFoundWords.add(toSearch);
                }
            }else{
                languageScores[i]++;
            }
            i++;
        }
    }

    private void tryAgainWithTweaks(){
        for(String notFoundWord : notFoundWords){
            for(Pair dict : dictionary){
                Trie toUse = dict.words;
                if(!toUse.findStringInTrie(notFoundWord.substring(0,notFoundWord.length()-1)).equals("notFound")){
                    notFoundWords.remove(notFoundWord);
                }
            }
        }
    }

    private void printProgress(int progressMarker){
        if(progressMarker%(stringsToAnalyze.size()/100*5)==0){
            System.out.println(">>> Checked " + progressMarker + "/" + stringsToAnalyze.size() + " (" + progressMarker/(stringsToAnalyze.size()/100) + "%)" + " words so far <<<");
            long elapsedTime = ((System.nanoTime()-startTime)/1000000000)/60;
            if(elapsedTime!=0) {
                System.out.println(">>> Took " + elapsedTime + " minutes so far. " + progressMarker / (elapsedTime * 60) + " (Words per Second) <<<");
            }
            if(progressMarker!=0&&elapsedTime!=0) {
                System.out.println(">>> Estimated time remaining: " + (stringsToAnalyze.size() - progressMarker) / (progressMarker / elapsedTime) + " minutes <<<");
            }
            System.out.println(">>> Hits so far: " + (progressMarker - notFoundWords.size()) + " Misses so far: " + notFoundWords.size() + " <<<");
        }
    }

}
