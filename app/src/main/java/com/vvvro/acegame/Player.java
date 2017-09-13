package com.vvvro.acegame;

import java.util.ArrayList;

/**
 * Created by vvvro on 8/18/2016.
 */
public class Player {
    public String name;
    public Hand hand;
    public Player(){
        hand = new Hand();
    }
    public Player(String a){
        name = a;
        hand = new Hand();
    }
    public void setName(String a){
        name = a;
    }
    public void addCard(Card card){
        hand.addCard(card);
    }
    public void addCards(ArrayList<Card> cards){
        for(int i=0;i<cards.size();i++) {
            addCard(cards.get(i));
        }
    }
    public void removeCard(Card card){
        hand.removeCard(card);
    }
    public void getCards(ArrayList<Card> cards){
        for(int i =0;i<cards.size();i++) {
            addCard(cards.get(i));
        }
    }
    public String getName(){
        return name;
    }
    public Hand getCards(){
        return hand;
    }
    public int getNumberOfCards(){
        return hand.getNumberOfCards();
    }
    public void displayCards(){
        hand.displayCards();
    }
    public Card makeMove(Card top){
        int suit = top.getSuit();
        int flag = 0;
        Card card = new Card();
        for(int i = 0;i<hand.cards.size();i++) {
            if(hand.cards.get(i).getSuit()==suit) {
                card = hand.cards.get(i);
                hand.playCard(card);
                flag = 1;
                break;
            }
        }
        if(flag != 1) {
            card = hand.cards.get(0);
            hand.playCard(card);
        }
        return card;
    }
    public Boolean canPlayerMakeMove(Card top){
        int suit = top.getSuit();
        int flag = 0;
        Card card = new Card();
        for(int i=0;i<hand.cards.size();i++) {
            if(hand.cards.get(i).getSuit()==suit) flag = 1;
        }
        if(flag == 1) return true;
        else return false;
    }
    public Card chooseStartCard(){
        Card card = hand.cards.get(0);
        hand.playCard(card);
        return card; //To-Do: Randomize or use smart algorithm
    }
}
