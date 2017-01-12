/**
 * 
 */
package com.saverchenko.cellandmolecularbiologyqa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Class that implements a chained hashDate table
 *
 */
public class ChainedHashTable {
	// the size of the hashDate table
	protected int size;
	// linked list of all the keys whose hashDate value is the same
	protected LinkedList<Question>[] table;

	ArrayList<Integer> newArray;


	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public ChainedHashTable(int size) {
		this.size = size;
		this.table = new LinkedList[size];

		// populate the table with linked lists
		for (int i = 0; i < size; i++) {
			table[i] = new LinkedList<Question>();
		}
	}

	/**
	 * Insert a value into the hashDate table
	 */
	public void insert(Question question) {
		table[getStringHash(question)].insertFirst(question);
	}

	/**
	 * Retrieve pointer to the first node in a linked list
	 */
	public LinkedList<Question> retrieve(int index) {

		LinkedList<Question> currentList = table[index];
		return currentList;
	}


	/**
	 * Record the current date
	 */
	public String[] getCalendarData(){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		String formattedDate = df.format(c.getTime());

		System.out.println("the date is " + formattedDate);

		String[] parts = formattedDate.split("-");
		return parts;
	}


	/**
	 * Retrieve questions based on dates
	 */
	public void hashDate(String[] parts){

		int hash = Integer.parseInt(parts[0])*10+Integer.parseInt(parts[1]);

		System.out.println("the hashDate is " + hash);
		newArray = new ArrayList<>();

		int numTopics=0;

		if(hash<=105){
			numTopics=1;
		}
		else if(hash>=105 && hash<=113){
			numTopics=2;
		}
		else if(hash>=113&&hash<=115){
			numTopics=3;
		}
		else if(hash>=115&&hash<=119){
			numTopics=4;
		}
		else if(hash>=119&&hash<=121){
			numTopics=5;
		}
		else if(hash>=121&&hash<=151){
			numTopics=6;
		}
		for(int i = 0; i<numTopics;i++){
			newArray.add(i);
		}

	}

	/**
	 * Get a list of topics
	 */
	public ArrayList<Integer> getHashArray(String[]parts){
		hashDate(parts);
		return newArray;
	}

	
	/**
	 * Calculates the key of a String
	 */
	public int getStringHash(Question question) {

		return question.getTopicNumber();
	}
	
	/**
	 * For every slot, print out the size of the linked list stored in the slot 
	 */
	public void printListLength(){
		//Print the size of each linked list
		for (int i = 0; i < size; i++) {
			System.out.println("Linked list size in slot "+ i + " is: " + table[i].size());
		}
		
	}

}
