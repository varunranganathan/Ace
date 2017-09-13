package com.vvvro.acegame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public PlayGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayGameFragment newInstance(String param1, String param2) {
        PlayGameFragment fragment = new PlayGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_play_game, container, false);
        Button nextButton = (Button) rootView.findViewById(R.id.nextButton);
        final TextView gameStatus = (TextView) rootView.findViewById(R.id.gameStatus);
        final TextView topOfDeck = (TextView) rootView.findViewById(R.id.topOfDeck);
        final ImageView topOfDeckImage = (ImageView) rootView.findViewById(R.id.topOfDeckImage);
       // final Vibrator v = (Vibrator) rootView.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        final Context context = topOfDeckImage.getContext();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PlayGameActivity2.winners.size() == PlayGameActivity2.numPlayers - 1) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(rootView.getContext());
                    String message = "";
                    if(PlayGameActivity2.winners.contains(0)==false) message = "Better luck next game!";
                    else message = "Thank you for playing!";
                    message +=" The winners are ";
                    for(int i =0;i<PlayGameActivity2.winners.size();i++) {
                        int n = PlayGameActivity2.winners.get(i);
                        if(n==0) message+="you";
                        else message+="Player "+String.valueOf(n);
                        if(i==PlayGameActivity2.winners.size()-2){
                            message+=" and ";
                        }else if(i<PlayGameActivity2.winners.size()-2){
                            message+=", ";
                        }
                    }
                    message+="!";
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(rootView.getContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    if (PlayGameActivity2.userTurn == 0 && PlayGameActivity2.playerMadeAMove == 0) {
                        if (PlayGameActivity2.cardList.isEmpty() == true) {
                            if (PlayGameActivity2.turn == 0) {
                                //User makes first play
                                //To-Do Check if Player is out of cards
                                PlayGameActivity2.userTurn = 1;
                                PlayGameActivity2.playerMadeAMove = 0; //added //Was PlayGameActivity
                                gameStatus.setText("Your Turn!");

                                //Added from here
                                if (PlayGameActivity2.player[0].hand.cards.size() == 0) {
                                    PlayGameActivity2.userTurn = 0;
                                    PlayGameActivity2.playerMadeAMove = 0; //Was Play Game Activity
                                    gameStatus.setText("");
                                    PlayGameActivity2.numMoves++;
                                    if (PlayGameActivity2.numMoves == PlayGameActivity2.numPlayers) {
                                        PlayGameActivity2.numMoves = 0;
                                        PlayGameActivity2.cardList.clear();
                                        topOfDeck.setText("The round is over. The Deck is empty");
                                        //if(v.hasVibrator()) v.vibrate(500);
                                        topOfDeckImage.setVisibility(View.INVISIBLE);
                                        PlayGameActivity2.turn = PlayGameActivity2.highestCardPlayer;
                                    } else {
                                        int i =0;
                                        do {
                                            if (PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1)
                                                PlayGameActivity2.turn = 0;
                                            else PlayGameActivity2.turn++;
                                            if(i!=0) PlayGameActivity2.numMoves++;
                                            i++;
                                        }while(PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.size()==0) ;
                                    }
                                    onClick(view); //Recursion
                                }
                                //Till here
                                //Increment turn
                            } else {
                                PlayGameActivity2.userTurn = 0;
                                if (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.size() == 0) {
                                    PlayGameActivity2.numMoves++;
                                    int i= 0;
                                    do {
                                        if (PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1)
                                            PlayGameActivity2.turn = 0;
                                        else PlayGameActivity2.turn++;
                                        if(i!=0) PlayGameActivity2.numMoves++;
                                        i++;
                                    }while (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.size()==0);
                                /*if(PlayGameActivity2.numMoves==PlayGameActivity2.numPlayers) {
                                    PlayGameActivity2.numMoves = 0;
                                    PlayGameActivity2.cardList.clear();
                                    topOfDeck.setText("Round is over.Deck is Empty");
                                    PlayGameActivity2.turn = PlayGameActivity2.highestCardPlayer;
                                }else {
                                    if (PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1)
                                        PlayGameActivity2.turn = 0;
                                    else PlayGameActivity2.turn++;
                                }*/
                                } else {
                                    //gameStatus.setText("Player " + String.valueOf(turn) + "'s Turn!");
                                    Card card = PlayGameActivity2.player[PlayGameActivity2.turn].chooseStartCard();
                                    PlayGameActivity2.cardList.add(card);
                                    //Added from here
                                    if (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.isEmpty() == true) {
                                        if (PlayGameActivity2.winners.contains(PlayGameActivity2.turn) == false) {
                                            PlayGameActivity2.winners.add(PlayGameActivity2.turn);
                                        }
                                    }
                                    //Till here
                                    PlayGameActivity2.highestCard = card;
                                    PlayGameActivity2.highestCardPlayer = PlayGameActivity2.turn;
                                    gameStatus.setText("Player " + String.valueOf(PlayGameActivity2.turn) + " played the " + card.getCardName());
                                    topOfDeck.setText(card.getCardName());
                                    String resource = Card.cardResourceNameFromCard(card);
                                    int id = context.getResources().getIdentifier(resource,"drawable",context.getPackageName());
                                    topOfDeckImage.setImageResource(id);
                                    topOfDeckImage.setVisibility(View.VISIBLE);
                                    PlayGameActivity2.numMoves++;
                                    int i=0;
                                    do {
                                        if (PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1)
                                            PlayGameActivity2.turn = 0;
                                        else PlayGameActivity2.turn++;
                                        if(i!=0) PlayGameActivity2.numMoves++;
                                        i++;
                                    }while(PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.size()==0);
                                /*if(PlayGameActivity2.numMoves==PlayGameActivity2.numPlayers) {
                                    PlayGameActivity2.numMoves = 0;
                                    PlayGameActivity2.cardList.clear();
                                    topOfDeck.setText("Round is over. Deck is Empty");
                                    PlayGameActivity2.turn = PlayGameActivity2.highestCardPlayer;
                                }else {
                                    if (PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1)
                                        PlayGameActivity2.turn = 0;
                                    else PlayGameActivity2.turn++;
                                }*/
                                }
                            }
                        } else {
                            if (PlayGameActivity2.turn == 0) {
                                //To-Do Check if Player is out of cards
                                PlayGameActivity2.cut = 0;
                                if (PlayGameActivity2.player[0].canPlayerMakeMove(PlayGameActivity2.cardList.get(PlayGameActivity2.cardList.size() - 1)) == false) {
                                    Log.v("LOGTAG1", "You can give cut to " + String.valueOf(PlayGameActivity2.highestCardPlayer) + " who played the " + String.valueOf(PlayGameActivity2.highestCard.getCardName()));
                                    PlayGameActivity2.cut = 1;
                                }
                                PlayGameActivity2.userTurn = 1;
                                PlayGameActivity2.playerMadeAMove = 0;//Added
                                gameStatus.setText("Your Turn!");

                                //Added from here
                                if (PlayGameActivity2.player[0].hand.cards.size() == 0) {
                                    PlayGameActivity2.cut = 0;
                                    PlayGameActivity2.userTurn = 0;
                                    PlayGameActivity2.playerMadeAMove = 0; //Was play game activity
                                    gameStatus.setText("");
                                    PlayGameActivity2.numMoves++;
                                    if (PlayGameActivity2.numMoves == PlayGameActivity2.numPlayers) {
                                        PlayGameActivity2.numMoves = 0;
                                        PlayGameActivity2.cardList.clear();
                                        topOfDeck.setText("The round is over. The Deck is empty");
                                        topOfDeckImage.setVisibility(View.INVISIBLE);
                                       // if(v.hasVibrator()) v.vibrate(500);
                                        PlayGameActivity2.turn = PlayGameActivity2.highestCardPlayer;
                                    } else {
                                        int i =0;
                                        do {
                                            if (PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1)
                                                PlayGameActivity2.turn = 0;
                                            else PlayGameActivity2.turn++;
                                            if(i!=0) PlayGameActivity2.numMoves++;
                                            i++;
                                        }while (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.size()==0);
                                    }
                                    onClick(view);
                                }
                                //Till here
                                //turn++;
                            } else {
                                PlayGameActivity2.userTurn = 0;
                                if (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.size() == 0) {
                                    PlayGameActivity2.numMoves++;
                                    if (PlayGameActivity2.numMoves == PlayGameActivity2.numPlayers) {
                                        PlayGameActivity2.numMoves = 0;
                                        PlayGameActivity2.cardList.clear();
                                        topOfDeck.setText("The round is over. The Deck is empty");
                                        topOfDeckImage.setVisibility(View.INVISIBLE);
                                        //if(v.hasVibrator()) v.vibrate(500);
                                        PlayGameActivity2.turn = PlayGameActivity2.highestCardPlayer;
                                    } else {
                                        int i =0;
                                        do {
                                            if (PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1)
                                                PlayGameActivity2.turn = 0;
                                            else PlayGameActivity2.turn++;
                                            if(i!=0) PlayGameActivity2.numMoves++;
                                            i++;
                                        }while (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.size()==0);
                                    }
                                } else {
                                    //gameStatus.setText("Player " + String.valueOf(turn) + "'s Turn!");
                                    PlayGameActivity2.cut = 0;
                                    if (PlayGameActivity2.player[PlayGameActivity2.turn].canPlayerMakeMove(PlayGameActivity2.cardList.get(PlayGameActivity2.cardList.size() - 1)) == false) {
                                        Log.v("LOGTAG1", String.valueOf(PlayGameActivity2.turn) + " has to cut " + String.valueOf(PlayGameActivity2.highestCardPlayer) + " who played the " + String.valueOf(PlayGameActivity2.highestCard.getCardName()));
                                        PlayGameActivity2.cut = 1;
                                    }
                                    Card card = PlayGameActivity2.player[PlayGameActivity2.turn].makeMove(PlayGameActivity2.cardList.get(PlayGameActivity2.cardList.size() - 1));
                                    Log.v("Previous Top", PlayGameActivity2.cardList.get(PlayGameActivity2.cardList.size() - 1).getCardName());
                                    PlayGameActivity2.cardList.add(card);
                                    //Added from here
                                    if (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.isEmpty() == true) {
                                        if (PlayGameActivity2.winners.contains(PlayGameActivity2.turn) == false) {
                                            PlayGameActivity2.winners.add(PlayGameActivity2.turn);
                                            Snackbar.make(view, "Player "+String.valueOf(PlayGameActivity2.turn)+" is done", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    }
                                    //Till here
                                    Log.v("New Top", card.getCardName());
                                    topOfDeck.setText(card.getCardName());
                                    String resource = Card.cardResourceNameFromCard(card);
                                    int id = context.getResources().getIdentifier(resource,"drawable",context.getPackageName());
                                    topOfDeckImage.setImageResource(id);
                                    topOfDeckImage.setVisibility(View.VISIBLE);
                                    if (PlayGameActivity2.cut == 0) { //Added
                                        if (Card.compare(card, PlayGameActivity2.highestCard) == 1) {
                                            PlayGameActivity2.highestCard = card;
                                            PlayGameActivity2.highestCardPlayer = PlayGameActivity2.turn;
                                        }
                                    }
                                    if (PlayGameActivity2.cut == 1) {
                                        if(PlayGameActivity2.highestCardPlayer==0) {
                                            gameStatus.setText("Player " + String.valueOf(PlayGameActivity2.turn) + " cut you with the " + card.getCardName() + "." + " You received " + PlayGameActivity2.cardList.size() + " cards.");
                                            PlayGameActivity2.anticipatingWin = 0;
                                        }else{
                                            gameStatus.setText("Player " + String.valueOf(PlayGameActivity2.turn) + " cut player " + String.valueOf(PlayGameActivity2.highestCardPlayer) + " with the " + card.getCardName() + "." + " Player " + String.valueOf(PlayGameActivity2.highestCardPlayer) + " received " + PlayGameActivity2.cardList.size() + " cards.");
                                            //Player Won
                                            //Added here 3
                                            if(PlayGameActivity2.anticipatingWin==1) {
                                                PlayGameActivity2.anticipatingWin = 0;
                                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(rootView.getContext());
                                                if (PlayGameActivity2.winners.isEmpty())
                                                    alertDialogBuilder.setMessage("Congratulations! You won! Continue watching?");
                                                else
                                                    alertDialogBuilder.setMessage("Congratulations! You came " + String.valueOf(PlayGameActivity2.winners.size()) + "! Continue watching?");
                                                alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });
                                                alertDialogBuilder.setNegativeButton("QUIT", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                                AlertDialog alertDialog = alertDialogBuilder.create();
                                                alertDialog.show();
                                            }
                                            //Added here 3
                                        }
                                        PlayGameActivity2.player[PlayGameActivity2.highestCardPlayer].addCards(PlayGameActivity2.cardList);
                                        if (PlayGameActivity2.highestCardPlayer == 0)
                                            UserCardsFragment.adapter.notifyDataSetChanged();
                                        PlayGameActivity2.cardList.clear();
                                        topOfDeck.setText("Deck is Empty");
                                        topOfDeckImage.setVisibility(View.INVISIBLE);
                                       // if(v.hasVibrator()) v.vibrate(500);
                                        PlayGameActivity2.numMoves = 0;
                                        PlayGameActivity2.turn = PlayGameActivity2.highestCardPlayer;
                                        PlayGameActivity2.cut = 0;
                                    } else {
                                        gameStatus.setText("Player " + String.valueOf(PlayGameActivity2.turn) + " played the " + card.getCardName());
                                        topOfDeck.setText(card.getCardName());
                                        resource = Card.cardResourceNameFromCard(card);
                                        id = context.getResources().getIdentifier(resource,"drawable",context.getPackageName());
                                        topOfDeckImage.setImageResource(id);
                                        topOfDeckImage.setVisibility(View.VISIBLE);
                                        PlayGameActivity2.numMoves++;
                                        if (PlayGameActivity2.numMoves == PlayGameActivity2.numPlayers) {
                                            PlayGameActivity2.numMoves = 0;
                                            PlayGameActivity2.cardList.clear();
                                            topOfDeck.setText("The round is over. The Deck is empty");
                                            //topOfDeckImage.setVisibility(View.INVISIBLE); //Removed 4
                                           // if(v.hasVibrator()) v.vibrate(500);
                                            //Player Won
                                            //Added here 3
                                            if(PlayGameActivity2.anticipatingWin==1) {
                                                PlayGameActivity2.anticipatingWin = 0;
                                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(rootView.getContext());
                                                if (PlayGameActivity2.winners.isEmpty())
                                                    alertDialogBuilder.setMessage("Congratulations! You won! Continue watching?");
                                                else
                                                    alertDialogBuilder.setMessage("Congratulations! You came " + String.valueOf(PlayGameActivity2.winners.size()) + "! Continue watching?");
                                                alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });
                                                alertDialogBuilder.setNegativeButton("QUIT", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                                AlertDialog alertDialog = alertDialogBuilder.create();
                                                alertDialog.show();
                                            }
                                            //Added till here 3
                                            PlayGameActivity2.turn = PlayGameActivity2.highestCardPlayer;
                                        } else {
                                            int i=0;
                                            do {
                                                if (PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1)
                                                    PlayGameActivity2.turn = 0;
                                                else PlayGameActivity2.turn++;
                                                if(i!=0) PlayGameActivity2.numMoves++;
                                                i++;
                                            }while (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.size()==0);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (PlayGameActivity2.playerMadeAMove == 1) {
                        if (PlayGameActivity2.cut == 1) {
                            PlayGameActivity2.cardList.add(PlayGameActivity2.playerCard);
                            //Added from here
                            if (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.isEmpty() == true) {
                                if (PlayGameActivity2.winners.contains(PlayGameActivity2.turn) == false) {
                                    PlayGameActivity2.winners.add(PlayGameActivity2.turn);
                                    Snackbar.make(view, "Player "+String.valueOf(PlayGameActivity2.turn)+" is done", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                                //Player Won
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(rootView.getContext());
                                if(PlayGameActivity2.winners.isEmpty()) alertDialogBuilder.setMessage("Congratulations! You won! Continue watching?");
                                else alertDialogBuilder.setMessage("Congratulations! You came "+String.valueOf(PlayGameActivity2.winners.size())+"! Continue watching?");
                                alertDialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alertDialogBuilder.setNegativeButton("QUIT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(rootView.getContext(),MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                            //Till here
                            gameStatus.setText("You cut player " + String.valueOf(PlayGameActivity2.highestCardPlayer) + " with the " + PlayGameActivity2.playerCard.getCardName() + "." + " Player " + String.valueOf(PlayGameActivity2.highestCardPlayer) + " received " + PlayGameActivity2.cardList.size() + " cards");
                            PlayGameActivity2.player[PlayGameActivity2.highestCardPlayer].addCards(PlayGameActivity2.cardList);
                            PlayGameActivity2.cardList.clear();
                            topOfDeck.setText("The Deck is empty");
                            topOfDeckImage.setVisibility(View.INVISIBLE);
                            //if(v.hasVibrator()) v.vibrate(500);
                            PlayGameActivity2.numMoves = 0;
                            PlayGameActivity2.turn = PlayGameActivity2.highestCardPlayer;
                        /*if(PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1) PlayGameActivity2.turn = 0;
                        else PlayGameActivity2.turn++;*/
                            PlayGameActivity2.cut = 0;

                        } else {
                            if (PlayGameActivity2.cardList.isEmpty() == true) {
                                PlayGameActivity2.highestCard = PlayGameActivity2.playerCard;
                                PlayGameActivity2.highestCardPlayer = PlayGameActivity2.turn;
                            } else {
                                if (Card.compare(PlayGameActivity2.playerCard, PlayGameActivity2.highestCard) == 1) {
                                    PlayGameActivity2.highestCard = PlayGameActivity2.playerCard;
                                    PlayGameActivity2.highestCardPlayer = PlayGameActivity2.turn;
                                }
                            }
                            PlayGameActivity2.cardList.add(PlayGameActivity2.playerCard);
                            //Added from here
                            if (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.isEmpty() == true) {
                                if (PlayGameActivity2.winners.contains(PlayGameActivity2.turn) == false) {
                                    PlayGameActivity2.winners.add(PlayGameActivity2.turn);
                                    Snackbar.make(view, "Player "+String.valueOf(PlayGameActivity2.turn)+" is done", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                            //Till here
                            //Added from here 2
                            if (PlayGameActivity2.player[0].hand.cards.isEmpty() == true) {
                                PlayGameActivity2.anticipatingWin = 1;
                            }
                            //Added till here 2
                            gameStatus.setText("You played the " + PlayGameActivity2.playerCard.getCardName());
                            topOfDeck.setText(PlayGameActivity2.playerCard.getCardName());
                            String resource = Card.cardResourceNameFromCard(PlayGameActivity2.playerCard);
                            int id = context.getResources().getIdentifier(resource,"drawable",context.getPackageName());
                            topOfDeckImage.setImageResource(id);
                            topOfDeckImage.setVisibility(View.VISIBLE);
                            PlayGameActivity2.numMoves++;
                            if (PlayGameActivity2.numMoves == PlayGameActivity2.numPlayers) {
                                PlayGameActivity2.numMoves = 0;
                                PlayGameActivity2.cardList.clear();
                                topOfDeck.setText("The round is over. The Deck is empty");
                                //topOfDeckImage.setVisibility(View.INVISIBLE); //Removed 4
                                //if(v.hasVibrator()) v.vibrate(500);
                                PlayGameActivity2.turn = PlayGameActivity2.highestCardPlayer;
                            } else {
                                int i =0;
                                do {
                                    if (PlayGameActivity2.turn == PlayGameActivity2.numPlayers - 1)
                                        PlayGameActivity2.turn = 0;
                                    else PlayGameActivity2.turn++;
                                    if(i!=0) PlayGameActivity2.numMoves++;
                                    i++;
                                }while (PlayGameActivity2.player[PlayGameActivity2.turn].hand.cards.size()==0);
                            }
                        }
                        PlayGameActivity2.userTurn = 0;
                        PlayGameActivity2.playerMadeAMove = 0;
                    }else {
                        if(PlayGameActivity2.userTurn==1){
                            Snackbar.make(view, "Please select a card", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                    Log.v("NUM1", String.valueOf(PlayGameActivity2.player[1].getCards().cards.size()));
                    Log.v("NUM2", String.valueOf(PlayGameActivity2.player[2].getCards().cards.size()));
                    Log.v("NUM3", String.valueOf(PlayGameActivity2.player[3].getCards().cards.size()));
                    Log.v("NUMYOU", String.valueOf(PlayGameActivity2.player[0].getCards().cards.size()));
                }
            }

        });

        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
