package com.mgg.entity;

import java.util.Comparator;

/**
 * This class represents a Linked List and utilizes the Node class,
 * and allows for modification and access of the linked list.
 * 
 * Date: 04/05/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 * 
 */

public class LinkedList<T> {
	private Node<T> head;
	private int size;
	private final Comparator<T> cmp;
	
	public LinkedList() {
		this.head = null;
		this.size = 0;
		this.cmp = null;
	}
	
	public LinkedList(Comparator<T> cmp) {
		this.head = null;
		this.size = 0;
		this.cmp = cmp;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public boolean isEmpty() {
		if (this.size == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Adds an element into the list in a sorted position according to constructed comparator
	 * @param x
	 */
	public void addSortedElement(T x) {		
		if(this.cmp == null) {
			throw new IllegalStateException("A valid constructor was not called.");
		}
		if (this.isEmpty()) {
			addToStart(x);
			return;
		}
		
		Node<T> current = this.head;
		Node<T> next = null;
		
		if (this.cmp.compare(x, current.getElement()) <= 0) {
			addToStart(x);
			return;
		}
		while (this.cmp.compare(x, current.getElement()) > 0) {
			if (!current.hasNext()) {
				addToEnd(x);
				return;
			}
			else {
				next = current.getNext();
			}
			
			if(this.cmp.compare(x, next.getElement()) <= 0) {
				Node<T> newNode = new Node<T>(x);
				current.setNext(newNode);
				newNode.setNext(next);
				this.size++;
				return;
			}
			else {
				current = current.getNext();
			}
		}
	}
	
	/**
	 * Adds a given element to the start of the list
	 * @param x
	 */
	public void addToStart(T x) {
		Node<T> newHead = new Node<T>(x);
		newHead.setNext(this.head);
		this.head = newHead;
		this.size++;
		return;
	}
	
	/**
	 * Adds a given element to the end of the list
	 * @param x
	 */
	public void addToEnd(T x) {
		if(this.isEmpty()) {
			addToStart(x);
			return;
		}
		
		Node<T> previous = this.getNodeAtIndex(this.size - 1);
		Node<T> current = this.getNodeAtIndex(this.size);
		Node<T> newNode = new Node<T>(x);
		newNode.setNext(current);
		previous.setNext(newNode);
		this.size++;
		return;
	}
	
	/**
	 * Adds a given element to a provided index
	 * @param x, index
	 */
	public void addElementAtIndex(T x, int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index " + index + " is out of bounds");
		}
		if (index == 0) {
			this.addToStart(x);
		} else {
			Node<T> previous = this.getNodeAtIndex(index - 1);
			Node<T> current = previous.getNext();
			Node<T> newNode = new Node<T>(x);
			newNode.setNext(current);
			previous.setNext(newNode);
			this.size++;
			return;
		}
	}
	
	/**
	 * Gets an element at a provided index
	 * @param index
	 * @return element
	 */
	public T getElementAtIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("Index " + index + " out of " + this.size + " is out of bounds!");
		}
		Node<T> current = this.getNodeAtIndex(index);
		return current.getElement();
	}

	/**
	 * Gets a node at a provided index
	 * @param index
	 * @return current node
	 */
	private Node<T> getNodeAtIndex(int index) {
		if(index < 0 || index > this.size) {
			throw new IllegalArgumentException ("Index " + index + " out of " + this.size + " is out of bounds!");
		}
		
		Node<T> current = this.head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current;
	}
	
	/**
	 * Finds the index of the element provided
	 * @param key
	 * @return
	 */
	public int indexOf(T key) {
		if(this.cmp == null) {
			throw new IllegalStateException("A valid constructor was not called.");
		}
		Node<T> current = this.head;
		for(int i=0; i<this.size; i++) {
			if(this.cmp.compare(key, current.getElement()) == 0) {
				return i;
			}
			current = current.getNext();
		}
		return -1;
	}
	
	/**
	 * Clears out the contents of the list, making it an empty list.
	 */
	public void clear() {
		Node<T> current = this.head;
		while(current != null) {
			this.head = this.head.getNext();
			current = this.head;
			this.size--;
		}
	}
	
	/**
	 * Removes the element at the start of the list
	 */
	public T removeFromStart() {
		if(this.isEmpty()) {
			return null;
		}
		
		T x = this.head.getElement();
		this.head = this.head.getNext();		
		this.size--;
		return x;
	}
	
	/**
	 * Removes the element at the end of the list
	 * @return
	 */
	public T removeFromEnd() {
		if(this.isEmpty()) {
			return null;
		}
		
		T x = this.getElementAtIndex(this.size);
		removeElementAtIndex(this.size);
		return x;
	}
	
	/**
	 * Removes an element at a provided index
	 * @param index
	 */
	public void removeElementAtIndex(int index) {
		if(index < 0 || index > this.size) {
			throw new IllegalArgumentException ("Index " + index + " out of " + this.size + " is out of bounds!");
		}
		
		if (index == 0) {
			this.head = this.head.getNext();
			this.size--;
		}
		else {
			Node<T> prev = getNodeAtIndex(index - 1);
			Node<T> current = prev.getNext();
			prev.setNext(current.getNext());
			this.size--;
		}
		return;
	}
}
