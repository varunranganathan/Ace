package com.vvvro.acegame;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by vvvro on 8/18/2016.
 */
public class Hand {
    public ArrayList<Card> cards = new ArrayList<Card>();
    public Hand(){

    }
    public void addCard(Card card){
        cards.add(card);
    }
    public void removeCard(Card card){
        cards.remove(card);
    }
    public void displayCards(){
        for(int i=0;i<cards.size();i++) {
            Log.v("CARD",cards.get(i).getCardName());
        }
    }
    public ArrayList<Card> getCard(){
        return cards;
    }
    public Card playCard(Card card){
        if(cards.contains(card)==false) {

        }else {
            cards.remove(card);
            Log.v("Play","Card Played "+card.getCardName());
        }
        return card;
    }
    public int getNumberOfCards(){
        return cards.size();
    }
}
