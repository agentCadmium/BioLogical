package com.saverchenko.cellandmolecularbiologyqa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class represents a unit of data
 */
public class Question {

    private String question;
    private String correctAnswer;
    private String incorrectAnswer1;
    private String incorrectAnswer2;
    private String incorrectAnswer3;
    private String incorrectAnswer4;
    private String picture;
    private int topicNumber;
    private boolean isMultipleChoice;
    private ArrayList<String> answerArray;


    /**
     * Constructor
     */
    public Question(){
    }


    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public String getIncorrectAnswer1(){
        return incorrectAnswer1;
    }

    public String getIncorrectAnswer2(){
        return incorrectAnswer2;
    }

    public String getIncorrectAnswer3(){
        return incorrectAnswer3;
    }

    public String getIncorrectAnswer4(){
        return incorrectAnswer4;
    }

    public String getPicture(){
        return picture;
    }

    public int getTopicNumber(){
        return topicNumber;
    }

    public boolean getIsMultipleChoice(){
        return isMultipleChoice;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public void setCorrectAnswer(String correctAnswer){
        this.correctAnswer = correctAnswer;
    }

    public void setIncorrectAnswer1(String incorrectAnswer1){
        this.incorrectAnswer1 = incorrectAnswer1;
    }

    public void setIncorrectAnswer2(String incorrectAnswer2){
        this.incorrectAnswer2 = incorrectAnswer2;
    }

    public void setIncorrectAnswer3(String incorrectAnswer3){
        this.incorrectAnswer3 = incorrectAnswer3;
    }

    public void setIncorrectAnswer4(String incorrectAnswer4){
        this.incorrectAnswer4 = incorrectAnswer4;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }

    public void setTopicNumber(int topicNumber){
        this.topicNumber = topicNumber;
    }

    public void setMultipleChoice(String multipleChoice){
        if (multipleChoice.equals("true")) {
            this.isMultipleChoice = true;
        }
        else{
            this.isMultipleChoice = false;
        }
    }


    /**
     * Randomize the order of questions
     */
    public void setAnswerOrder(){

       answerArray = new ArrayList<>();

        answerArray.add(incorrectAnswer1);
        answerArray.add(incorrectAnswer2);
        answerArray.add(incorrectAnswer3);
        answerArray.add(correctAnswer);

        Collections.shuffle(answerArray);
    }

    public ArrayList<String> getAnswerArray(){
        return answerArray;
    }

}
