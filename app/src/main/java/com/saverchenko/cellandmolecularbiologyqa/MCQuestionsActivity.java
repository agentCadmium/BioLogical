package com.saverchenko.cellandmolecularbiologyqa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import android.text.TextUtils;
import android.util.Log;

/**
 * Activity that displays sequence of multple choice questions
 */
public class MCQuestionsActivity extends AppCompatActivity {

    //the array list that receives list of topic passed from the Topics activity with an intent
    protected ArrayList<Integer> receivingTopicsList;
    protected ChainedHashTable hashTable;
    //log tag to parse xml life
    final String LOG_TAG = "myLogs";
    protected Question question;
    protected LinkedListNode<Question> questionInUse;
    protected static final int NUM_TOPICS=6;
    protected RadioGroup rbtnGrp;
    protected Integer randTopic;
    protected TextView answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        hashTable = new ChainedHashTable(NUM_TOPICS);

        //Read and parse our xml file
        xmlParser();

        //receive the list of topics from the Topics Activity
        //chose a random topic from the ones selected by the user
        receivingTopicsList = new ArrayList<Integer>();
        receivingTopicsList = getIntent().getIntegerArrayListExtra("selectedTopics");

        //initialize the answer filed
        answer = (TextView)findViewById(R.id.answer);

        //prints which topics have been selected
        System.out.println("the receiving array is " + receivingTopicsList.toString());

        //generate a random topic from the ones that the user picked
        generateRandomTopic();
        questionInUse = hashTable.retrieve(randTopic).getFirstNode();

