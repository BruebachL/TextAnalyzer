package com.kalypso.tools.dataStructures;

public class TrieNode {

    TrieNode[] subNodes = new TrieNode[26];
    String value;
    private TrieNode parent;

    TrieNode(TrieNode p){
        this.parent = p;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        if(parent == null){
            return "";
        }else{
            return parent.toString() + value;
        }
    }
}
