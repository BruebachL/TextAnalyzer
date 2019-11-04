package com.kalypso.tools.dataStructures;

public class TrieNode {

    TrieNode[] subNodes = new TrieNode[26];
    char value;
    String key;
    private TrieNode parent;

    TrieNode(TrieNode p, char val){
        this.parent = p;
        this.value = val;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String toString(){
        if(parent == null){
            return "";
        }else{
            return parent.toString() + value;
        }
    }

}
