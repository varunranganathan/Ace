package com.vvvro.acegame;

/**
 * Created by vvvro on 8/18/2016.
 */
public class Card {
    public int cardNumber; //This varies from 2 - 14
    public int suit; //This varies from 1 to 4
    public Card(){

    }
    public Card(int cardNumber,int suit){
        this.cardNumber = cardNumber;
        this.suit = suit;
    }
    public void setCardNumber(int cardNumber){
        this.cardNumber = cardNumber;
    }
    public void setSuit(int suit){
        this.suit = suit;
    }
    public int getSuit(){
        return suit;
    }
    public String getCardName(){
        String nameSuit;
        if(suit==1) nameSuit = "Hearts";
        else if(suit == 2) nameSuit = "Diamonds";
        else if(suit == 3) nameSuit = "Clubs";
        else nameSuit = "Spades";
        if(cardNumber == 0) return "Ace "+"of "+nameSuit;
        else if(cardNumber == 11) return "Jack "+ "of "+nameSuit;
        else if(cardNumber == 12) return  "Queen "+ "of "+nameSuit;
        else if(cardNumber == 13) return "King "+"of "+nameSuit;
        else return String.valueOf(cardNumber)+" "+"of "+nameSuit;
    }

    public static Card intToCard(int n){
        Card card = new Card();
        card.setCardNumber(n%14);
        if(n<=14) card.setSuit(1);
        else if(n<=28) card.setSuit(2);
        else if(n<=42) card.setSuit(3);
        else if(n<=56) card.setSuit(4);
        return card;
    }

    public static int compare(Card a,Card b){
        //if a>b returns 1
        if(a.getSuit()==b.getSuit()) {
            if(a.cardNumber==0) return 1;
            else if(b.cardNumber==0) return 0;
            if(a.cardNumber>b.cardNumber) return 1;
            else return 0;
        }else return -1;
    }

    public static String cardResourceNameFromCard(Card card){
        String resourceName = "";
        int cardNumber = card.cardNumber;
        switch (cardNumber) {
            case 0: resourceName+="ace_of_";
                break;
            case 11:resourceName+="jack_of_";
                break;
            case 12:resourceName+="queen_of_";
                break;
            case 13:resourceName+="king_of_";
                break;
            default:resourceName+=String.valueOf(cardNumber)+"_of_";
        }
        switch (card.getSuit()) {
            case 1:resourceName+="hearts";
                break;
            case 2:resourceName+="diamonds";
                break;
            case 3:resourceName+="clubs";
                break;
            case 4:resourceName+="spades";
                break;
        }
        resourceName = "a" + resourceName;
        return resourceName;
    }
}
