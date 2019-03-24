package com.jesperu.rssreader;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    public static final int RESULT_CODE = 0;
    public String NEWSLINK = "news_link";
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button button;
    private int refreshRate;
    private int itemLimit = 5;
    private String inputURL = "http://rss.cnn.com/rss/edition.rss";

    private String strArray[] = readRSS(inputURL).split("\n");  //Lagrer RSS feed i en array, kan splite på \n siden jeg legger inn det etter hver som blir lest i readRSS
    private ArrayList<newsItem> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        button = findViewById(R.id.preferenceButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPreferences();
            }

        });


        for (int i = 0; i < (itemLimit * 2); i+=2){           //Går gjennom array med RSS og skriver ut titler (annenhver, derfor +=2)
            newsList.add(new newsItem(strArray[i]));
        }

       buildRecyclerView();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CODE){
            if (resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                refreshRate = Integer.parseInt(bundle.get(PreferenceActivity.REFRESH).toString());
                itemLimit = Integer.parseInt(bundle.get(PreferenceActivity.LIMIT).toString());
                inputURL = bundle.get(PreferenceActivity.URL).toString();

                for (int i = 0; i < (itemLimit * 2); i+=2){           //Går gjennom array med RSS og skriver ut titler (annenhver, derfor +=2)
                    newsList.add(new newsItem(strArray[i]));
                }

                buildRecyclerView();

            }
        }
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new NewsAdapter(newsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String hardkode = "<![CDATA[Cruise ship evacuating off Norway coast]]>";     //hard koda inn en title fra RSS feed, her må det egentlig sendes med den tittelen brukeren trykker på
                String link = newsItem.getmHeadline();


                for (int i = 0; i < strArray.length; i++){          //går gjennom array og skriver ut link til tittelen brukeren har valgt
                    if (strArray[i].equals(link)){
                        System.out.println(strArray[i+1]);
                        openContent(link);
                    }
                }



            }
        });

    }

    public static String readRSS(String urlAdress){        //Funksjonen som skriver inn title og link fra RSS feed til en string.
        try {
            URL rssUrl = new URL(urlAdress);
            BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String sourceCode ="";
            String line;
            while ((line = in.readLine()) != null) {
                int titleEndIndex = 0;
                int titleStartIndex = 0;
                while (titleStartIndex >= 0) {
                    titleStartIndex = line.indexOf("<title>", titleEndIndex);
                    if (titleStartIndex >= 0) {
                        titleEndIndex = line.indexOf("</title>", titleStartIndex);
                        sourceCode += line.substring(titleStartIndex + "<title>".length(), titleEndIndex) + "\n";
                    }
                    titleStartIndex = line.indexOf("<link>", titleEndIndex);
                    if (titleStartIndex >= 0) {
                        titleEndIndex = line.indexOf("</link>", titleStartIndex);
                        sourceCode += line.substring(titleStartIndex + "<link>".length(), titleEndIndex) + "\n";
                    }
                }
            }
            in.close();
            return sourceCode;
        } catch (MalformedURLException ue){
            System.out.println("Malformed URL");
        } catch (IOException ioe){
            System.out.println("Something went wrong reading the contents");
        }
        return null;
    }

    public void openPreferences() {
        Intent intent = new Intent(this, PreferenceActivity.class);
        startActivityForResult(intent,RESULT_CODE);
    }

    public void openContent(String link){
        Intent intent = new Intent(this, ContentActivity.class);
        Bundle b = new Bundle();

        b.putString(NEWSLINK, link);

        startActivity(intent);
    }

}



