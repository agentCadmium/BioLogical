package com.saverchenko.cellandmolecularbiologyqa;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Main activity that appears on app launch
 */
public class MainActivity extends AppCompatActivity {

    //array with the main menu options
    private String menuArray[] = {
            "Choose topics",
            "Multiple choice questions",
            "Short-answer questions",
            "Favorite questions"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listView id
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setTextFilterEnabled(true);




        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.list_item, menuArray){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

                switch (position){
                    //set background color
                    case 0: view.setBackgroundColor(Color.parseColor("#4FA5D4"));
                        break;
                    case 1: view.setBackgroundColor(Color.parseColor("#4B7AB2"));
                        break;
                    case 2: view.setBackgroundColor(Color.parseColor("#5E5395"));
                        break;
                    case 3: view.setBackgroundColor(Color.parseColor("#5D426F"));
                        break;

                }
                return view;
            }
        };
        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);

        //Switch into a new activity for each item in listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent();

                switch (position){
                    case 0: intent.setClass(MainActivity.this, TopicsActivity.class);
                        intent.putExtra("head", position);
                        //launch a new activity
                        startActivity(intent);
                        break;

                    case 1: intent.setClass(MainActivity.this, TopicsActivity.class);
                        intent.putExtra("head", position);
                        //launch a new activity
                        startActivity(intent);
                        break;

                    case 2: intent.setClass(MainActivity.this, TopicsActivity.class);
                        intent.putExtra("head", position);
                        //launch a new activity
                        startActivity(intent);
                        break;

                    case 3:
                        break;
                }

            }
        });

    }


}
