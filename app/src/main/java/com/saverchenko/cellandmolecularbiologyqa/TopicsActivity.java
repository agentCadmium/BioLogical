package com.saverchenko.cellandmolecularbiologyqa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Activity that displays sequence of short answer questions
 */
public class TopicsActivity extends AppCompatActivity {

    //array with the topic options
    private String topicArray[] = {
            "Lipids and membranes",
            "Protein translation and folding",
            "Protein Sorting",
            "Protein structure/function and regulation",
            "Cytoskeleton",
            "Genetics principles and genetic problems"
    };

    private ArrayList<Integer> listSelectedTopics;

    private ChainedHashTable table = new ChainedHashTable(5);

    //represents a item in the topics list
    private class ViewHolder {
        CheckedTextView name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        listSelectedTopics = new ArrayList<>();
        // get the listView id
        ListView listView = (ListView) findViewById(R.id.topicsListView);
        listView.setTextFilterEnabled(true);



        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.topics_list_item, topicArray){
           @Override
            public View getView(final int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

               CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
               cb.setBackgroundColor(Color.parseColor("#42CBC9"));

                switch (position){
                    //set background color
                    case 0: view.setBackgroundColor(Color.parseColor("#53B0D1"));
                        break;
                    case 1: view.setBackgroundColor(Color.parseColor("#4C99CF"));
                        break;
                    case 2: view.setBackgroundColor(Color.parseColor("#4675AB"));
                        break;
                    case 3: view.setBackgroundColor(Color.parseColor("#63539E"));
                        break;
                    case 4: view.setBackgroundColor(Color.parseColor("#5A528E"));
                        break;
                    case 5: view.setBackgroundColor(Color.parseColor("#5F4272"));
                        break;
                }

               ViewHolder holder = null;
               Log.v("ConvertView", String.valueOf(position));

               if (convertView == null) {
                   LayoutInflater vi = (LayoutInflater)getSystemService(
                           Context.LAYOUT_INFLATER_SERVICE);

                   holder = new ViewHolder();

                   holder.name = (CheckedTextView) view.findViewById(R.id.checkedTextView1);
                   view.setTag(holder);

                   holder.name.setOnClickListener( new View.OnClickListener() {
                       public void onClick(View v) {
                           CheckedTextView cb = (CheckedTextView) v ;
                           cb.setChecked(true);
                           listSelectedTopics.add(position);

                       }
                   });
               }
               else {
                   holder = (ViewHolder) convertView.getTag();
               }

                return view;
            }

        };

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);
        // allow multiple clicks
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }


    /**
     * Calls activity that displays multiple choice questions
     */
    public void onMCClick(View view) {

        CheckBox cb = (CheckBox)findViewById(R.id.checkBox);

        //if topics are chosen based on date
        if(cb.isChecked()){

            //create a list of selected topics and pass it to the next activity
            Intent mainIntent = new Intent(TopicsActivity.this, MCQuestionsActivity.class);
            listSelectedTopics = table.getHashArray(table.getCalendarData());
            System.out.println("The list of topics selected was " + listSelectedTopics);
            mainIntent.putIntegerArrayListExtra("selectedTopics", listSelectedTopics);
            startActivity(mainIntent);

        }

        //if topics selected manually
        else {

            Intent intent = new Intent(TopicsActivity.this, MCQuestionsActivity.class);
            // we put the list of topics selected into the "selectedTopics" key
            intent.putIntegerArrayListExtra("selectedTopics", listSelectedTopics);
            startActivity(intent);


        }
    }


    /**
     * Calls activity that displays short answer questions
     */
    public void onSAClick(View view) {
        CheckBox cb = (CheckBox)findViewById(R.id.checkBox);

        if(cb.isChecked()){

            Intent mainIntent = new Intent(TopicsActivity.this, SAQuestionsActivity.class);
            listSelectedTopics = table.getHashArray(table.getCalendarData());
            System.out.println("The list of topics selected was " + listSelectedTopics);
            mainIntent.putIntegerArrayListExtra("selectedTopics", listSelectedTopics);
            startActivity(mainIntent);
        }

        else {

            Intent intent = new Intent(TopicsActivity.this, SAQuestionsActivity.class);
            // we put the list of topics selected into the "selectedTopics" key
            intent.putIntegerArrayListExtra("selectedTopics", listSelectedTopics);
            startActivity(intent);
        }
    }


}
