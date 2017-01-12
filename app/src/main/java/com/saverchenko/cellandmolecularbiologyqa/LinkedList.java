package com.saverchenko.cellandmolecularbiologyqa;
/**
 * Implements a linked list 
 */
public class LinkedList<T> {
	
	//reference to first link in the list
	protected LinkedListNode<T> head; // the access of this instance property was changed from private to protected
	
	/**
	 * Constructor
	 * Create an empty list
	 */
	public LinkedList(){
		
		head = null;
	}
	/**
	 * Get data stored in head node of list
	 * @return data of type T
	 */
	public T getFirst(){
		
		//if list is not empty
		if(head!=null)
			return head.getData(); 
		else
			return null;
	}
	 
	/**
	 * Get the head node of the list.
	 * @return node
	 */
	public LinkedListNode<T> getFirstNode(){

			return head;

	}
	 
	/**
	 * Get data stored in last node of list.
	 * @return data of type T 
	 */
	public T getLast(){
		
		LinkedListNode<T> currentNode = head;
		//if list is not empty 
		if(head!=null){
			
			//iterate though the list to find the last node
			while(currentNode.getNext()!=null){
				currentNode = currentNode.getNext();	
			}
			return currentNode.getData();
		}
		else
			return null;
	
	}
	 
	/**
	 * Get the tail node of the list.
	 */
	public LinkedListNode<T> getLastNode(){
		
		LinkedListNode<T> currentNode = head;
		//if list is not empty 
		if(head!=null){
			
			//iterate though the list to find the last node
			while(currentNode.getNext()!=null){
				currentNode = currentNode.getNext();	
		}
		return currentNode;
		}
		else 
			return null;
	}
	 
	/**
	 * Insert a new node with data at the head of the list.
	 * @param data of type T
	 */
	public void insertFirst( T data ){
		
		//create a new node
		LinkedListNode<T> newNode = new LinkedListNode<T>();
		newNode.setData(data);
		newNode.setNext(head);
		//establish newNode as the head node
		head = newNode;	
	}
	 
	/**
	 * Insert a new node with data after currentNode
	 *  the current node, after which the new node is inserted, the stored in the new node
	 */
	public void insertAfter( LinkedListNode<T> currentNode, T data ){
		
		if(head!=null){
			LinkedListNode<T> insertedNode = new LinkedListNode<T>();
			insertedNode.setData(data);
			//link the the node to node after currentNode
			insertedNode.setNext(currentNode.getNext());
			//set the new node as the next for current node
			currentNode.setNext(insertedNode);
		}
		else{
			System.out.println("List is empty");
		}
	}
	 
	/**
	 * Insert a new node with data at the end of the list.
	 * @param data of type T
	 */
	public void insertLast( T data ){
		
		if(head!=null){
			
			//create a new node and set its next to null
			LinkedListNode<T> newNode = new LinkedListNode<T>();
			newNode.setData(data);
			newNode.setNext(null);
			//link it to the previous node
			getLastNode().setNext(newNode);
		}
		else{
			System.out.println("List is empty");
		}
	}
		
	/**
	 * Remove the first node
	 */
	public void deleteFirst(){
		if(head!=null){
			
		//move head pointer to the node after the first node	
		head = head.getNext();
		}
		else{
			System.out.println("List is empty");
		}
	}
	
	/**
	 * Remove the last node
	 */
	public void deleteLast(){
		
		if(head.getNext() == null) {
		    head = null;
		}
		
		else{
			LinkedListNode<T> currentNode = head;
			while(currentNode.getNext().getNext()!=null){
				currentNode = currentNode.getNext();
			}
			//removes last node
			currentNode.setNext(null);
		}
	}
	 
	/**
	 * Remove node following currentNode
	 * If no node exists (i.e., currentNode is the tail), do nothing
	 * node after which the node has to be deleted
	 */
	public void deleteNext( LinkedListNode<T> currentNode ){
		
		//If list is empty or currentNode is the tail
		if (currentNode == getLastNode() || head==null){
			System.out.println("List is either empty or the current node is tail");
		} else{

			//set the node after the deleted node as next for current node
			currentNode.setNext(currentNode.getNext().getNext());
		}
	}
	 
	/**
	 * Return the number of nodes in this list.
	 */
	public int size(){
		int count = 0;
		LinkedListNode<T> currentNode = head;
		
		//loop through the list and increment number of nodes
		while(currentNode!= null){
			count ++;
			currentNode = currentNode.getNext();
		}
		return count;
	}
	 
	/**
	 * Check if list is empty.
	 * @return true if list contains no items.
	 */
	public boolean isEmpty(){
		
		if (head!=null){
			return false;
		}
		return true;
	}
	
 
	/**
	 * Return a String representation of the list.
	 */
	public String toString(){
		
		LinkedListNode<T> currentNode = head;
		String printableList = "";
		if (head!=null){
			
			//store growing String of the list in a variable while looping though the list
			while(currentNode.getNext()!= null){
				printableList += currentNode.toString() + "-> ";
				currentNode = currentNode.getNext();
			}
			//add last node to the String
			printableList+=currentNode.toString();
			return printableList;
		}
		else{
			System.out.println("List is empty");
			return null;
		}	
	}	
}
