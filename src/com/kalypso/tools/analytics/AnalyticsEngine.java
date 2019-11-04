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


    public AnalyticsEngine(int verbosity){
        this.verbosityLevel = verbosity;
    }

    public Float[] refactorLanguage(){
        Float[] languageScores = new Float[dictionary.size()];
        for(int i = 0; i<dictionary.size();i++){
            languageScores[i] = 0.0f;
        }

        startTime = System.nanoTime()+1;
        int i = 0;
        for(String current : stringsToAnalyze){
            for(Pair dict : dictionary){
                Trie toUse = dict.words;
                if(toUse.findStringInTrie(current).equals("notFound")){
                    if(!notFoundWords.contains(current)){
                        notFoundWords.add(current);
                    }
                }else{
                    languageScores[0]++;
                }
            }

            if(verbosityLevel>0){
                i++;
                printProgress(startTime,i);
            }

        }
        for(String notFoundWord : notFoundWords){
            for(Pair dict : dictionary){
                Trie toUse = dict.words;
                if(!toUse.findStringInTrie(notFoundWord.substring(0,notFoundWord.length()-1)).equals("notFound")){
                    notFoundWords.remove(notFoundWord);
                }
            }
        }
        return languageScores;
    }

    private void printProgress(long startTime, int progressMarker){
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
