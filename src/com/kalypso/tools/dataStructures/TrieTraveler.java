package com.kalypso.tools.dataStructures;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class TrieTraveler {

    TrieNode startNode;
    Deque<TrieNode> toTraverse = new LinkedList<TrieNode>();
    ArrayList<TrieNode> traversed;


    public TrieTraveler(TrieNode start){
        this.startNode= start;
    }

    public ArrayList<TrieNode> findAllSubWords(){
        ArrayList<TrieNode> hits = new ArrayList<>();
        toTraverse.push(startNode);
        while(!toTraverse.isEmpty()) {
            TrieNode currentTraversed = toTraverse.pop();
            for(TrieNode child : currentTraversed.subNodes) {
                if (child != null) {
                    toTraverse.add(child);

                    if (child.key != null) {
                        hits.add(child);
                    }
                }
            }
        }
        return hits;
    }



}
