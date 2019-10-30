package com.kalypso.tools.analytics;

import java.util.ArrayList;

public class AnalyticsEngine {

    public ArrayList<Pair> dictionary = new ArrayList<>();
    public ArrayList<String> stringsToAnalyze = new ArrayList<>();
    public ArrayList<String> notFoundWords = new ArrayList<>();
    int verbosityLevel;


    public AnalyticsEngine(int verbosity){
        this.verbosityLevel = verbosity;
    }

    public Float[] analyzeLanguageScore(){
        Float[] languageScores = new Float[dictionary.size()];
        for(Float score : languageScores){
            score = 0.0f;
        }
        languageScores[0] = 0.0f;
        //languageScores[1] = 0.0f;
        long startTime = System.nanoTime()+1;
        for(int i = 0; i<stringsToAnalyze.size();i++){
            boolean found = false;
            String compareAgainst = stringsToAnalyze.get(i);
            for(int j = 0; j<dictionary.size();j++){

                int startingPoint = approximateStartingPoint(compareAgainst,j);

                for(int k = startingPoint; k<dictionary.get(j).words.size();k++){
                    String currentWord = dictionary.get(j).words.get(k);
                    if(currentWord.equals(compareAgainst)&&!found){
                        if(verbosityLevel>1) {
                            System.out.println("We found an " + dictionary.get(j).getDescriptor() + " word: " + currentWord);
                        }
                        languageScores[j] += 1.0f;
                        found = true;
                    }
                    if(currentWord.length()>2&&compareAgainst.length()>2) {
                        if (currentWord.charAt(0) > compareAgainst.charAt(0)) {
                            break;
                        }
                    }
                }
            }
            if(verbosityLevel>0){
                if(i%1500==0){
                    System.out.println(">>> Checked " + i + "/" + stringsToAnalyze.size() + " (" + i/(stringsToAnalyze.size()/100) + "%)" + " words so far <<<");
                    long elapsedTime = ((System.nanoTime()-startTime)/1000000000)/60;
                    if(elapsedTime!=0) {
                        System.out.println(">>> Took " + elapsedTime + " minutes so far. " + i / (elapsedTime * 60) + " (Words per Second) <<<");
                    }
                    if(i!=0&&elapsedTime!=0) {
                        System.out.println(">>> Estimated time remaining: " + (stringsToAnalyze.size() - i) / (i / elapsedTime) + " minutes <<<");
                    }
                    System.out.println(">>> Hits so far: " + (i - notFoundWords.size()) + " Misses so far: " + notFoundWords.size() + " <<<");
                }
            }
            if(verbosityLevel>0&&!found&&!notFoundWords.contains(compareAgainst)){
                //System.out.println("We didn't know what language >" + compareAgainst + "< is.");
                if(compareAgainst.charAt(compareAgainst.length()-1)=='s'&&compareAgainst.length()>4) {
                    compareAgainst = compareAgainst.substring(0,compareAgainst.length()-2);
                   // System.out.println("Trying again with some tweaks...");
                    for (int j = 0; j < dictionary.size(); j++) {
                        for (String currentWord : dictionary.get(j).words) {
                            if (currentWord.equals(compareAgainst) && !found) {
                                if (verbosityLevel > 1) {
                                    System.out.println("We found an " + dictionary.get(j).getDescriptor() + " word: " + currentWord);
                                }
                                languageScores[j] += 1.0f;
                                found = true;
                            }
                            if(currentWord.length()>2&&compareAgainst.length()>2) {
                                if (currentWord.charAt(0) > compareAgainst.charAt(0)) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if(!found&&!notFoundWords.contains(compareAgainst)){
                notFoundWords.add(compareAgainst);
            }
        }
        return languageScores;
    }

    public int approximateStartingPoint(String currentWordToCompare, int currentDictionary){
        boolean rightStartingPoint = false;
        int startingPointApproximation = 0;
        while(!rightStartingPoint){
            if(currentWordToCompare.charAt(0)>dictionary.get(currentDictionary).words.get(startingPointApproximation).charAt(0)){
                startingPointApproximation+=dictionary.get(currentDictionary).words.size()/28;
            }
            if(currentWordToCompare.charAt(0)<=dictionary.get(currentDictionary).words.get(startingPointApproximation).charAt(0)){
                startingPointApproximation-=(dictionary.get(currentDictionary).words.size()/28);
                if(startingPointApproximation<0){
                    startingPointApproximation=1;
                }
                rightStartingPoint=true;
            }
        }
        return startingPointApproximation;
    }

}
