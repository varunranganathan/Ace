package com.vvvro.acegame;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vvvro on 9/1/2016.
 */
public class UserCardsAdapter extends RecyclerView.Adapter<UserCardsAdapter.ViewHolder> {
    public Hand hand;
    public Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cardName;
        public ImageButton cardImage;

        public ViewHolder(View itemView) {
            super(itemView);
            //cardName = (TextView) itemView.findViewById(R.id.cardName);
            cardImage = (ImageButton) itemView.findViewById(R.id.cardName);
        }
    }

    public UserCardsAdapter(Hand myDataset, Context myContext) {
        hand = myDataset;
        mContext = myContext;
    }

    @Override
    public int getItemCount() {
        return hand.getNumberOfCards();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //holder.cardName.setText(hand.getCard().get(position).getCardName());
            /*String resourceName = "";
            int cardNumber = hand.getCard().get(position).cardNumber;
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
            switch (hand.getCard().get(position).getSuit()) {
                case 1:resourceName+="hearts";
                    break;
                case 2:resourceName+="diamonds";
                    break;
                case 3:resourceName+="clubs";
                    break;
                case 4:resourceName+="spades";
                    break;
            }
            resourceName = "a" + resourceName;*/
        Context context = holder.cardImage.getContext();
        String resourceName = Card.cardResourceNameFromCard(hand.getCard().get(position));
        Log.v("Resource", resourceName);
        int id = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
        holder.cardImage.setImageResource(id);
        holder.cardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PlayGameActivity2.userTurn == 0) {
                    Snackbar.make(view, "It's not your turn yet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Card playedCard = hand.getCard().get(position);
                    int invalid = 0;
                    if (PlayGameActivity2.cardList.isEmpty() == false && PlayGameActivity2.cut != 1) {
                        Card card = PlayGameActivity2.cardList.get(PlayGameActivity2.cardList.size() - 1);
                        if (playedCard.getSuit() != card.getSuit()) {
                            Snackbar.make(view, "Invalid move", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            invalid = 1;
                        }
                    }
                    if (invalid != 1) {
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                        alertDialogBuilder.setMessage("Play the " + hand.getCard().get(position).getCardName() + " ?");
                        alertDialogBuilder.setPositiveButton("PLAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Card playedCard = hand.getCard().get(position);
                                if (PlayGameActivity2.cardList.isEmpty() == true) {
                                    PlayGameActivity2.playerCard = playedCard;
                                    PlayGameActivity2.playerMadeAMove = 1;
                                    PlayGameActivity2.userTurn = 0;
                                    PlayGameActivity2.player[0].hand.removeCard(playedCard);
                                    notifyDataSetChanged();
                                } else {
                                    Card card = PlayGameActivity2.cardList.get(PlayGameActivity2.cardList.size() - 1);
                                    if (PlayGameActivity2.player[0].canPlayerMakeMove(card) == true) {
                                        if (playedCard.suit == card.suit) {
                                            PlayGameActivity2.playerCard = playedCard;
                                            PlayGameActivity2.playerMadeAMove = 1;
                                            PlayGameActivity2.userTurn = 0;
                                            PlayGameActivity2.player[0].hand.removeCard(playedCard);
                                            notifyDataSetChanged();
                                        }
                                    } else {
                                        PlayGameActivity2.playerCard = playedCard;
                                        PlayGameActivity2.playerMadeAMove = 1;
                                        PlayGameActivity2.userTurn = 0;
                                        PlayGameActivity2.player[0].hand.removeCard(playedCard);
                                        notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                        alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }

            }
        });
    }
}
