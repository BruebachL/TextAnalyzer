package com.kalypso.tools.dataStructures;

public class TrieNode {

    TrieNode[] subNodes = new TrieNode[26];
    String value;

    public TrieNode(){

    }

    public void setValue(String value) {
        this.value = value;
    }
}
