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

/**
 * Activity that displays sequence of short answer questions
 */
public class SAQuestionsActivity extends AppCompatActivity {

    protected ArrayList<Integer> receivingTopicsList;
    protected ChainedHashTable hashTable;
    final String LOG_TAG = "myLogs";
    protected Question question;
    protected LinkedListNode<Question> questionInUse;
    protected static final int NUM_TOPICS=6;
    protected RadioGroup rbtnGrp;
    protected Integer randTopic;
    protected TextView saAnswerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saquestions);

        hashTable = new ChainedHashTable(NUM_TOPICS);

        //Read and parse our xml file
        xmlParser();

        //receive the list of topics from the Topics Activity
        //chose a random topic from the ones selected by the user
        receivingTopicsList = new ArrayList<Integer>();
        receivingTopicsList = getIntent().getIntegerArrayListExtra("selectedTopics");

        System.out.println("the receiving array is " + receivingTopicsList.toString());

        //generate a random topic from the ones that the user picked
        generateRandomTopic();
        questionInUse = hashTable.retrieve(randTopic).getFirstNode();

        //initialize the answer field
        saAnswerView = (TextView)findViewById(R.id.saAnswerView);
        checkSAQuestion();
        displayContent();
    }

    /**
     * Activity that displays sequence of short answer questions
     */
    public void generateRandomTopic(){

        Random rand = new Random();
        System.out.println("The random topic number is " + rand.nextInt(receivingTopicsList.size()));
        randTopic = receivingTopicsList.get(rand.nextInt(receivingTopicsList.size()));

        receivingTopicsList.remove(randTopic);
    }


    /**
     * While iterating through the linked list with questions, skip the ones that are multiple choice
     */
    public void checkSAQuestion(){

        System.out.println("the sa/mc tag is " + questionInUse.getData().getIsMultipleChoice());
        if (questionInUse.getData().getIsMultipleChoice()){

            getNextQuestion();
            checkSAQuestion();
        }
    }


    /**
     * Display the content of the screen
     */
    public void displayContent(){
        TextView saQuestionView = (TextView)findViewById(R.id.saQuestionView);
        System.out.println("what is the question when it is being set at textView" + questionInUse.getData().getQuestion());
        //display the question
        saQuestionView.setText(questionInUse.getData().getQuestion());
    }

    public void getNextQuestion(){
        if (questionInUse.getNext()!=null) {
            questionInUse = questionInUse.getNext();
            System.out.println("the question after getNext question is " + questionInUse.getData().getQuestion());
        }

        else if(!receivingTopicsList.isEmpty()){
            generateRandomTopic();
            questionInUse = hashTable.retrieve(randTopic).getFirstNode();

        }

        else{
            Intent intent = new Intent(SAQuestionsActivity.this, TopicsActivity.class);
            startActivity(intent);
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
     * Shows the answer to the question
     */
    public void onShowAnswerClick(View view) {

        saAnswerView.setText(questionInUse.getData().getCorrectAnswer());
    }


    /**
     * Process request for next question
     */
    public void onNextClick(View view) {

        getNextQuestion();
        //set the answer to empty
        saAnswerView.setText("");
        checkSAQuestion();
        displayContent();
    }


}
