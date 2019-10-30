package com.kalypso.tools.analytics;

import java.util.ArrayList;

public class Pair {
    String descriptor;
    public ArrayList<String> words = new ArrayList<>();

    public Pair(String desc, ArrayList<String> words){
        this.descriptor=desc;
        this.words=words;
    }

    public String getDescriptor(){
        return descriptor;
    }

}
