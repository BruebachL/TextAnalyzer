package com.kalypso.tools.dataStructures;

import java.security.InvalidParameterException;

public class Trie {

    private TrieNode root;
    private int runningInsertionKey = 0;

    public Trie(){
        this.root = new TrieNode(null);
        this.root.setValue("");
    }

    private int convertAsciiToArrayPosition(char asciiChar){
        int arrayPosition = (int)(asciiChar)-97;
        if(arrayPosition<0||arrayPosition>26){
            throw new InvalidParameterException();
        }
        return arrayPosition;
    }

    public void insertStringIntoTrie(String toInsert){
        char[] individualChars = toInsert.toCharArray();
        TrieNode currentNode = root;
        for(char current : individualChars){
            int arrayPosition = convertAsciiToArrayPosition(current);
            if(currentNode.subNodes[arrayPosition]==null){
                currentNode.subNodes[arrayPosition] = new TrieNode(currentNode);
                currentNode = currentNode.subNodes[arrayPosition];
            }else{
                currentNode = currentNode.subNodes[arrayPosition];
            }
        }
            currentNode.value = String.valueOf(runningInsertionKey);
            runningInsertionKey++;
    }

    public String findStringInTrie(String toFind){
        char[] individualChars = toFind.toCharArray();
        TrieNode currentNode = root;
        for (char current : individualChars){
            int arrayPosition = convertAsciiToArrayPosition(current);
            if(currentNode.subNodes[arrayPosition]==null){
                return "notFound";
            }else{
                currentNode = currentNode.subNodes[arrayPosition];
            }
        }
        if(currentNode.value==null){
            return "notFound";
        }
        return currentNode.value;

    }

    public TrieNode findStringsLastNode(String toFind){
        char[] individualChars = toFind.toCharArray();
        TrieNode currentNode = root;
        for (char current : individualChars){
            int arrayPosition = convertAsciiToArrayPosition(current);
            if(currentNode.subNodes[arrayPosition]==null){
                return null;
            }else{
                currentNode = currentNode.subNodes[arrayPosition];
            }
        }
        return currentNode;
    }

}
