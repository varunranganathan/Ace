package com.vvvro.acegame;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayGameActivity2 extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static int numPlayers = 6;
    public String playerName = "Player";
    public static Player[] player = new Player[8]; //Was numplayers, not static
    public static int turn;
    public static int userTurn = 0;
    public static int move;
    public static int playerMadeAMove = 0;
    public static Card playerCard;
    public static Card highestCard;
    public static int highestCardPlayer;
    public static ArrayList<Integer> winners;
    public static ArrayList<Card> cardList;
    public static int cut;
    public static int numMoves;
    public static int anticipatingWin;
    public Button nextButton;
    TextView gameStatus;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        userTurn = 0;

        cardList = new ArrayList<Card>();
        winners = new ArrayList<Integer>();
        gameStatus = (TextView) findViewById(R.id.gameStatus);
        for (int i = 0; i < numPlayers; i++) {
            player[i] = new Player();
            if (i == 0) player[i].setName("You");
            else player[i].setName(playerName + " " + String.valueOf(i + 1));
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        PlayGameActivity2.turn = 0;
        PlayGameActivity2.userTurn = 0;
        if(MainActivity.numberOfPlayers==4||MainActivity.numberOfPlayers==6) {
            numPlayers = MainActivity.numberOfPlayers;
        }else {
            numPlayers = 6;
        }
        anticipatingWin = 0;
        for(int i =0;i<player.length;i++) {
            if(player[i]!=null) PlayGameActivity2.player[i].hand.cards.clear();
        }
        winners.clear();
        playerMadeAMove = 0;
        cut = 0;
        numMoves = 0;
        assignCards();
        cardList.clear();
        startGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game_activity2, menu);
        return true;
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        PlayGameActivity2.turn = 0;
        PlayGameActivity2.userTurn = 0;
        PlayGameActivity2.cardList.clear();
        PlayGameActivity2.highestCard = new Card();
        for(int i =0;i<numPlayers;i++) {
            PlayGameActivity2.player[i].hand.cards.clear();
        }
        numPlayers = 4;
        PlayGameActivity2.playerCard = new Card();
        PlayGameActivity2.playerMadeAMove = 0;
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PlayGameActivity2.turn = 0;
        PlayGameActivity2.userTurn = 0;
        PlayGameActivity2.cardList.clear();
        PlayGameActivity2.highestCard = new Card();
        for(int i =0;i<numPlayers;i++) {
            PlayGameActivity2.player[i].hand.cards.clear();
        }
        PlayGameActivity2.numPlayers = 4;
        PlayGameActivity2.playerCard = new Card();
        PlayGameActivity2.playerMadeAMove = 0;
        finish();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void assignCards() {
        int min = 1, max = 56;
        ArrayList<Integer> cardsGenerated = new ArrayList<Integer>();
        int count = 0;
        int playerNumber = 0;
        while (count < 56) {
            int val = min + (int) (Math.random() * ((max - min) + 1));
            if (cardsGenerated.contains(val)) {
            } else {
                if (val % 14 == 1) {
                    cardsGenerated.add(val);
                    count++;
                } else {
                    cardsGenerated.add(val);
                    count++;
                    player[playerNumber].addCard(Card.intToCard(val));
                    if (val == 56) {
                        Log.v("AoS", Card.intToCard(val).getCardName());
                        turn = playerNumber;
                        Log.v("Turn", String.valueOf(turn));
                    }
                    playerNumber++;
                    if (playerNumber == numPlayers) playerNumber = 0;
                }
            }
        }
        displayAllCards();
    }

    static void displayAllCards() {
        for (int i = 0; i < numPlayers; i++) {
            Log.v("Player Name", player[i].getName());
            Log.v("Num Cards", String.valueOf(player[i].getNumberOfCards()));
            player[i].displayCards();
        }
    }

    public void startGame() {
        userTurn = 0;
        move = 0;
        cardList.clear();
        int game = 1;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_play_game_activity2, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            if(position == 0) {
                Fragment fragment = new PlayGameFragment();
                return fragment;
            }else {
                Fragment fragment = new UserCardsFragment();
                return fragment;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "GAME";
                case 1:
                    return "CARDS";
            }
            return null;
        }
    }
}
