package com.kalypso.tools.analytics;

import com.kalypso.tools.dataStructures.Trie;

import java.util.ArrayList;

public class Pair {
    String descriptor;
    public Trie words = new Trie();

    public Pair(String desc, Trie words){
        this.descriptor=desc;
        this.words=words;
    }

    public String getDescriptor(){
        return descriptor;
    }

}
