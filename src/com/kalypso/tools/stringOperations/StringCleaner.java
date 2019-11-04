package com.kalypso.tools.stringOperations;

import com.kalypso.tools.analytics.AnalyticsEngine;
import com.kalypso.tools.analytics.Pair;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class StringCleaner {

    private AnalyticsEngine aE;

    public StringCleaner(AnalyticsEngine analyticsEngine){
        this.aE = analyticsEngine;
    }

/*    public void cleanDictionaries(){
        for(Pair pair : aE.dictionary){
            ArrayList<String> cleanedWords = new ArrayList<>();
            for(String dictionaryWord : pair.words){
                String cleanWord = replaceUmlaute(dictionaryWord);
                if(cleanWord!=null) {
                    cleanedWords.add(cleanWord.toLowerCase());
                }
            }
            pair.words = cleanedWords;
        }
    }*/

    public void cleanRawLines(){
        aE.stringsToAnalyze = splitRawLines(aE.stringsToAnalyze);
        replaceUmlaute(aE.stringsToAnalyze);
        aE.stringsToAnalyze = removeSpecialCharacters(aE.stringsToAnalyze);
        aE.stringsToAnalyze = toLowerCase(aE.stringsToAnalyze);
    }

    private ArrayList<String> splitRawLines(ArrayList<String> rawLines){
        ArrayList<String> splitList = new ArrayList<>();
        for(String rawLine : rawLines){
            String[] subStrings = rawLine.split("\\s+");
            splitList.addAll(Arrays.asList(subStrings));
        }
        return splitList;
    }

    //Hard removes ALL special characters via a negative mask

    private ArrayList<String> removeSpecialCharacters(ArrayList<String> splitList){
        ArrayList<String> cleanedList = new ArrayList<>();
        Pattern regex = Pattern.compile("[^A-Za-z]");
        for(String splitWord : splitList){
            splitWord = replaceUmlaute(splitWord);
            if(splitWord.contains("-")) {
                String[] splitWords = splitWord.split("-");
                for (String split : splitWords) {
                    split = split.replaceAll(String.valueOf(regex), "");
                    if (split.length() > 0) {
                        cleanedList.add(split);
                    }
                }
                cleanedList.remove(splitWord);
            }else if(splitWord.contains("/")){
                    String[] splitWords = splitWord.split("/");
                    for(String split : splitWords){
                        split = split.replaceAll(String.valueOf(regex),"");
                        if(split.length()>0) {
                            cleanedList.add(split);
                        }
                    }
                    cleanedList.remove(splitWord);
            }else {
                splitWord = splitWord.replaceAll(String.valueOf(regex), "");
                if(splitWord.length()>0) {
                    cleanedList.add(splitWord);
                }
            }
        }
        return cleanedList;
    }

    private ArrayList<String> toLowerCase(ArrayList<String> mixedCaseList){
        ArrayList<String> lowerCaseList = new ArrayList<>();
        for(String word : mixedCaseList){
            lowerCaseList.add(word.toLowerCase());
        }
        return lowerCaseList;
    }

    private static String[][] UMLAUT_REPLACEMENTS = { { "Ä", "Ae" }, { "Ü", "Ue" }, { "Ö", "Oe" }, { "ä", "ae" }, { "ü", "ue" }, { "ö", "oe" }, { "ß", "ss" } };
    private  String replaceUmlaute(String orig) {
        String result = orig;

        result = Normalizer.normalize(result, Normalizer.Form.NFKC);

        for (String[] umlautReplacement : UMLAUT_REPLACEMENTS) {
            result = result.replaceAll(umlautReplacement[0], umlautReplacement[1]);
        }

        return result;
    }

    private ArrayList<String> replaceUmlaute(ArrayList<String> orig) {

        for(String current : orig) {
            current = Normalizer.normalize(current, Normalizer.Form.NFKC);
            for (String[] umlautReplacement : UMLAUT_REPLACEMENTS) {
                current = current.replaceAll(umlautReplacement[0], umlautReplacement[1]);
            }
        }

        return orig;
    }

}