        checkSAQuestion();
        displayContent();
    }


    /**
     * While iterating through the linked list with questions, skip the ones that are short answer
     */
    public void checkSAQuestion(){

        if (!questionInUse.getData().getIsMultipleChoice()){
            getNextQuestion();
            checkSAQuestion();
        }
    }


    /**
     * Display the content of the screen
     */
    public void displayContent(){

        //initialize components
        TextView infoTextView = (TextView)findViewById(R.id.question);
        ImageView imageView = (ImageView)findViewById(R.id.questionImage);
        rbtnGrp = (RadioGroup)findViewById(R.id.radioGroup);

        //if the answers are in letter format, then display the answers in ordr (A,B,C,D)
        if(questionInUse.getData().getIncorrectAnswer1().equals("A")||questionInUse.getData().getIncorrectAnswer1().equals("B")||questionInUse.getData().getIncorrectAnswer1().equals("C")||questionInUse.getData().getIncorrectAnswer1().equals("D")){
            ((RadioButton) rbtnGrp.getChildAt(0)).setText("A");
            ((RadioButton) rbtnGrp.getChildAt(1)).setText("B");
            ((RadioButton) rbtnGrp.getChildAt(2)).setText("C");
            //some questions have only 3 answers, so don't include last option if only 3 answers
            if(questionInUse.getData().getIncorrectAnswer3().equals("")){
                ((RadioButton) rbtnGrp.getChildAt(3)).setText("");
            }
            else{
                ((RadioButton) rbtnGrp.getChildAt(3)).setText("D");
            }
        }

        //if the answers are text-based, then randomize their order
        else {
            //set the random order of answers to be displayed in radioButtons' text
            questionInUse.getData().setAnswerOrder();

            ((RadioButton) rbtnGrp.getChildAt(0)).setText(questionInUse.getData().getAnswerArray().get(0));
            ((RadioButton) rbtnGrp.getChildAt(1)).setText(questionInUse.getData().getAnswerArray().get(1));
            ((RadioButton) rbtnGrp.getChildAt(2)).setText(questionInUse.getData().getAnswerArray().get(2));
            ((RadioButton) rbtnGrp.getChildAt(3)).setText(questionInUse.getData().getAnswerArray().get(3));
        }

        //display the question
        infoTextView.setText(questionInUse.getData().getQuestion());

        //if there is an accompanying picture, show it
        int id = getResources().getIdentifier("com.saverchenko.cellandmolecularbiologyqa:drawable/" + questionInUse.getData().getPicture(), null, null);
        imageView.setImageResource(id);
    }


    /**
     * Generate random place in the table array, without repetition
     */
    public void generateRandomTopic(){

        Random rand = new Random();
        System.out.println("The random topic number is " + rand.nextInt(receivingTopicsList.size()));
        randTopic = receivingTopicsList.get(rand.nextInt(receivingTopicsList.size()));

        receivingTopicsList.remove(randTopic);
    }


    /**
     * Upon clicking on an answer, check if it is correct
     */
    public void onRadioClick(View v) {

        answer = (TextView)findViewById(R.id.answer);

        // get selected radio button from radioGroup
        int selectedId = rbtnGrp.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        if(!radioButton.getText().equals(questionInUse.getData().getCorrectAnswer())){
            answer.setText("Incorrect answer");
        }

        else{
            answer.setText("Correct answer");
        }
    }


    /**
     * Reads and parsers an XML file.
     */
    protected void xmlParser() {
        try {
            XmlPullParser xmlPullParser = getResources().getXml(R.xml.data);
            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xmlPullParser.getEventType()) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // beginning of the tag
                    case XmlPullParser.START_TAG:
                        question = new Question();
                        Log.d(LOG_TAG, "START_TAG");
                        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                            switch (xmlPullParser.getAttributeName(i)){
                                case "picture":
                                    question.setPicture(xmlPullParser.getAttributeValue(i));
                                    Log.d(LOG_TAG, "picture: " + xmlPullParser.getAttributeValue(i));
                                    break;
                                case "question":
                                    question.setQuestion(xmlPullParser.getAttributeValue(i));
                                    Log.d(LOG_TAG, "question: " + xmlPullParser.getAttributeValue(i));
                                    break;
                                case "answer_correct":
                                    question.setCorrectAnswer(xmlPullParser.getAttributeValue(i));
                                    Log.d(LOG_TAG, "answer_correct: " + xmlPullParser.getAttributeValue(i));
                                    break;
                                case "answer_incorrect1":
                                    question.setIncorrectAnswer1(xmlPullParser.getAttributeValue(i));
                                    Log.d(LOG_TAG, "answer_incorrect1: " + xmlPullParser.getAttributeValue(i));
                                    break;
                                case "answer_incorrect2":
                                    question.setIncorrectAnswer2(xmlPullParser.getAttributeValue(i));
                                    Log.d(LOG_TAG, "answer_incorrect2: " + xmlPullParser.getAttributeValue(i));
                                    break;
                                case "answer_incorrect3":
                                    question.setIncorrectAnswer3(xmlPullParser.getAttributeValue(i));
                                    Log.d(LOG_TAG, "answer_incorrect3: " + xmlPullParser.getAttributeValue(i));
                                    break;
                                case "answer_incorrect4":
                                    question.setIncorrectAnswer4(xmlPullParser.getAttributeValue(i));
                                    Log.d(LOG_TAG, "answer_incorrect4: " + xmlPullParser.getAttributeValue(i));
                                    break;
                                case "M-C":
                                    question.setMultipleChoice(xmlPullParser.getAttributeValue(i));
                                    Log.d(LOG_TAG, "M-C: " + xmlPullParser.getAttributeValue(i));
                                    break;
                            }
                        }
                        break;
                    // value of the tag
                    case XmlPullParser.TEXT:
                        question.setTopicNumber(Integer.parseInt(xmlPullParser.getText()));
                        hashTable.insert(question);
                        Log.d(LOG_TAG, "value: " + xmlPullParser.getText());
                        // end of the tag
                    case XmlPullParser.END_TAG:

                        Log.d(LOG_TAG, "END_TAG");
                        break;
                    default:
                        break;
                }

                //next entry
                xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        hashTable.printListLength();
    }

    /**
     * Goes to the next question
     */
    public void getNextQuestion(){

        //step through the linked list first
        if (questionInUse.getNext()!=null) {
            questionInUse = questionInUse.getNext();
            System.out.println("the question after getNext question is " + questionInUse.getData().getQuestion());
        }

        //then if there are topics left, select another random topic
        else if(!receivingTopicsList.isEmpty()){
            generateRandomTopic();
            questionInUse = hashTable.retrieve(randTopic).getFirstNode();

        }

        //if no more topics lift, return to the topics selection menu
        else{
            Intent intent = new Intent(MCQuestionsActivity.this, TopicsActivity.class);
            startActivity(intent);
        }
    }


    /**
     * Process request for next question
     */
    public void onNextClick(View view) {

        getNextQuestion();
        checkSAQuestion();

        //uncheck all buttons
        for(int i = 0; i<4; i++){
            ((RadioButton) rbtnGrp.getChildAt(i)).setChecked(false);
        }
        //clear text for the answer
        answer.setText("");

        displayContent();
    }
}
